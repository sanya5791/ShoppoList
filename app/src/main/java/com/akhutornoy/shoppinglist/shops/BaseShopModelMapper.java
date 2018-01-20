package com.akhutornoy.shoppinglist.shops;

import com.akhutornoy.shoppinglist.base.Mapper;
import com.akhutornoy.shoppinglist.domain.Shop;

public class BaseShopModelMapper extends Mapper<Shop, BaseShopModel> {
    @Override
    public BaseShopModel map(Shop source) {
        return new BaseShopModel("0", source.getName());
    }

    @Override
    public Shop mapInverse(BaseShopModel source) {
        return new Shop(source.getName());
    }
}
