package com.pieroncini.tommaso.goshopping.data.models;

import java.util.Date;
import java.util.List;

/**
 * class that represents a shopping group in the application
 */
public class Group {

    private String groupName;
    private Image groupImage;
    private List<User> members;
    private User admin;
    private Date createdAt;

    public Group(String groupName, User admin, List<User> members, Image image, Date createdAt) {
        this.members = members;
        this.admin = admin;
        this.groupName = groupName;
        this.groupImage = image;
        this.createdAt = createdAt;
    }

    /*public void setGroupImage() {
        try {
            groupImage = new getPicFromServer().execute(imagePath).get();
        } catch (Exception e) {
            Log.e("log_error", "failed setting bitmap in group.class");
        }
    }*/

    public String getName() {
        return this.groupName;
    }

    public void setName(String groupName) {
        this.groupName = groupName;
    }

    /*public String getTrueName() {
        return this.groupName;
    }*/

    public Image getImage() {
        return this.groupImage;
    }

    public void setImage(Image image) {
        this.groupImage = image;
    }

    public List<User> getMembers() {
        return this.members;
    }

    public void addMember(User member) {
        this.members.add(member);
    }

    public void removeMember(User member) {
        this.members.remove(member);
    }

    public int getMemberCount() {
        return this.members.size();
    }

    public User getAdmin() {
        return this.admin;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }

    @Override
    public String toString() {
        return "Group: " + this.groupName;
    }

    /*public String getMembersString() {
        String members = "";
        for (String member : this.members) {
            members = members + member + ", ";
        }
        return members.substring(0, members.length() - 2);
    }*/
}
