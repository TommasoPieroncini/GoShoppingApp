package com.pieroncini.tommaso.goshopping.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.pieroncini.tommaso.goshopping.R;
import com.pieroncini.tommaso.goshopping.data.DataManager;
import com.pieroncini.tommaso.goshopping.ui.base.BaseActivity;
import com.pieroncini.tommaso.goshopping.ui.main.GroupsListActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity implements ILoginView {

    @Inject
    ILoginPresenter<ILoginView> mPresenter;

    @BindView(R.id.login_email)
    EditText username;

    @BindView(R.id.login_password)
    EditText password;
//
//    @BindView(R.id.imageView7)
//    ImageView login;
//
//    @BindView(R.id.imageView6)
//    ImageView register;

    private String[] authenticationFromStorage;

    private String inputUsername;
    private String inputPassword;

    private Intent intent1;
    private Intent intent2;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(LoginActivity.this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    // On login pressed username and password are sent through to the server
    private void login() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void openMainActivity() {
        Intent intent = GroupsListActivity.getStartIntent(LoginActivity.this);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    protected void setUp() {

    }
}
