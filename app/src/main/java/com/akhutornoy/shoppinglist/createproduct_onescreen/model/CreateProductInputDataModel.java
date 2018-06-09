package com.akhutornoy.shoppinglist.createproduct_onescreen.model;

import java.util.List;

public class CreateProductInputDataModel {
    private final List<String> quantityTypesAvailable;
    private final List<String> shopsAvailable;

    public CreateProductInputDataModel(List<String> quantityTypesAvailable, List<String> shopsAvailable) {
        this.quantityTypesAvailable = quantityTypesAvailable;
        this.shopsAvailable = shopsAvailable;
    }

    public List<String> getQuantityTypesAvailable() {
        return quantityTypesAvailable;
    }

    public List<String> getShopsAvailable() {
        return shopsAvailable;
    }
}
