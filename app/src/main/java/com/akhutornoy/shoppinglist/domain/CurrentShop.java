package com.akhutornoy.shoppinglist.domain;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.support.annotation.NonNull;

@Entity(primaryKeys = "name",
        foreignKeys = @ForeignKey(
                entity = Shop.class,
                parentColumns = "name",
                childColumns = "name"))
public class CurrentShop {
    @NonNull
    private String name;

    public CurrentShop(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getName() {
        return name;
    }
}
