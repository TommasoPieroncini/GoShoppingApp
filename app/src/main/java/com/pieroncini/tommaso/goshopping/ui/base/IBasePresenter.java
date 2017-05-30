package com.pieroncini.tommaso.goshopping.ui.base;

/**
 * Created by Tommaso on 5/28/2017.
 */

public interface IBasePresenter<V extends IBaseView> {

    void onAttach(V mvpView);

    void onDetach();

    void setUserAsLoggedOut();
}
