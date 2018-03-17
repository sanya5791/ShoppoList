package com.akhutornoy.shoppinglist.addproducts.model;

import com.akhutornoy.shoppinglist.base.ProductModel;

public class AddProductModel extends ProductModel {
    private boolean mIsAdded;

    private AddProductModel(Builder builder) {
        super(builder);
        mIsAdded = builder.mIsAdded;
    }

    public boolean isAdded() {
        return mIsAdded;
    }

    public void setIsAdded(boolean added) {
        mIsAdded = added;
    }

    public void setQuantity(String quantity) {
        mQuantity = quantity;
    }

    public static class Builder extends ProductModel.Builder {
        private boolean mIsAdded;

        public Builder setId(String id) {
            super.setId(id);
            return this;
        }

        public Builder setName(String name) {
            super.setName(name);
            return this;
        }

        public Builder setUnit(String unit) {
            super.setUnit(unit);
            return this;
        }

        public Builder setQuantity(String quantity) {
            super.setQuantity(quantity);
            return this;
        }

        public Builder setIsAdded(boolean isAdded) {
            this.mIsAdded = isAdded;
            return this;
        }

        public AddProductModel build() {
            return new AddProductModel(this);
        }
    }
}
