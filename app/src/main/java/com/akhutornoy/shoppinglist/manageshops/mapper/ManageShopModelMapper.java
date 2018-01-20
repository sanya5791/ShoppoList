package com.akhutornoy.shoppinglist.manageshops.mapper;

import com.akhutornoy.shoppinglist.base.Mapper;
import com.akhutornoy.shoppinglist.domain.Shop;
import com.akhutornoy.shoppinglist.manageshops.model.ManageShopModel;

public class ManageShopModelMapper extends Mapper<Shop, ManageShopModel> {
    @Override
    public ManageShopModel map(Shop source) {
        return new ManageShopModel("0", source.getName());
    }

    @Override
    public Shop mapInverse(ManageShopModel source) {
        return new Shop(source.getName());
    }
}
