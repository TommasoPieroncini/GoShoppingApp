package com.pieroncini.tommaso.goshopping.Models;

import android.graphics.Bitmap;
import android.util.Log;

import com.pieroncini.tommaso.goshopping.Utilities.getPicFromServer;

// Model for a Shopping Group: includes name, image, members and creator's names, theme (TODO)
public class Group {

    private String groupName;
    private Bitmap groupImage;
    private String imagePath;
    private String[] members;
    private String theme;
    private String creator;

    public Group(String groupName, String[] members, String theme) {
        this.members = members;
        this.theme = theme;
        this.creator = members[0];
        this.groupName = groupName.replaceFirst(this.creator, "");
        this.imagePath = "http://128.61.104.207:8165/GoShopping/GroupPics/groupPic" + "-" + this.groupName + creator;
    }

    public void setGroupImage() {
        try {
            groupImage = new getPicFromServer().execute(imagePath).get();
        } catch (Exception e) {
            Log.e("log_error", "failed setting bitmap in group.class");
        }
    }

    public String getName() {
        return this.groupName.replaceAll("_", " ");
    }

    public String getTrueName() {
        return this.groupName;
    }

    public Bitmap getGroupImage() {
        if (groupImage != null) {
            return this.groupImage;
        } else {
            return null;
        }
    }

    public String[] getMembers() {
        return this.members;
    }

    public String getTheme() { return this.theme; }

    public void setName(String groupName) {
        this.groupName = groupName;
    }

    public String toString() {
        return "Group: " + this.groupName;
    }

    public String getMembersString() {
        String members = "";
        for (String member : this.members) {
            members = members + member + ", ";
        }
        return members.substring(0, members.length() - 2);
    }

    public String getCreator() {
        return this.creator;
    }
}
