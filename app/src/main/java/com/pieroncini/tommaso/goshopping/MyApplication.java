package com.pieroncini.tommaso.goshopping;

import android.app.Application;
import android.content.Context;

import com.pieroncini.tommaso.goshopping.data.DataManager;
import com.pieroncini.tommaso.goshopping.data.models.Group;
import com.pieroncini.tommaso.goshopping.di.components.ApplicationComponent;
import com.pieroncini.tommaso.goshopping.di.components.DaggerApplicationComponent;
import com.pieroncini.tommaso.goshopping.di.modules.ApplicationModule;

import javax.inject.Inject;

public class MyApplication extends Application {

    protected ApplicationComponent applicationComponent;

    @Inject
    DataManager dataManager;

    public static MyApplication get(Context context) {
        return (MyApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .build();
        applicationComponent.inject(this);
    }

    public ApplicationComponent getComponent(){
        return applicationComponent;
    }

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
