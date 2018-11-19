package com.akhutornoy.shoppinglist.ui.tobuy.model;

import java.util.List;

public class ToBuyModel {
    private List<ToBuyProductModel> toBuyModels;
    private String currentShop;

    public ToBuyModel(List<ToBuyProductModel> toBuyModels, String currentShop) {
        this.toBuyModels = toBuyModels;
        this.currentShop = currentShop;
    }

    public List<ToBuyProductModel> getToBuyModels() {
        return toBuyModels;
    }

    public String getCurrentShop() {
        return currentShop;
    }
}
