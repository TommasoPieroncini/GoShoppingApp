package com.pieroncini.tommaso.goshopping.data;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Tommaso on 2/26/2016.
 */
public class addItem extends AsyncTask<String, Void , String> {

    @Override
    protected String doInBackground(String... params) {
        String result = "";
        InputStream is = null;
        String itemName = params[0];
        String itemQuantity = params[1];
        String itemNote = params[2];
        String groupName = params[3];
        String username = params[4];
        String imagePath = params[5];
        try {
            URL url = new URL("http://128.61.104.207:8165/GoShopping/update_shopping.php");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            OutputStream out = new BufferedOutputStream(conn.getOutputStream());
            String message = "name=" + itemName + "&quantity=" + itemQuantity + "&note="
                    + itemNote + "&groupname=" + groupName + "&username=" + username + "&path=" + imagePath;
            out.write(message.getBytes());
            out.flush();
            out.close();
            int responseCode = conn.getResponseCode();
            is = conn.getInputStream();
        } catch (Exception e ) {
            Log.e("log_error", "failed to do add item request");
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
        }
        catch(Exception e){
            Log.e("log_tag2", "Error converting result".toString());
        }

        return result;
    }
}
