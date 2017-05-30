package com.pieroncini.tommaso.goshopping.ui.login;

import com.pieroncini.tommaso.goshopping.ui.base.IBasePresenter;

/**
 * Created by Tommaso on 5/28/2017.
 */

public interface ILoginPresenter<V extends ILoginView> extends IBasePresenter<V> {

    void onServerLoginClick(String email, String password);

    void onGoogleLoginClick();

    void onFacebookLoginClick();
}
