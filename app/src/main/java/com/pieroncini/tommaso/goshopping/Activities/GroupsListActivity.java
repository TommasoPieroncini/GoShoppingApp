package com.pieroncini.tommaso.goshopping.Activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.pieroncini.tommaso.goshopping.Models.Group;
import com.pieroncini.tommaso.goshopping.Utilities.MyApplication;
import com.pieroncini.tommaso.goshopping.R;
import com.pieroncini.tommaso.goshopping.Utilities.getDataGroups;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class GroupsListActivity extends AppCompatActivity {

    private final Context context = this;
    private Intent toCreateGroup;
    private Bundle logOutBooleanBundle;
    private Intent logOutIntent;
    private ListView groupsList;
    private ArrayList<Group> allGroups;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("GoShopping!");
        username = ((MyApplication) getApplicationContext()).getUsername();
        Boolean infoLogin = ((MyApplication) getApplicationContext()).getInfoLogin();
        toCreateGroup = new Intent(context, CreateGroupActivity.class);
        ImageView newGroup = (ImageView) findViewById(R.id.imageView2);
        logOutBooleanBundle = new Bundle();
        logOutIntent = new Intent(this, LoginActivity.class);
        groupsList = (ListView) findViewById(R.id.listView2);
        allGroups = new ArrayList<>();
        refresh();

        if (infoLogin) {
            showInfoPopUp();
        }

        newGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopUp();
            }
        });

        // Groups behavior when clicked
        groupsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Group groupSelected = allGroups.get(position);
                ((MyApplication) getApplicationContext()).setCurrentGroup(groupSelected);
                Intent toProductsList = new Intent(getApplicationContext(), ItemsListActivity.class);
                startActivity(toProductsList);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_groups_list_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_log_out) {
            File authData = new File(getFilesDir(), "GoShopping_Authentification_Data.txt");
            if (authData.delete()) {
                String message = "LogOut";
                logOutBooleanBundle.putString("message", message);
                logOutIntent.putExtras(logOutBooleanBundle);
                startActivity(logOutIntent);
                finish();
            } else {
                Toast.makeText(getApplicationContext(),
                        "Ooops sorry, there was an error.\nTry again!", Toast.LENGTH_LONG).show();
            }
            return true;
        }

        /*if (id == R.id.action_see_profile) {
            return true;
        }*/

        if (id == R.id.action_groups_refresh) {
            refresh();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Goodbye!", Toast.LENGTH_SHORT).show();
        moveTaskToBack(true);
    }


    private void showPopUp() {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.custom_dialog_create_group);
        dialog.setTitle("               NEW GROUP");
        final EditText groupName = (EditText) dialog.findViewById(R.id.editText10);
        groupName.setFocusable(true);
        groupName.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(groupName, InputMethodManager.SHOW_IMPLICIT);
        Button done = (Button) dialog.findViewById(R.id.button6);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                String name = groupName.getText().toString();
                Bundle bundle = new Bundle();
                bundle.putString("groupName", name);
                toCreateGroup.putExtras(bundle);
                startActivity(toCreateGroup);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    // Tutorial dialog
    private void showInfoPopUp() {
        final AlertDialog.Builder builder =  new AlertDialog.Builder(this);
        String message = "Here you can create shopping groups for all" +
                " of your needs by pressing on the plus button!";
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

    // Refreshed the list of groups
    private void refresh() {
        try {
            allGroups = new getDataGroups().execute(username).get();
        } catch (Exception e) {
            Log.e("log_error", "failed to retrieve groups data");
        }
        for (Group g : allGroups) {
            g.setGroupImage();
        }
        GroupsAdapter adapter = new GroupsAdapter(this, allGroups);
        groupsList.setAdapter(adapter);
    }
}
