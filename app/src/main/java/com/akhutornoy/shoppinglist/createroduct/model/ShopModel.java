package com.akhutornoy.shoppinglist.createroduct.model;

import com.akhutornoy.shoppinglist.base.model.BaseShopModel;

public class ShopModel extends BaseShopModel {
    private boolean mIsChecked;

    public ShopModel(String name, int sortNumber) {
        super(name, sortNumber);
    }

    public boolean isChecked() {
        return mIsChecked;
    }

    public void setChecked(boolean checked) {
        mIsChecked = checked;
    }
}
