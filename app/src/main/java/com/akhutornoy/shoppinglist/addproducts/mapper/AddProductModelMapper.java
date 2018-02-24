package com.akhutornoy.shoppinglist.addproducts.mapper;

import com.akhutornoy.shoppinglist.addproducts.model.AddProductModel;
import com.akhutornoy.shoppinglist.base.Mapper;
import com.akhutornoy.shoppinglist.domain.Product;

public class AddProductModelMapper extends Mapper<Product, AddProductModel> {
    @Override
    public AddProductModel map(Product source) {
        return new AddProductModel.Builder()
                .setName(source.getName())
                .setUnit(source.getMeasureType())
                .setQuantity(source.getDefaultQuantity())
                .build();
    }

    @Override
    public Product mapInverse(AddProductModel source) {
        throw new IllegalArgumentException("Not implemented");
    }
}
