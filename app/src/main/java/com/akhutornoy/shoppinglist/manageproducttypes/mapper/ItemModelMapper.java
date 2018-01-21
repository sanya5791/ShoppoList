package com.akhutornoy.shoppinglist.manageproducttypes.mapper;

import com.akhutornoy.shoppinglist.base.Mapper;
import com.akhutornoy.shoppinglist.base.model.ItemModel;
import com.akhutornoy.shoppinglist.domain.ProductType;

public class ItemModelMapper extends Mapper<ProductType, ItemModel> {
    @Override
    public ItemModel map(ProductType source) {
        return new ItemModel(source.getName(), source.getSortNumber());
    }

    @Override
    public ProductType mapInverse(ItemModel source) {
        return new ProductType(source.getName());
    }
}
