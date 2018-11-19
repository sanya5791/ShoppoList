package com.akhutornoy.shoppinglist.ui.createproduct_onescreen.model;

public class CheckableItemModel {
    private final String name;
    private boolean isChecked;

    public CheckableItemModel(String name, boolean isChecked) {
        this.name = name;
        this.isChecked = isChecked;
    }

    public CheckableItemModel(String name) {
        this(name, false);
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
