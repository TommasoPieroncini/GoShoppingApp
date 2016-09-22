package com.pieroncini.tommaso.goshopping.Models;

import android.graphics.Bitmap;
import android.util.Log;

import com.pieroncini.tommaso.goshopping.Utilities.getPicFromServer;

/**
 * Created by Tommaso on 6/19/2016.
 */
public class Item {

    private String name;
    private String quantity;
    private String notes;
    private int id;
    private String check;
    private String username;
    private String picAddress;
    private Bitmap image;
    private Group group;

    public Item(String n, String q, String notes, int id, String ch, String usern, String picAdd) {
        name = n;
        quantity = q;
        this.notes = notes;
        this.id = id;
        check = ch;
        username = usern;
        picAddress = "http://128.61.104.207:8165/GoShopping/ItemPics/";
    }

    public String getName() {
        return name;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getNotes() {
        return notes;
    }

    public String getCheck() {
        return check;
    }

    public void setCheck(String ch) {
        check = ch;
    }

    public String getUsername(){
        return username;
    }

    public String toString() {
        return name + "   by: " + username + "\nQuantity: " + quantity + "   Notes: " + notes;
    }


    public void setUpItem(Group group) {
        try {
            image = new getPicFromServer().execute(picAddress + group.getName() + group.getCreator()
                    + "-" + username + "-" + name).get();
        } catch (Exception e) {
            Log.e("log_error", "failed setting bitmap in item.class");
        }
        this.group = group;
    }

    public Bitmap getImage() {
        return this.image;
    }

    public Group getGroup() {
        return this.group;
    }
}
