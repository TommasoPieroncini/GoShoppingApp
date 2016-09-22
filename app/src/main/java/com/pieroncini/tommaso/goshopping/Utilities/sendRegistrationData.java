package com.pieroncini.tommaso.goshopping.Utilities;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Tommaso on 6/22/2016.
 */
public class sendRegistrationData extends AsyncTask<String,Void,String> {
    @Override
    protected String doInBackground(String... inputData) {
        String serverResponse;
        InputStream is = null;
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL("http://128.61.104.207:8165/GoShopping/shopping_registration.php");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            OutputStream out = new BufferedOutputStream(conn.getOutputStream());
            String message = "email=" + inputData[0]+"&username=" + inputData[1]+"&password=" + inputData[2];
            out.write(message.getBytes());
            out.flush();
            out.close();
            is = conn.getInputStream();
            int responseCode = conn.getResponseCode();
            Log.i("log_tag", "POST Response Code :: " + responseCode);
        } catch (Exception e){
            Log.e("log_tag1", "CONNECTION FAILED in Registration Activity: " + e);
        }
        try {
            try {
                String line;
                br = new BufferedReader(new InputStreamReader(is));
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e){
            Log.e("log_tag3", "ERROR IN PARSING RESPONSE: " + e);
        }
        serverResponse = sb.toString();
        return serverResponse;
    }
}
