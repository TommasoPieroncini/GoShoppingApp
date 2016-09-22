package com.pieroncini.tommaso.goshopping.Utilities;

import android.os.AsyncTask;
import android.util.Log;

import com.pieroncini.tommaso.goshopping.Models.Item;

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
public class getDataItems extends AsyncTask<String, Void, ArrayList<Item>> {

    @Override
    protected ArrayList<Item> doInBackground(String... params){
        String groupName = params[0];
        ArrayList<Item> items = new ArrayList<>();
        String result = "";
        InputStream input = null;
        try{
            URL url = new URL("http://128.61.104.207:8165/GoShopping/connect_shopping.php");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            Log.e("log", "connect_shopping");
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            String message = "groupname=" + groupName;
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
            JSONArray jArray = new JSONArray(result);

            for(int i = 0; i < jArray.length(); i++){
                JSONObject json = jArray.getJSONObject(i);
                String name = new String(json.getString("item"));
                String q = new String(json.getString("quantity"));
                String nts = new String(json.getString("note"));
                int id = new Integer(json.getString("id"));
                String check = new String(json.getString("checked"));
                String usern = new String(json.getString("username"));
                String picAdd = new String(json.getString("picture"));
                Item newItem = new Item(name, q, nts, id, check, usern, picAdd);
                items.add(newItem);
            }
        }
        catch(Exception e){
            Log.e("log_tag3", "Error Parsing Data " + e.toString());
        }
        return items;
    }
}
