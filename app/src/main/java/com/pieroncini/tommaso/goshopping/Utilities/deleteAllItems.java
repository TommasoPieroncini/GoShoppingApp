package com.pieroncini.tommaso.goshopping.Utilities;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Tommaso on 7/4/2016.
 */
public class deleteAllItems extends AsyncTask<String, Void, Void> {

    @Override
    protected Void doInBackground(String... params){
        InputStream is = null;
        String groupName = params[0];
        try {
            URL url = new URL("http://128.61.104.207:8165/GoShopping/delete_all_shopping.php");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            Log.e("log", "deleteallitems");
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            OutputStream out = new BufferedOutputStream(conn.getOutputStream());
            String message = "groupname=" + groupName;
            Log.e("log", message);
            out.write(message.getBytes());
            out.flush();
            out.close();
            int responseCode = conn.getResponseCode();
            is = conn.getInputStream();
        } catch (Exception e) {
            Log.e("log_tag4", "Error in delete: " + e.toString());
        }
        return null;
    }
}
