package com.bboroccu.model.dao;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by bboroccu on 16. 1. 22..
 */
@Entity
@Table(name = "member", schema = "", catalog = "streamingdb")
public class Member {
    private int seq;
    private String email;
    private String username;
    private String password;
    private String usergb;
    private String token;
    private String useyn;
    private String phone;
    private Date regdate;
    private Date update;
    @Basic
    @Column(name = "seq")
    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }
    @Id
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    @Basic
    @Column(name = "name")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "usergb")
    public String getUsergb() {
        return usergb;
    }

    public void setUsergb(String usergb) {
        this.usergb = usergb;
    }
    @Basic
    @Column(name = "token")
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Basic
    @Column(name = "regdate")
    public Date getRegdate() {
        return regdate;
    }

    public void setRegdate(Date regdate) {
        this.regdate = regdate;
    }

    @Basic
    @Column(name = "useyn")
    public String getUseyn() {
        return useyn;
    }

    public void setUseyn(String useyn) {
        this.useyn = useyn;
    }

    @Basic
    @Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Member that = (Member) o;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = email != null ? email.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        return result;
    }

    @Override
    public String toString()
    {
        return seq + "," + email + "," + username;
    }
}
