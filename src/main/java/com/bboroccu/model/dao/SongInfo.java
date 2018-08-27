package com.bboroccu.model.dao;

import javax.persistence.*;

/**
 * Created by bboroccu on 16. 1. 22..
 */
@Entity
@Table(name = "songinfo", schema = "", catalog = "streamingdb")
public class SongInfo {
    private int seq;
    private String title;
    private String creator;
    private String filename;
    private String path;
    private String imgpath;
    private String uploader;

    @Id
    @Column(name = "seq")
    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }
    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    @Basic
    @Column(name = "creator")
    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    @Basic
    @Column(name = "filename")
    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    @Basic
    @Column(name = "path")
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
    @Basic
    @Column(name = "imgpath")
    public String getImgpath() {
        return imgpath;
    }

    public void setImgpath(String imgpath) {
        this.imgpath = imgpath;
    }

    @Basic
    @Column(name = "uploader")
    public String getEmail() {
        return uploader;
    }

    public void setEmail(String email) {
        this.uploader = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SongInfo that = (SongInfo) o;
        if (uploader != null ? !uploader.equals(that.uploader) : that.uploader != null) return false;
        if (seq != ((SongInfo) o).getSeq()) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = uploader != null ? uploader.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return seq + "," + uploader + "," + title;
    }
}
