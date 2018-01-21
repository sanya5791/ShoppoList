package com.akhutornoy.shoppinglist.domain;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

@Entity(primaryKeys = "name",
        foreignKeys = @ForeignKey(entity = ProductType.class,
                                    parentColumns = "name",
                                    childColumns = "productType")
)
public class Product {
    @NonNull
    private String name;
    @Nullable
    private String defaultQuantity;
    @Nullable
    private String productType;

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

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }
}
