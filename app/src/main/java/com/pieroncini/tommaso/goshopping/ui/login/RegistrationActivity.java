package com.pieroncini.tommaso.goshopping.ui.login;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.pieroncini.tommaso.goshopping.MyApplication;
import com.pieroncini.tommaso.goshopping.R;
import com.pieroncini.tommaso.goshopping.data.sendRegistrationData;

import java.io.FileOutputStream;

public class RegistrationActivity extends AppCompatActivity {

    private EditText email;
    private EditText username;
    private EditText password;
    private EditText password2;
    private String inputEmail;
    private String inputUsername;
    private String inputPassword;
    private String inputPassword2;
    private Intent intent;
    private String serverResponse;
    private boolean changeColor = false;
    private ImageView register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("GoShopping!");

        email = (EditText) findViewById(R.id.editText2);
        username = (EditText) findViewById(R.id.editText);
        password = (EditText) findViewById(R.id.editText3);
        password2 = (EditText) findViewById(R.id.editText4);
        register = (ImageView) findViewById(R.id.imageView);
        Button goToLogin = (Button) findViewById(R.id.button4);
        intent = new Intent(this, LoginActivity.class);
        showInfoPopUp();

        register.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                changeColor = true;
                register.setScaleX((float) .8);
                register.setScaleY((float) .8);
                return false;
            }
        });

        // Reads register button input
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (changeColor) {
                    register.setScaleX((float) 1.04);
                    register.setScaleY((float) 1.04);
                }
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                inputEmail = email.getText().toString();
                inputUsername = username.getText().toString();
                inputPassword = password.getText().toString();
                inputPassword2 = password2.getText().toString();
                if (inputUsername.contains(" ")) {
                    Toast.makeText(getApplicationContext(), "Can't have spaces in your username!",
                            Toast.LENGTH_LONG).show();
                    return;
                }

                // Checks password equivalency
                if (inputPassword.equals(inputPassword2)) {
                    if (inputEmail.contains("@")) {
                        try {
                            serverResponse = new sendRegistrationData().execute(inputEmail,
                                    inputUsername,inputPassword).get().toString();
                        } catch (Exception e) {
                            Log.e("log_tag3", "FAILED TO GET RESPONSE FROM sendData");
                        }

                        // Writes to internal file system
                        String fileName = "GoShopping_Authentification_Data.txt";
                        String content = inputUsername + "," + inputPassword;
                        FileOutputStream outputStream;

                        try {
                            outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
                            outputStream.write(content.getBytes());
                            outputStream.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Log.e("serverresponse", serverResponse);
                        if (serverResponse.equals("Thank you for registering!")) {
                            ((MyApplication) getApplicationContext()).setInfoLogin(true);
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Invalid e-mail",
                                Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Passwords don't match",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });
    }

    // Initial tutorial dialog
    private void showInfoPopUp() {
        final AlertDialog.Builder builder =  new AlertDialog.Builder(this);
        String message = "Register here for an awesome GoShopping experience! \nFor security reasons," +
                " avoid re-using passwords from other websites!";
        builder.setTitle("Help desk");
        Drawable shoppingIcon = getResources().getDrawable(R.drawable.shopping_bag);
        builder.setIcon(shoppingIcon);
        builder.setMessage(message)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface d, int id) {
                                d.dismiss();
                            }
                        });
        builder.create().show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_registration, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}


