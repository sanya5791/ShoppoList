package com.akhutornoy.shoppinglist.base.model;

public class BaseShopModel {
    private final String id;
    private final String name;

    public BaseShopModel(String id, String name) {
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
