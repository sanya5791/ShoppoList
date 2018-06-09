package com.akhutornoy.shoppinglist.createproduct_onescreen.model;

import java.util.List;

public class CreateProductOutputModel {
    private final String name;
    private final String quantityTypeSelected;
    private final String defaultQuantity;
    private final List<String> shopsSelected;

    public CreateProductOutputModel(String name, String quantityTypeSelected, String defaultQuantity, List<String> shopsSelected) {
        this.name = name;
        this.quantityTypeSelected = quantityTypeSelected;
        this.defaultQuantity = defaultQuantity;
        this.shopsSelected = shopsSelected;
    }
}
