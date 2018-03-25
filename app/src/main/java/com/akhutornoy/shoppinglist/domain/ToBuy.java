package com.akhutornoy.shoppinglist.domain;

import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;

@Entity(primaryKeys = {"name", "unit", "shopName"})
public class ToBuy {
    @NonNull
    private String name;
    @NonNull
    private String unit;
    private String quantity;
    private boolean isBought;
    @NonNull
    private String shopName;

    public ToBuy(@NonNull String name, @NonNull String unit, String quantity, @NonNull String shopName) {
        this.name = name;
        this.unit = unit;
        this.quantity = quantity;
        this.shopName = shopName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public boolean isBought() {
        return isBought;
    }

    public void setBought(boolean bought) {
        isBought = bought;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
}
