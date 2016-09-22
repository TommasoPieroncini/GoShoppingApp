package com.pieroncini.tommaso.goshopping.Utilities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by Tommaso on 3/4/2016.
 */
public class getPicFromServer extends AsyncTask<String, Void, Bitmap> {

    @Override
    protected Bitmap doInBackground(String... strings){
        String path = strings[0].replaceAll(" ", "-") + ".jpg";
        Log.e("TEST", path);
        Bitmap bitmap = null;

        try {
            URL url = new URL(path);
            InputStream inputStream = url.openStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
        } catch(Exception e){
            Log.e("log_error", "failed to retrieve image: " + e);
        }
        return bitmap;
    }
}
