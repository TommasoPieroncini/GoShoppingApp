package com.pieroncini.tommaso.goshopping.Utilities;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Tommaso on 6/22/2016.
 */
public class setItemChecked extends AsyncTask<String, Void, Void> {

    @Override
    protected Void doInBackground(String... checks){
        InputStream is = null;
        String groupName = checks[2];
        try {
            URL url = new URL("http://128.61.104.207:8165/GoShopping/shopping_set_checked.php");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            Log.e("logsetitemchecked", checks[1]);
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            OutputStream out = new BufferedOutputStream(conn.getOutputStream());
            String mystr = "pos=" + checks[0] + "&checked=" + checks[1] + "&groupname=" + groupName;
            Log.e("itemchecked", mystr);
            out.write(mystr.getBytes());
            out.flush();
            out.close();
            int responseCode = conn.getResponseCode();
        } catch (Exception e) {
            Log.e("log_tag","Couldn't connect to set_checked: " + e.toString());
        }
        return null;
    }
}
