package com.pieroncini.tommaso.goshopping.Utilities;

import android.os.AsyncTask;
import android.util.Log;

import com.pieroncini.tommaso.goshopping.Models.Group;

import org.json.JSONArray;
import org.json.JSONObject;

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
public class getDataGroups extends AsyncTask<String, Void, ArrayList<Group>> {

    @Override
    protected ArrayList<Group> doInBackground(String... params){
        String username = params[0];
        String groupname;
        String[] members;
        String imageURL;
        String theme;
        ArrayList<Group> data = new ArrayList<>();
        String result = "";
        InputStream input = null;
        try{
            URL url = new URL("http://128.61.104.207:8165/GoShopping/get_groups_data.php");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            String message = "username=" + username;
            OutputStream out = new BufferedOutputStream(conn.getOutputStream());
            out.write(message.getBytes());
            out.flush();
            out.close();
            int responseCode = conn.getResponseCode();
            input = conn.getInputStream();
        }
        catch(Exception e){
            Log.e("log_tag1", "Error in http connection " + e.toString());
        }
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(input,"iso-8859-1"),8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null){
                sb.append(line + "/n");
            }
            input.close();

            result = sb.toString();
        }
        catch(Exception e){
            Log.e("log_tag2", "Error converting result".toString());
        }

        try{
            Log.e("log", "getting groups data");
            JSONArray jArray = new JSONArray(result);

            for(int i = 0; i < jArray.length(); i++){
                JSONObject json = jArray.getJSONObject(i);
                groupname = new String(json.getString("name"));
                members = new String(json.getString("members")).split(",");
                imageURL = new String(json.getString("image"));
                theme = new String(json.getString("theme"));
                Group nextGroup = new Group(groupname, members, theme);
                data.add(nextGroup);
            }
        }
        catch(Exception e){
            Log.e("log_tag3", "Error Parsing Data " + e.toString());
        }
        return data;
    }
}
