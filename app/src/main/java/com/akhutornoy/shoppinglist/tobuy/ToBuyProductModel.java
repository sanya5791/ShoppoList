package com.akhutornoy.shoppinglist.tobuy;

public class ToBuyProductModel {
    private String id;
    private String name;
    private boolean isBought;

    private ToBuyProductModel(Builder builder) {
        id = builder.id;
        name = builder.name;
        isBought = builder.isBought;
    }

    public void setBought(boolean bought) {
        isBought = bought;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isBought() {
        return isBought;
    }

    public static class Builder {
        private String id;
        private String name;
        private boolean isBought;

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setIsBought(boolean isBought) {
            this.isBought = isBought;
            return this;
        }

        public ToBuyProductModel build() {
            return new ToBuyProductModel(this);
        }
    }
}
