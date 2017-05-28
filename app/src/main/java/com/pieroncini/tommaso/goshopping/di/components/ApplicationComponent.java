package com.pieroncini.tommaso.goshopping.di.components;

import android.app.Application;
import android.content.Context;

import com.pieroncini.tommaso.goshopping.data.DataManager;
import com.pieroncini.tommaso.goshopping.data.DbHelper;
import com.pieroncini.tommaso.goshopping.data.SharedPrefsHelper;
import com.pieroncini.tommaso.goshopping.di.ApplicationContext;
import com.pieroncini.tommaso.goshopping.MyApplication;
import com.pieroncini.tommaso.goshopping.di.modules.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Tommaso on 5/28/2017.
 */

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(MyApplication demoApplication);

    @ApplicationContext
    Context getContext();

    Application getApplication();

    DataManager getDataManager();

    SharedPrefsHelper getPreferenceHelper();

    DbHelper getDbHelper();

}
