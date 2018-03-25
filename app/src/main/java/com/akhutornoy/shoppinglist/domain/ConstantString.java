package com.akhutornoy.shoppinglist.domain;

import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;

@Entity(primaryKeys = "name")
public class ConstantString {
    public static final String SHOP_NAME_ALL = "ShopNameAll";
    @NonNull
    private String name;
    @NonNull
    private String value;

    public ConstantString(@NonNull String name, @NonNull String value) {
        this.name = name;
        this.value = value;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public String getValue() {
        return value;
    }
}
