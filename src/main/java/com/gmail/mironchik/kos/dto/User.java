package com.gmail.mironchik.kos.dto;

/**
 * Date: 18.08.13
 * Time: 1:56
 */
public class User {
    private Integer id;
    private String name;
    private String phlink;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhlink() {
        return phlink;
    }

    public void setPhlink(String phlink) {
        this.phlink = phlink;
    }
}
