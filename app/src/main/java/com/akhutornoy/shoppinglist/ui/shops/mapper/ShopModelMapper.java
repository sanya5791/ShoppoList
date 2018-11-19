package com.akhutornoy.shoppinglist.ui.shops.mapper;

import com.akhutornoy.shoppinglist.base.Mapper;
import com.akhutornoy.shoppinglist.domain.Shop;
import com.akhutornoy.shoppinglist.ui.shops.model.ShopModel;

public class ShopModelMapper extends Mapper<Shop, ShopModel> {
    private String currentShop;

    public void setCurrentShop(String currentShop) {
        this.currentShop = currentShop;
    }

    @Override
    public ShopModel map(Shop source) {
        String name = source.getName();
        int sortNumber = source.getSortNumber();
        return currentShop == null
                ? new ShopModel(name, sortNumber)
                : new ShopModel(name, sortNumber, isCurrentShop(name));
    }

    private boolean isCurrentShop(String name) {
        return currentShop.equals(name);
    }

    @Override
    public Shop mapInverse(ShopModel source) {
        return new Shop(source.getName());
    }
}
