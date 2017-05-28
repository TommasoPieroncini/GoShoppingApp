package com.pieroncini.tommaso.goshopping.data.models;

/**
 * Created by Tommaso on 6/19/2016.
 * class that represents a shopping item
 */
public class Item {

    private String name;
    private String quantityString;
    private String notes;
    private long id;
    private Check check;
    private User creator;
    private Image image;
    private Group group;

    public Item(String name, String quantityString, String notes, long id, User creator, Image image, Group group) {
        this.name = name;
        this.quantityString = quantityString;
        this.notes = notes;
        this.id = id;
        this.creator = creator;
//        picAddress = "http://128.61.104.207:8165/GoShopping/ItemPics/";
        check = new Check();
        this.image = image;
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public String getQuantity() {
        return quantityString;
    }

    public String getNotes() {
        return notes;
    }

    public Check getCheck() {
        return check;
    }

    public void setCheck(boolean check, User checkedBy) {
        if (check) {
            this.check.check(checkedBy);
        } else {
            this.check.unCheck();
        }
    }

    public User getCreator(){
        return creator;
    }

    public Image getImage() {
        return this.image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Group getGroup() {
        return this.group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public long getId() {
        return this.id;
    }


    @Override
    public String toString() {
        // TODO: reformat
        return "Item!";
    }

//    public void setUpItem(Group group) {
//        try {
//            image = new getPicFromServer().execute(picAddress + group.getName() + group.getCreator()
//                    + "-" + username + "-" + name).get();
//        } catch (Exception e) {
//            Log.e("log_error", "failed setting bitmap in item.class");
//        }
//        this.group = group;
//    }

}
