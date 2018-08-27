package com.bboroccu.model.dao;

import javax.persistence.*;

/**
 * Created by bboroccu on 16. 1. 26..
 */
@Entity
@Table(name="songlist" , schema = "", catalog = "streamingdb")
public class SongList {
    private int seq;
    private int memberseq;
    private String title;
    private String playlist;

    @Id
    @Column(name = "seq")
    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    @Basic
    @Column(name = "memberseq")
    public int getMemberseq() {
        return memberseq;
    }

    public void setMemberseq(int memberseq) {
        this.memberseq = memberseq;
    }

    @Basic
    @Column(name = "playlist")
    public String getPlaylist() {
        return playlist;
    }

    public void setPlaylist(String playlist) {
        this.playlist = playlist;
    }

    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SongList that = (SongList) o;
        if (memberseq != that.memberseq) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return seq + "," + memberseq;
    }
}
