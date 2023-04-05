package com.jtech.jhs.api;

import java.util.List;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jtech.jhs.jfiles.JFile;
import com.jtech.jhs.jfiles.JFileService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/api")
public class APIController {
    private static final Logger log = LoggerFactory.getLogger(APIController.class);
    
    @Autowired
    private JFileService jfileService;

    @Value("${jhs.base.path}")
    private String basePath;

    @GetMapping("")
    public @ResponseBody
    String getfb() { 
        return "API";
    };

    @GetMapping("/dirs")
    public @ResponseBody
    String[] getDirs(@RequestParam String d) {
        return jfileService.dirs(basePath + "/" + d);
    }

    @GetMapping("/files")
    public @ResponseBody
    List<JFile> getFiles(@RequestParam String d) {
        return jfileService.files(d);
    }

    @GetMapping("/search")
    public @ResponseBody
    List<JFile> getSearch(@RequestParam String q) {
       return q.isEmpty() ? List.of() : jfileService.search(q);
    }

    @GetMapping("/scan")
    public @ResponseBody
    String getScan(@RequestParam String d) throws IOException {
        jfileService.scan(d);
        return "success";
    }

    @GetMapping("/scanstate")
    public @ResponseBody
    boolean getScanState() throws IOException {
        return Global.IsFileScanRunning;
    }

    /* 
    @GetMapping("/fc")
    public @ResponseBody
    List<String> getfc(@RequestParam String p) throws IOException {
        return jfileService.fc(basePath + "/" + p);
    }

    @PostMapping("/savefc")
    public void postfc(@RequestBody FContent fc) throws IOException {
        jfileService.savefc(basePath + "/" + fc.getP(), fc.getFc());
    }
    */
}
