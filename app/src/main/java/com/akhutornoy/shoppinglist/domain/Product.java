package com.akhutornoy.shoppinglist.domain;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

@Entity(primaryKeys = "name",
        foreignKeys = @ForeignKey(entity = MeasureType.class,
                                    parentColumns = "name",
                                    childColumns = "measureType")
)
public class Product {
    @NonNull
    private String name;
    @Nullable
    private String defaultQuantity;
    @Nullable
    private String measureType;

    public Product(@NonNull String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDefaultQuantity() {
        return defaultQuantity;
    }

    public void setDefaultQuantity(String defaultQuantity) {
        this.defaultQuantity = defaultQuantity;
    }

    public String getMeasureType() {
        return measureType;
    }

    public void setMeasureType(String measureType) {
        this.measureType = measureType;
    }
}
