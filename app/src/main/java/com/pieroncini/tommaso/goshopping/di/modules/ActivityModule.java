package com.pieroncini.tommaso.goshopping.di.modules;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.pieroncini.tommaso.goshopping.di.ActivityContext;
import com.pieroncini.tommaso.goshopping.di.PerActivity;
import com.pieroncini.tommaso.goshopping.ui.login.ILoginPresenter;
import com.pieroncini.tommaso.goshopping.ui.login.ILoginView;
import com.pieroncini.tommaso.goshopping.ui.login.LoginPresenter;
import com.pieroncini.tommaso.goshopping.ui.splash.ISplashPresenter;
import com.pieroncini.tommaso.goshopping.ui.splash.ISplashView;
import com.pieroncini.tommaso.goshopping.ui.splash.SplashPresenter;
import com.pieroncini.tommaso.goshopping.utils.rx.ISchedulerProvider;
import com.pieroncini.tommaso.goshopping.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Tommaso on 5/28/2017.
 */

@Module
public class ActivityModule {

    private AppCompatActivity mActivity;

    public ActivityModule(AppCompatActivity activity) {
        this.mActivity = activity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return mActivity;
    }

    @Provides
    AppCompatActivity provideActivity() {
        return mActivity;
    }

    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return new SchedulerProvider();
    }

    @Provides
    @PerActivity
    ILoginPresenter<ILoginView> provideLoginPresenter(
            LoginPresenter<ILoginView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    ISplashPresenter<ISplashView> provideSplashPresenter(
            SplashPresenter<ISplashView> presenter) {
        return presenter;
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(AppCompatActivity activity) {
        return new LinearLayoutManager(activity);
    }
}
