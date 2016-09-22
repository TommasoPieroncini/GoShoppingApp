package com.pieroncini.tommaso.goshopping.Utilities;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Tommaso on 5/10/2016.
 */
public class createGroup extends AsyncTask<ArrayList<String>, Void, String> {

    @Override
    protected String doInBackground(ArrayList<String>... args) {
        String success = "";
        InputStream is = null;
        ArrayList<String> groupAndMembersNames = args[0];
        String result = "";
        String username = groupAndMembersNames.get(0);
        try {
            URL url = new URL("http://128.61.104.207:8165/GoShopping/create_group.php");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            Log.e("loglength", Integer.toString(groupAndMembersNames.size()));
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            OutputStream out = new BufferedOutputStream(conn.getOutputStream());
            String mystr = "username=" + username + "&group=" + groupAndMembersNames.get(groupAndMembersNames.size() - 1) + "&members=";
            //RE-RUN THIS!
            for (int i = 0; i < groupAndMembersNames.size()-1; i++) {
                mystr = mystr + groupAndMembersNames.get(i);
                if (i < groupAndMembersNames.size()-2) {
                    mystr = mystr + "/";
                }
            }
            Log.e("mystr", mystr);
            out.write(mystr.getBytes());
            out.flush();
            out.close();
            is = conn.getInputStream();
            int responseCode = conn.getResponseCode();
        } catch (Exception e) {
            Log.e("log_tag4", "Error in createGroup");
        }

        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null){
                sb.append(line);
            }
            is.close();

            result = sb.toString();
            Log.e("result", result);
        }
        catch(Exception e){
            Log.e("log_tag2", "Error converting result".toString());
        }

        Log.e("result", result);
        if (result.equals("true")) {
            success = "Group created successfully!";
        } else if (result.equals("You have already created a group by this name!")) {
            success = "You have already created a group by this name!";
        }

        return success;
    }
}