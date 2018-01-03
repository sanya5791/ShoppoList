package com.akhutornoy.shoppinglist.base;

public class ProductModel {
    private String mId;
    private String mName;
    private String mUnit;
    private String mQuantity;

    protected ProductModel(Builder builder) {
        mId = builder.id;
        mName = builder.name;
        mUnit = builder.unit;
        mQuantity = builder.quantity;
    }

    public String getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getUnit() {
        return mUnit;
    }

    public String getQuantity() {
        return mQuantity;
    }

    public static class Builder {
        private String id;
        private String name;
        private String unit;
        private String quantity;

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setUnit(String unit) {
            this.unit = unit;
            return this;
        }

        public Builder setQuantity(String quantity) {
            this.quantity = quantity;
            return this;
        }

        public ProductModel build() {
            return new ProductModel(this);
        }
    }
}
