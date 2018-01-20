package com.akhutornoy.shoppinglist.shops;

import com.akhutornoy.shoppinglist.base.Mapper;
import com.akhutornoy.shoppinglist.base.model.BaseShopModel;
import com.akhutornoy.shoppinglist.domain.Shop;

public class BaseShopModelMapper extends Mapper<Shop, BaseShopModel> {
    @Override
    public BaseShopModel map(Shop source) {
        return new BaseShopModel(source.getName(), source.getSortNumber());
    }

    @Override
    public Shop mapInverse(BaseShopModel source) {
        return new Shop(source.getName());
    }
}
