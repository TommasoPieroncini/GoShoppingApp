package com.pieroncini.tommaso.goshopping.di.modules;

import android.app.Activity;
import android.content.Context;

import com.pieroncini.tommaso.goshopping.di.ActivityContext;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Tommaso on 5/28/2017.
 */

@Module
public class ActivityModule {

    private Activity mActivity;

    public ActivityModule(Activity activity) {
        mActivity = activity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return mActivity;
    }

    @Provides
    Activity provideActivity() {
        return mActivity;
    }
}
