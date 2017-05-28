package com.pieroncini.tommaso.goshopping.data.models;

import java.util.Date;

/**
 * Created by Tommaso on 5/26/2017.
 * class for a user of the application
 */
public class User {

    private String username;
    private Date createdAt;

    public User(String username, Date createdAt) {
        this.username = username;
        this.createdAt = createdAt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }
}
