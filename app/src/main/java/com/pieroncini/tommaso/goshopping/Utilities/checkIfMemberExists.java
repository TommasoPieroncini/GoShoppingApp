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

/**
 * Created by Tommaso on 2/26/2016.
 */

public class checkIfMemberExists extends AsyncTask<String, Void, Boolean> {

    @Override
    protected Boolean doInBackground(String... names) {
        Boolean memberExists = false;
        InputStream is = null;
        String name = names[0];
        String result = "";
        try {
            URL url = new URL("http://128.61.104.207:8165/GoShopping/check_if_exists.php");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            OutputStream out = new BufferedOutputStream(conn.getOutputStream());
            String mystr = "tocheck=" + name;
            out.write(mystr.getBytes());
            out.flush();
            out.close();
            is = conn.getInputStream();
            int responseCode = conn.getResponseCode();
        } catch (Exception e) {
            Log.e("log_tag","Couldn't connect to if_exists: " + e.toString());
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

        if (result.equals("true")) {
            memberExists = true;
        }

        return memberExists;
    }
}
