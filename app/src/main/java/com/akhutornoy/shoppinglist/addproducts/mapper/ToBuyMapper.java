package com.akhutornoy.shoppinglist.addproducts.mapper;

import com.akhutornoy.shoppinglist.base.Mapper;
import com.akhutornoy.shoppinglist.domain.Product;
import com.akhutornoy.shoppinglist.domain.ToBuy;

public class ToBuyMapper extends Mapper<Product, ToBuy> {
    @Override
    public ToBuy map(Product source) {
        return new ToBuy(source.getName(), source.getMeasureType(), source.getDefaultQuantity());
    }

    @Override
    public Product mapInverse(ToBuy source) {
        return new Product(source.getName(), source.getQuantity(), source.getUnit());
    }
}
