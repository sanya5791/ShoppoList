package com.akhutornoy.shoppinglist.createproduct_onescreen.model;

public class CheckableItemModel {
    private final String name;
    private boolean isChecked;

    public CheckableItemModel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
