package com.pieroncini.tommaso.goshopping.Utilities;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import com.pieroncini.tommaso.goshopping.Models.Image;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Tommaso on 3/22/2016.
 */
public class uploadPic extends AsyncTask<Image, Void, Void> {

    @Override
    protected Void doInBackground(Image... params) {
        Image image = params[0];
        String imagePath = image.getName();
        String phpFile = "handle_item_pic_upload.php";
        String info = imagePath.split("-")[0];
        if (info.equals("groupPic")) {
            phpFile = "handle_group_pic_upload.php";
        }
        Bitmap imageBitmap = image.getBitmap();
        InputStream is = null;
        DataOutputStream dout;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        Log.e("picName", imagePath);
        if (imageBitmap == null) {
            Log.e("log", "bitmap is null!");
        }

        try {
            URL url = new URL("http://128.61.104.207:8165/GoShopping/" + phpFile);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("uploaded_file", imagePath);
            conn.setUseCaches(false);

            // Set HTTP method to POST.
            conn.setRequestMethod("POST");

            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bout);
            bout.flush();
            byte[] bytes = bout.toByteArray();
            bout.close();

            dout = new DataOutputStream(conn.getOutputStream());
            dout.writeBytes(twoHyphens + boundary + lineEnd);
            dout.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\"" + imagePath + "\"" + lineEnd);
            dout.writeBytes(lineEnd);
            dout.write(bytes);
            dout.writeBytes(lineEnd);
            dout.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

            dout.flush();
            dout.close();

            int resp = conn.getResponseCode();
            is = conn.getInputStream();

        } catch (Exception e) {
            Log.e("log error", "failed to connect to handle pic upload");
        }

        return null;
    }
}
