package com.akhutornoy.shoppinglist.domain;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.support.annotation.NonNull;

@Entity(primaryKeys = {"product", "shop"},
        foreignKeys = {@ForeignKey(entity = Product.class,
                                    parentColumns = "name",
                                    childColumns = "product"),
                        @ForeignKey(entity = Shop.class,
                                    parentColumns = "name",
                                    childColumns = "shop")}
)
public class ProductInShop {
    @NonNull
    private String product;
    @NonNull
    private String shop;

    public ProductInShop(@NonNull String product, @NonNull String shop) {
        this.product = product;
        this.shop = shop;
    }

    @NonNull
    public String getProduct() {
        return product;
    }

    public void setProduct(@NonNull String product) {
        this.product = product;
    }

    @NonNull
    public String getShop() {
        return shop;
    }

    public void setShop(@NonNull String shop) {
        this.shop = shop;
    }
}
