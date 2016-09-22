package com.pieroncini.tommaso.goshopping.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pieroncini.tommaso.goshopping.Utilities.MyApplication;
import com.pieroncini.tommaso.goshopping.R;
import com.pieroncini.tommaso.goshopping.Utilities.getLoginInfo;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {

    private String[] authentificationFromStorage;
    private EditText username;
    private EditText password;
    private String inputUsername;
    private String inputPassword;
    private String access;
    private Intent intent1;
    private Intent intent2;
    private Boolean infoDeleted = false;
    private boolean changeColor = false;
    private ImageView login;
    private ImageView register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("GoShopping!");

        Intent recoverIntent = getIntent();
        Bundle recoverBundle = recoverIntent.getExtras();
        if (recoverBundle != null){
            String message = recoverBundle.getString("message");
            if (message != null && message.equals("LogOut")) {
                infoDeleted = true;
            }
        }
        intent1 = new Intent(this, GroupsListActivity.class);
        intent2 = new Intent(this, RegistrationActivity.class);
        username = (EditText) findViewById(R.id.editText5);
        password = (EditText) findViewById(R.id.editText6);
        login = (ImageView) findViewById(R.id.imageView7);
        register = (ImageView) findViewById(R.id.imageView6);

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
        try {
            FileInputStream fis = openFileInput("GoShopping_Authentification_Data.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
                Log.e("log_tag 11", line);
            }
            authentificationFromStorage = sb.toString().split(",");
        } catch(Exception e) {
            Log.e("log_tag 10", "Failed to retrieve data: " + e);
            if (infoDeleted) {
                Toast.makeText(LoginActivity.this, "You logged out", Toast.LENGTH_LONG).show();
            }
        }

        // OTHERWISE network request to server
        if (authentificationFromStorage != null){
            inputUsername = authentificationFromStorage[0];
            inputPassword = authentificationFromStorage[1];

            try{
                Log.e("log_tag12","gettingPermission");
                access = new getLoginInfo().execute(inputUsername, inputPassword).get();
                Log.e("log_tag13","gotPermission");
            } catch (Exception e){
                Log.e("log_tag5","FAILED TO GET PERMISSION RESPONSE" + e);
            }

            if (access.equals("allowed")){
                Log.e("log_tag6", "in access");
                ((MyApplication) this.getApplication()).setUsername(inputUsername);
                ((MyApplication) this.getApplication()).setPassword(inputPassword);
                Toast.makeText(this, "Logged in as " +((MyApplication)
                        this.getApplication()).getUsername(),Toast.LENGTH_SHORT).show();
                startActivity(intent1);
                finish();
            } else if(access.equals("denied")) {
                Toast.makeText(LoginActivity.this,
                        "We lost track of your data! Why don't you register again?",
                        Toast.LENGTH_LONG).show();
            } else {
                Log.e("log_tag_phpnotworking",access);
            }
        }


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
        if (changeColor) {
            login.setScaleX((float) 1.04);
            login.setScaleY((float) 1.04);
        }
        inputUsername = username.getText().toString();
        inputPassword = password.getText().toString();
        if (inputPassword.equals(""))
            inputPassword = "fakewillfail";

        try{
            access = new getLoginInfo().execute(inputUsername, inputPassword).get();
        } catch (Exception e){
            Log.e("log_tag5","FAILED TO GET PERMISSION RESPONSE" + e);
        }

        if (access.equals("allowed")){
            Log.e("log_tag6", "in access");
            String fileName = "GoShopping_Authentification_Data.txt";
            String content = inputUsername + "," + inputPassword;

            FileOutputStream outputStream = null;
            try {
                outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
                outputStream.write(content.getBytes());
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            ((MyApplication) getApplicationContext()).setUsername(inputUsername);
            ((MyApplication) getApplicationContext()).setPassword(inputPassword);
            Toast.makeText(getApplicationContext(), "Logged in as " +
                    ((MyApplication) getApplicationContext()).getUsername(),
                    Toast.LENGTH_SHORT).show();
            startActivity(intent1);
            finish();
        } else if(access.equals("denied")) {
            Toast.makeText(getApplicationContext(), "Wrong username or password",
                    Toast.LENGTH_LONG).show();
        }
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
