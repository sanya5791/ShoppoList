package com.akhutornoy.shoppinglist.createproduct.model;

import com.akhutornoy.shoppinglist.base.model.ItemModel;

public class ShopModel extends ItemModel {
    private boolean mIsChecked;

    public ShopModel(String name, int sortNumber) {
        super(name, sortNumber);
    }

    public ShopModel(String name, int sortNumber, boolean isChecked) {
        super(name, sortNumber);
        mIsChecked = isChecked;
    }

    public boolean isChecked() {
        return mIsChecked;
    }

    public void isChecked(boolean checked) {
        mIsChecked = checked;
    }
}
