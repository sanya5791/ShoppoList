package com.akhutornoy.shoppinglist.addproducts.mapper;

import com.akhutornoy.shoppinglist.base.Mapper;
import com.akhutornoy.shoppinglist.domain.Product;
import com.akhutornoy.shoppinglist.domain.ToBuy;

public class ToBuyMapper extends Mapper<Product, ToBuy> {
    private String shopName;

    @Override
    public ToBuy map(Product source) {
        if (shopName == null) {
            throw new IllegalArgumentException("'shopName' field should be preset first");
        }
        return new ToBuy(source.getName(), source.getMeasureType(), source.getDefaultQuantity(), shopName);
    }

    @Override
    public Product mapInverse(ToBuy source) {
        return new Product(source.getName(), source.getQuantity(), source.getUnit());
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
}
