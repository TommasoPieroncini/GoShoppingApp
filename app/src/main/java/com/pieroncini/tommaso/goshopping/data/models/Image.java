package com.pieroncini.tommaso.goshopping.data.models;

import android.graphics.Bitmap;

/**
 * Created by Tommaso on 3/22/2016.
 * class that represents an image in the application
 */
// Image class that helps me handle bitmaps and their locations on the server
public class Image {

    private Bitmap image;
    private String path;

    public Image(Bitmap image, String path) {
        this.image = image;
        this.path = path;
    }

    public Bitmap getBitmap() {
        return this.image;
    }

    public String getPathName() {
        return this.path;
    }
}
