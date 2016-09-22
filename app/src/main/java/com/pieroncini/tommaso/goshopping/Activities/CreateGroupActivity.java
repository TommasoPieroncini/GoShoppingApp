package com.pieroncini.tommaso.goshopping.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pieroncini.tommaso.goshopping.Utilities.MyApplication;
import com.pieroncini.tommaso.goshopping.R;
import com.pieroncini.tommaso.goshopping.Utilities.checkIfMemberExists;
import com.pieroncini.tommaso.goshopping.Utilities.createGroup;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class CreateGroupActivity extends AppCompatActivity {

    private EditText groupName;
    private EditText newMember;
    private Button addMember;
    private TextView strNumMembersAdded;
    private ArrayList<String> membersAdded;
    private int numMembers = 0;
    private Boolean memberExists = false;
    private boolean changeColor = false;
    private Intent toGroupsList;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("GoShopping!");
        Intent fromGroupsList = getIntent();
        username = ((MyApplication) getApplicationContext()).getUsername();
        Boolean infoLogin = ((MyApplication) getApplicationContext()).getInfoLogin();
        groupName = (EditText) findViewById(R.id.editText7);
        newMember = (EditText) findViewById(R.id.editText8);
        addMember = (Button) findViewById(R.id.button8);
        strNumMembersAdded = (TextView) findViewById(R.id.textView3);
        Button submit = (Button) findViewById(R.id.button5);
        membersAdded = new ArrayList<>();
        membersAdded.add(username);
        toGroupsList = new Intent(this, GroupsListActivity.class);

        // This checks if we're coming from the GroupsList activity, in which case we'll take the
        // groupname inserted there and input it in a new EditText
        if (fromGroupsList != null) {
            String grName;
            Bundle bundle = fromGroupsList.getExtras();
            grName = bundle.getString("groupName");
            groupName.setText(grName);
        }

        if (infoLogin) {
            showInfoPopUp();
        }

        // animating the member add button
        addMember.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                changeColor = true;
                addMember.setScaleX((float) .8);
                addMember.setScaleY((float) .8);
                return false;
            }
        });

        // adding specified username to group
        addMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (changeColor) {
                    addMember.setScaleX((float) 1.04);
                    addMember.setScaleY((float) 1.04);
                    addMember.setBackgroundResource(R.drawable.add_member);
                }
                String name = newMember.getText().toString();
                newMember.setText("");
                if (!name.equals(username)) {
                    if (!membersAdded.contains(name)) {
                        if (name.length() > 2) {
                            try {
                                memberExists = new checkIfMemberExists().execute(name).get();
                            } catch (Exception e) {
                                Log.e("log_error", "failed to check if member exists");
                            }
                            if (memberExists) {
                                numMembers = numMembers + 1;
                                strNumMembersAdded.setText("Members added: " + numMembers);
                                membersAdded.add(name);
                                Toast.makeText(getApplicationContext(), name +
                                        " has been added to the group", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), name +
                                        " isn't a valid GoShopping account!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "A username is longer than 2 characters!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "You have already added that person!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(),
                            "That's yourself!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // submit names added and groupname to the server and respond to the user
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = groupName.getText().toString();
                String groupCreated = "";
                if (name.contains("-") || name.contains("_")) {
                    Toast.makeText(getApplicationContext(),
                            "Group name contains illegal characters!", Toast.LENGTH_LONG).show();
                    return;
                }
                if (name.length() >= 2) {
                    membersAdded.add(name.replaceAll(" ", "_"));
                    try {
                        groupCreated = new createGroup().execute(membersAdded).get();
                    } catch (Exception e) {
                        Log.e("log_error", "failed to create group"+ e.toString());
                    }
                    membersAdded = new ArrayList<String>();
                    membersAdded.add(username);
                    strNumMembersAdded.setText("Members added: " + 0);
                    if (groupCreated.equals("Group created successfully!")) {
                        Toast.makeText(getApplicationContext(), groupCreated, Toast.LENGTH_SHORT).show();
                        groupName.setText("");
                        startActivity(toGroupsList);
                    } else if (groupCreated.equals("You have already created a group by this name!")) {
                        Toast.makeText(getApplicationContext(), groupCreated, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "There was an error, try again!",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Group names have to be at least 2 letters long!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Tutorial dialog
    private void showInfoPopUp() {
        final AlertDialog.Builder builder =  new AlertDialog.Builder(this);
        String message = "Here you can choose the name of your group and add members by pressing" +
                " on the icon. Press 'Create' when you are done!";
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

}


