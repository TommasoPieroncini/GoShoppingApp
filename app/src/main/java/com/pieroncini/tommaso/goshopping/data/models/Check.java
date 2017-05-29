package com.pieroncini.tommaso.goshopping.data.models;

/**
 * Created by Tommaso on 5/27/2017.
 * class that represents a check on an item
 */
public class Check {

    private boolean checked;
    private User checkedBy;

    public Check(){
    }

    public void check(User checkedBy) {
        checked = true;
        this.checkedBy = checkedBy;
    }

    public void unCheck() {
        checked = false;
        checkedBy = null;
    }

    public User getCheckedBy() {
        return checkedBy;
    }

    public boolean getValue() {
        return checked;
    }
}
