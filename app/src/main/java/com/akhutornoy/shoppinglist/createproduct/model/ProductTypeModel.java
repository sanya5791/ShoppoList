package com.akhutornoy.shoppinglist.createproduct.model;

import android.support.annotation.NonNull;

public class ProductTypeModel {
    private int mSortNumber;
    private String mName;
    private boolean mIsChecked;

    public ProductTypeModel(@NonNull String name, int sortNumber) {
        mName = name;
        mSortNumber = sortNumber;
    }

    public String getName() {
        return mName;
    }

    public int getSortNumber() {
        return mSortNumber;
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
