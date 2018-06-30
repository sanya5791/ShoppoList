package com.akhutornoy.shoppinglist.base;

import java.util.Objects;

public class ProductModel {
    protected String mId;
    protected String mName;
    protected String mUnit;
    protected String mQuantity;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductModel that = (ProductModel) o;
        return Objects.equals(mId, that.mId) &&
                Objects.equals(mName, that.mName) &&
                Objects.equals(mUnit, that.mUnit) &&
                Objects.equals(mQuantity, that.mQuantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mId, mName, mUnit, mQuantity);
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
