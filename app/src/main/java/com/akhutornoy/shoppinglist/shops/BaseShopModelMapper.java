package com.akhutornoy.shoppinglist.shops;

import com.akhutornoy.shoppinglist.base.Mapper;
import com.akhutornoy.shoppinglist.base.model.ItemModel;
import com.akhutornoy.shoppinglist.domain.Shop;

public class BaseShopModelMapper extends Mapper<Shop, ItemModel> {
    @Override
    public ItemModel map(Shop source) {
        return new ItemModel(source.getName(), source.getSortNumber());
    }

    @Override
    public Shop mapInverse(ItemModel source) {
        return new Shop(source.getName());
    }
}
