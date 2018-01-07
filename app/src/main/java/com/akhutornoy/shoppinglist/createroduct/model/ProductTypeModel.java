package com.akhutornoy.shoppinglist.createroduct.model;

import android.support.annotation.NonNull;

public class ProductTypeModel {
    private String mName;
    private boolean mIsChecked;

    public ProductTypeModel(@NonNull String name) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductTypeModel that = (ProductTypeModel) o;

        return mName.equals(that.mName);
    }

    @Override
    public int hashCode() {
        return mName.hashCode();
    }
}
