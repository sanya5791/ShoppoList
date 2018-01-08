package com.akhutornoy.shoppinglist.shops.model;

public class ShopModel {
    private final String id;
    private final String name;

    public ShopModel(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
