package com.akhutornoy.shoppinglist.addproducts.mapper;

import com.akhutornoy.shoppinglist.addproducts.model.AddProductModel;
import com.akhutornoy.shoppinglist.base.Mapper;
import com.akhutornoy.shoppinglist.domain.ToBuy;

public class ToBuyMapper extends Mapper<AddProductModel, ToBuy> {
    private String shopName;

    @Override
    public ToBuy map(AddProductModel source) {
        if (shopName == null) {
            throw new IllegalArgumentException("'shopName' field should be preset first");
        }
        return new ToBuy(source.getName(), source.getUnit(), source.getQuantity(), shopName, false);
    }

    @Override
    public AddProductModel mapInverse(ToBuy source) {
        return new AddProductModel.Builder()
                .setName(source.getName())
                .setQuantity(source.getQuantity())
                .setUnit(source.getUnit())
                .build();
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
}
