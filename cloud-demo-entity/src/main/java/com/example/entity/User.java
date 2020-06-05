package com.example.entity;


import com.sun.istack.internal.NotNull;

import java.io.Serializable;

public class User implements Serializable {

    private Integer id;

    @NotNull
    private String username;

    @NotNull
    private String password;

    public User() {

    }

    public User(Integer id, String username, String password) {
        this.id = 1;
        this.username = "1937612009";
        this.password = "suzhifan";
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
