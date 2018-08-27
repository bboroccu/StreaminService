package com.bboroccu.model.dao;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by jae-seongheo on 2016. 9. 4..
 */
@Entity
@Table(name = "requestsong_reply", schema = "", catalog = "streamingdb")
public class RequestSongReply {
    private int seq;
    private int requestseq;
    private String writer;
    private String writername;
    private String contents;
    private Date writedate;

    @Id
    @Column(name = "seq")
    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    @Basic
    @Column(name = "requestseq")
    public int getRequestseq() {
        return requestseq;
    }

    public void setRequestseq(int requestseq) {
        this.requestseq = requestseq;
    }

    @Basic
    @Column(name = "writer")
    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    @Basic
    @Column(name = "writername")
    public String getWritername() {
        return writername;
    }

    public void setWritername(String writername) {
        this.writername = writername;
    }

    @Basic
    @Column(name = "contents")
    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    @Basic
    @Column(name = "writedate")
    public Date getWritedate() {
        return writedate;
    }

    public void setWritedate(Date writedate) {
        this.writedate = writedate;
    }
}
