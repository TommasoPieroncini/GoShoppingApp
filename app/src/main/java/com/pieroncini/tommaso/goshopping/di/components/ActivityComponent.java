package com.pieroncini.tommaso.goshopping.di.components;

import com.pieroncini.tommaso.goshopping.di.PerActivity;
import com.pieroncini.tommaso.goshopping.di.modules.ActivityModule;
import com.pieroncini.tommaso.goshopping.ui.login.LoginActivity;

import dagger.Component;

/**
 * Created by Tommaso on 5/28/2017.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    // void inject(MainActivity mainActivity);

    void inject(LoginActivity loginActivity);

}