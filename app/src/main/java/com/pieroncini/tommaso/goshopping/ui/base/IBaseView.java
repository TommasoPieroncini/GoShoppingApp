package com.pieroncini.tommaso.goshopping.ui.base;

import android.support.annotation.StringRes;

/**
 * Created by Tommaso on 5/27/2017.
 */

public interface IBaseView {
    void showLoading();

    void hideLoading();

    void onError(@StringRes int resId);

    void onError(String message);

    void showMessage(String message);

    void showMessage(@StringRes int resId);

    boolean isNetworkConnected();

    void hideKeyboard();
}
