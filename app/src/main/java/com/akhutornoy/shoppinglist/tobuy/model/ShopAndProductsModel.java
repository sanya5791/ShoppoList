package com.akhutornoy.shoppinglist.tobuy.model;

import com.akhutornoy.shoppinglist.domain.ToBuy;

import java.util.List;

public class ShopAndProductsModel {
    private String shopName;
    private List<ToBuyProductModel> products;

    public ShopAndProductsModel(String shopName) {
        this.shopName = shopName;
    }

    public ShopAndProductsModel(String shopName, List<ToBuyProductModel> products) {
        this.shopName = shopName;
        this.products = products;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public List<ToBuyProductModel> getProducts() {
        return products;
    }

    public void setProducts(List<ToBuyProductModel> products) {
        this.products = products;
    }
}
