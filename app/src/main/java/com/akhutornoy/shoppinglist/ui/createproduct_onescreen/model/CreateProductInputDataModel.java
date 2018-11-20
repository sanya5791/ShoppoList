package com.akhutornoy.shoppinglist.ui.createproduct_onescreen.model;

import com.akhutornoy.shoppinglist.domain.MeasureType;
import com.akhutornoy.shoppinglist.domain.Shop;

import java.util.List;

public class CreateProductInputDataModel {
    private final List<MeasureType> measureTypes;
    private final List<Shop> shopsAvailable;
    private final String currentShop;

    public CreateProductInputDataModel(List<MeasureType> measureTypes, List<Shop> shopsAvailable, String currentShop) {
        this.measureTypes = measureTypes;
        this.shopsAvailable = shopsAvailable;
        this.currentShop = currentShop;
    }

    public List<MeasureType> getMeasureTypes() {
        return measureTypes;
    }

    public List<Shop> getShopsAvailable() {
        return shopsAvailable;
    }

    public String getCurrentShop() {
        return currentShop;
    }
}
