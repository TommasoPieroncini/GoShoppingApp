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
import android.widget.Toast;

import com.pieroncini.tommaso.goshopping.MyApplication;
import com.pieroncini.tommaso.goshopping.R;
import com.pieroncini.tommaso.goshopping.data.DataManager;
import com.pieroncini.tommaso.goshopping.data.getLoginInfo;
import com.pieroncini.tommaso.goshopping.di.components.ActivityComponent;
import com.pieroncini.tommaso.goshopping.di.modules.ActivityModule;
import com.pieroncini.tommaso.goshopping.ui.base.BaseActivity;
import com.pieroncini.tommaso.goshopping.ui.main.GroupsListActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

import javax.inject.Inject;

import butterknife.BindView;

public class LoginActivity extends BaseActivity {

    @Inject
    private DataManager mDataManager;

    private ActivityComponent activityComponent;

    @BindView(R.id.editText5)
    private EditText username;

    @BindView(R.id.editText6)
    private EditText password;

    @BindView(R.id.imageView7)
    private ImageView login;

    @BindView(R.id.imageView6)
    private ImageView register;

    private String[] authenticationFromStorage;

    private String inputUsername;
    private String inputPassword;

    private Intent intent1;
    private Intent intent2;


    public ActivityComponent getActivityComponent() {
        if (activityComponent == null) {
            activityComponent = DaggerActivityComponent.builder()
                    .activityModule(new ActivityModule(this))
                    .applicationComponent(MyApplication.get(this).getComponent())
                    .build();
        }
        return activityComponent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getActivityComponent().inject(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("GoShopping!");

        intent1 = new Intent(this, GroupsListActivity.class);
        intent2 = new Intent(this, RegistrationActivity.class);

        login.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                changeColor = true;
                login.setScaleX((float) .8);
                login.setScaleY((float) .8);
                return false;
            }
        });

        register.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                changeColor = true;
                register.setScaleX((float) .8);
                register.setScaleY((float) .8);
                return false;
            }
        });

        //IF AUTHENTICATION DATA IS IN INTERNAL STORAGE LOGIN ACTIVITY IS SKIPPED


        // If the authentication data can't be found in the server, the app rests at LoginActivity

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (changeColor) {
                    register.setScaleX((float) 1.04);
                    register.setScaleY((float) 1.04);
                }
                startActivity(intent2);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    login();
                }
                return true;
            }
        });
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
}
