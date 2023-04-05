package com.jtech.jhs.jfiles;

import org.hibernate.query.criteria.internal.expression.function.CastFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.jtech.jhs.Global;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import org.apache.commons.io.FileUtils;

@Service
public class JFileService {
    private static final Logger log = LoggerFactory.getLogger(JFileService.class);
    
    List<JFile> jlist; 

    @Value("${jhs.base.path}")
    private String basePath;

    @Value("${jhs.filetypes}")
    private String fileTypes;

    @Value("${jhs.ignorefiles}")
    private String ignoreFiles;

    @Autowired
    private JFileRepository repository;

    //read file content
    List<String> fc(String path) throws IOException {
        return Files.readAllLines(Paths.get(path));
    }

    // save file contnt
    void savefc(String path, String content) throws IOException {
        try {
            Files.write(Paths.get(path), content.getBytes());
        } catch (Exception e) {
            log.error("Caught NullPoijntExcepotion");
        }

    }

    //list of directories only
    public String[] dirs(String path) {
        String[] directories = new File(path).list(new FilenameFilter() {
            @Override
            public boolean accept(File file, String name) {
                return new File(file, name).isDirectory();
            }
        });
        return directories;
    }

    //list of files/dirs in the given directory
    public List<JFile> files(String path) {
        List<JFile> list = new ArrayList();
        File[] files = new File(basePath + '/' + path).listFiles();
        for (File f : files) {
            if (ignoreFiles.contains(f.getName()))
                continue;
            JFile jf = new JFile(f.getName(), path, date2str(new Date(f.lastModified())), f.length(), fileType(f));
            list.add(jf);
        }
        return list;
    }

    public List<JFile> search(String name) {
        return repository.findByFname(name.toLowerCase());
    }

    // scan directopry and save to db
    @Async("threadPoolTaskExecutor")
    public void scan(String path) throws IOException {
        log.info("scan: Starting.. " + path);
        Global.IsFileScanRunning = true;
        repository.deleteByFpath(path);
        try { 
            List<File> files = (List<File>) FileUtils.listFiles(new File(basePath + '/' + path), null, true);
            for (File f : files) {
                if (f.isFile() && !f.isHidden()) {
                    String fname = f.getName();
                    if (!ignoreFiles.contains(fname)) {
                        String fpath="./";
                        if (basePath.equals(fpath)) {
                            String p = f.getPath();
                            fpath = p.substring(0, p.indexOf(fname)-1);
                        } else {
                            String p = f.getAbsolutePath();
                            fpath = p.substring(p.indexOf(basePath)+basePath.length()+1, p.indexOf(fname)-1);
                        }
                        repository.save(new JFile(fname, fpath.toString(), date2str(new Date(f.lastModified())), f.length(), fileType(f)));
                    }  
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        Global.IsFileScanRunning = false;
        log.info("scan: Completed!");
    }

    private String date2str(Date idate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(idate);
    }
    
    private String fileType(File f) {
        if (!f.isFile())
            return "0Adir";
        String fname = f.getName();
        int i = fname.lastIndexOf('.');
        return i > 0 ? fname.substring(i+1).toLowerCase() : "";
    }
}