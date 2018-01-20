package com.akhutornoy.shoppinglist.createproduct.mapper;

import com.akhutornoy.shoppinglist.base.Mapper;
import com.akhutornoy.shoppinglist.createproduct.model.ShopModel;
import com.akhutornoy.shoppinglist.domain.Shop;

public class ShopModelMapper extends Mapper<Shop, ShopModel> {
    @Override
    public ShopModel map(Shop source) {
        return new ShopModel(source.getName(), source.getSortNumber());
    }

    @Override
    public Shop mapInverse(ShopModel source) {
        return new Shop(source.getName());
    }
}
