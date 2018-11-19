package com.akhutornoy.shoppinglist.ui.createproduct_onescreen.model;

import java.util.List;

public class CreateProductOutputModel {
    private final String name;
    private final String quantityTypeSelected;
    private final String defaultQuantity;
    private final List<String> shopsSelected;

    private CreateProductOutputModel(Builder builder) {
        this.name = builder.name;
        this.quantityTypeSelected = builder.quantityTypeSelected;
        this.defaultQuantity = builder.defaultQuantity;
        this.shopsSelected = builder.shopsSelected;
    }

    public String getName() {
        return name;
    }

    public String getQuantityTypeSelected() {
        return quantityTypeSelected;
    }

    public String getDefaultQuantity() {
        return defaultQuantity;
    }

    public List<String> getShopsSelected() {
        return shopsSelected;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String name;
        private String quantityTypeSelected;
        private String defaultQuantity;
        private List<String> shopsSelected;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setQuantityTypeSelected(String quantityTypeSelected) {
            this.quantityTypeSelected = quantityTypeSelected;
            return this;
        }

        public Builder setDefaultQuantity(String defaultQuantity) {
            this.defaultQuantity = defaultQuantity;
            return this;
        }

        public Builder setShopsSelected(List<String> shopsSelected) {
            this.shopsSelected = shopsSelected;
            return this;
        }

        public CreateProductOutputModel build() {
            return new CreateProductOutputModel(this);
        }
    }
}
