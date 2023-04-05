package com.jtech.jhs.jfiles;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;

@Transactional
public interface JFileRepository extends CrudRepository<JFile, Long> {
    List<JFile> findAll();
    List<JFile> findByFpath(String fpath);

    @Modifying
    @Query("DELETE JFile WHERE fpath like :path%")
    int deleteByFpath(String path);

    @Query("SELECT j FROM JFile j WHERE lower(j.fname) like %:name%")
    List<JFile> findByFname(String name);
}
