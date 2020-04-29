package com.xh.server.model;

import java.io.Serializable;

public class Category implements Serializable {
    private Integer catid;

    private String catno;

    private String name;

    private String desn;

    public Integer getCatid() {
        return catid;
    }

    public void setCatid(Integer catid) {
        this.catid = catid;
    }

    public String getCatno() {
        return catno;
    }

    public void setCatno(String catno) {
        this.catno = catno == null ? null : catno.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDesn() {
        return desn;
    }

    public void setDesn(String desn) {
        this.desn = desn == null ? null : desn.trim();
    }
}