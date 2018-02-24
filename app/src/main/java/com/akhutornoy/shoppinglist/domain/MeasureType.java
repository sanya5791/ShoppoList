package com.akhutornoy.shoppinglist.domain;

import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;

@Entity(primaryKeys = "name")
public class MeasureType {
    @NonNull
    private String name;
    private int sortNumber;

    public MeasureType(@NonNull String name) {
        this.name = name;
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
