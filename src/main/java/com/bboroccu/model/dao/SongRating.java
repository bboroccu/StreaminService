package com.bboroccu.model.dao;

import javax.persistence.*;

/**
 * Created by jae-seongheo on 2016. 5. 22..
 */
@Entity
@Table(name="songrating", schema = "", catalog = "streamingdb")
public class SongRating {
    private int seq;
    private int songseq;
    private int likecount;
    private int playcount;
    private int downcount;

    @Id
    @Column(name = "seq")
    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    @Basic
    @Column(name = "songseq")
    public int getSongseq() {
        return songseq;
    }

    public void setSongseq(int songseq) {
        this.songseq = songseq;
    }
    @Basic
    @Column(name = "likecount")
    public int getLikecount() {
        return likecount;
    }

    public void setLikecount(int likecount) {
        this.likecount = likecount;
    }
    @Basic
    @Column(name = "playcount")
    public int getPlaycount() {
        return playcount;
    }

    public void setPlaycount(int playcount) {
        this.playcount = playcount;
    }
    @Basic
    @Column(name = "downcount")
    public int getDowncount() {
        return downcount;
    }

    public void setDowncount(int downcount) {
        this.downcount = downcount;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return seq + "," + seq;
    }
}
