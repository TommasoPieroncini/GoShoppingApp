package com.pieroncini.tommaso.goshopping.data;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Tommaso on 7/4/2016.
 */
public class deleteGroup extends AsyncTask<String, Void, Void> {

    @Override
    public Void doInBackground(String... params) {
        String groupName = params[0];
        try {
            URL url = new URL("http://128.61.104.207:8165/GoShopping/shopping_del_group.php");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            OutputStream out = new BufferedOutputStream(conn.getOutputStream());
            String mystr = "groupname=" + groupName;
            out.write(mystr.getBytes());
            out.flush();
            out.close();
            int responseCode = conn.getResponseCode();
            Log.e("log", responseCode + " attempted connection");
        } catch (Exception e) {
            Log.e("log_error", "Failed to delete group");
        }
        return null;
    }
}
