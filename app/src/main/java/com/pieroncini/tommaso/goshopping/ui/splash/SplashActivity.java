package com.pieroncini.tommaso.goshopping.ui.splash;

import android.content.Intent;
import android.os.Bundle;

import com.pieroncini.tommaso.goshopping.R;
import com.pieroncini.tommaso.goshopping.ui.base.BaseActivity;
import com.pieroncini.tommaso.goshopping.ui.login.LoginActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;

public class SplashActivity extends BaseActivity implements ISplashView {

    @Inject
    ISplashPresenter<ISplashView> mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(SplashActivity.this);

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void setUp() {

    }
}
