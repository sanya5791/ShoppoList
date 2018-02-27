package com.akhutornoy.shoppinglist.tobuy.model;

import com.akhutornoy.shoppinglist.base.ProductModel;

public class ToBuyProductModel extends ProductModel {
    private boolean mIsBought;
    private String mShopName;

    private ToBuyProductModel(Builder builder) {
        super(builder);
        mIsBought = builder.mIsBought;
        mShopName = builder.mShopName;
    }

    public void setIsBought(boolean bought) {
        mIsBought = bought;
    }

    public boolean isBought() {
        return mIsBought;
    }

    public String getmShopName() {
        return mShopName;
    }

    public static class Builder extends ProductModel.Builder {
        private boolean mIsBought;
        private String mShopName;

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

        public Builder setIsBought(boolean isBought) {
            this.mIsBought = isBought;
            return this;
        }

        public Builder setShopName(String shopName) {
            this.mShopName = shopName;
            return this;
        }

        public ToBuyProductModel build() {
            return new ToBuyProductModel(this);
        }
    }
}
