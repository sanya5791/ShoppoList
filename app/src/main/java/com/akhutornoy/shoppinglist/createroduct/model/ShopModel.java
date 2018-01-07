package com.akhutornoy.shoppinglist.createroduct.model;

public class ShopModel {
    private final String mName;
    private boolean mIsChecked;

    public ShopModel(String name) {
        mName = name;
    }

    public String getName() {
        return mName;
    }

    public boolean isChecked() {
        return mIsChecked;
    }

    public void setChecked(boolean checked) {
        mIsChecked = checked;
    }
}
