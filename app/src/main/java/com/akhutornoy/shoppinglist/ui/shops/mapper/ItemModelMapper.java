package com.akhutornoy.shoppinglist.ui.shops.mapper;

import com.akhutornoy.shoppinglist.base.Mapper;
import com.akhutornoy.shoppinglist.ui.base.model.ItemModel;
import com.akhutornoy.shoppinglist.domain.Shop;

public class ItemModelMapper extends Mapper<Shop, ItemModel> {
    @Override
    public ItemModel map(Shop source) {
        return new ItemModel(source.getName(), source.getSortNumber());
    }

    @Override
    public Shop mapInverse(ItemModel source) {
        return new Shop(source.getName());
    }
}
