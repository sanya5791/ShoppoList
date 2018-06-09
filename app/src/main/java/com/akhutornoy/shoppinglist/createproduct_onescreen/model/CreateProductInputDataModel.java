package com.akhutornoy.shoppinglist.createproduct_onescreen.model;

import com.akhutornoy.shoppinglist.domain.MeasureType;
import com.akhutornoy.shoppinglist.domain.Shop;

import java.util.List;

public class CreateProductInputDataModel {
    private final List<MeasureType> measureTypes;
    private final List<Shop> shopsAvailable;

    public CreateProductInputDataModel(List<MeasureType> measureTypes, List<Shop> shopsAvailable) {
        this.measureTypes = measureTypes;
        this.shopsAvailable = shopsAvailable;
    }

    public List<MeasureType> getMeasureTypes() {
        return measureTypes;
    }

    public List<Shop> getShopsAvailable() {
        return shopsAvailable;
    }
}
