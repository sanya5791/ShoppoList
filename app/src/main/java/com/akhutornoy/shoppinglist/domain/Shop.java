package com.akhutornoy.shoppinglist.domain;

import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;

@Entity(primaryKeys = "name")
public class Shop {
    public static final int NO_SORT_NUMBER = 0;

    @NonNull
    private String name;
    private int sortNumber;

    public Shop(@NonNull String name) {
        this.name = name;
        this.sortNumber = NO_SORT_NUMBER;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSortNumber() {
        return sortNumber;
    }

    public void setSortNumber(int sortNumber) {
        this.sortNumber = sortNumber;
    }
}
