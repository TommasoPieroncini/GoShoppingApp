package com.pieroncini.tommaso.goshopping.Utilities;

import android.app.Application;

import com.pieroncini.tommaso.goshopping.Models.Group;

public class MyApplication extends Application {

    private String username;
    private String password;
    private Boolean infoLogin = false;
    private Group currentGroup;

    public void setUsername(String username){
        this.username = username;
    }

    public String getUsername(){
        return this.username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }

    public void setInfoLogin(Boolean infoLogin) {
        this.infoLogin = infoLogin;
    }

    public Boolean getInfoLogin() {
        return this.infoLogin;
    }

    public void setCurrentGroup(Group currentGroup) {
        this.currentGroup = currentGroup;
    }

    public Group getCurrentGroup() {
        return this.currentGroup;
    }
}
