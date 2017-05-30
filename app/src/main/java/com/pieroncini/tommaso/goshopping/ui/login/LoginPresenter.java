package com.pieroncini.tommaso.goshopping.ui.login;

import com.pieroncini.tommaso.goshopping.data.DataManager;
import com.pieroncini.tommaso.goshopping.ui.base.BasePresenter;
import com.pieroncini.tommaso.goshopping.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Tommaso on 5/28/2017.
 */

public class LoginPresenter<V extends ILoginView> extends BasePresenter<V>
        implements ILoginPresenter<V> {

    @Inject
    public LoginPresenter(DataManager dataManager,
                          SchedulerProvider schedulerProvider,
                          CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onServerLoginClick(String email, String password) {

    }

    @Override
    public void onGoogleLoginClick() {

    }

    @Override
    public void onFacebookLoginClick() {

    }
}
