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
public class deleteItem extends AsyncTask<String,Void, Void> {

    @Override
    protected Void doInBackground(String... params){
        InputStream is = null;
        int position = Integer.valueOf(params[0]) + 1;
        String groupName = params[1];
        try {
            URL url = new URL("http://128.61.104.207:8165/GoShopping/delete_shopping.php");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            Log.e("log", "deleteitem");
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            OutputStream out = new BufferedOutputStream(conn.getOutputStream());
            String mystr = "pos=" + Integer.toString(position) + "&groupname=" + groupName;
            out.write(mystr.getBytes());
            out.flush();
            out.close();
            int responseCode = conn.getResponseCode();
        } catch (Exception e) {
            Log.e("log_tag4", "Error in delete");
        }
        return null;
    }
}
