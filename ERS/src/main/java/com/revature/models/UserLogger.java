package com.revature.models;

import java.io.Serializable;

public class UserLogger implements Serializable {
    private String username;
    private String password;

    public UserLogger() {
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
