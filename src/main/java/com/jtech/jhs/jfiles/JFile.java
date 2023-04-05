package com.jtech.jhs.jfiles;

import javax.persistence.*;
import java.util.Date;

@Entity
public class JFile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, name= "fname", length = 1024)
    private String fname;

    @Column(nullable = false, name= "fpath", length = 4096) // relative path
    private String fpath;
    
    @Column(name="fdate")
    private String fdate;

    @Column(name="fsize")
    private Long fsize;

    @Column(name="ftype")
    private String ftype;

    @Column(name="lastmodified")
    private Date lastmodified;

    protected JFile() {}

    public JFile(String fname, String fpath, String fdate, Long fsize, String ftype) {
        this.fname = fname;
        this.fpath = fpath;
        this.fdate = fdate;
        this.fsize = fsize;
        this.ftype = ftype;
        this.lastmodified = new Date();
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", fname='" + getFname() + "'" +
            ", fpath='" + getFpath() + "'" +
            ", fdate='" + getFdate() + "'" +
            ", fsize='" + getFsize() + "'" +
            ", ftype='" + getFtype() + "'" +
            ", lastmodified='" + getLastmodified() + "'" +
            "}";
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFname() {
        return this.fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getFpath() {
        return this.fpath;
    }

    public void setFpath(String fpath) {
        this.fpath = fpath;
    }

    public String getFdate() {
        return this.fdate;
    }

    public void setFdate(String fdate) {
        this.fdate = fdate;
    }

    public Long getFsize() {
        return this.fsize;
    }

    public void setFsize(Long fsize) {
        this.fsize = fsize;
    }

    public String getFtype() {
        return this.ftype;
    }

    public void setFtype(String ftype) {
        this.ftype = ftype;
    }

    public Date getLastmodified() {
        return this.lastmodified;
    }

    public void setLastmodified(Date lastmodified) {
        this.lastmodified = lastmodified;
    }
}

