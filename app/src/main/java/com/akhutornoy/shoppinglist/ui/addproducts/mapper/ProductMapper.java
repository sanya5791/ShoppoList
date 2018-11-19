package com.akhutornoy.shoppinglist.ui.addproducts.mapper;

import com.akhutornoy.shoppinglist.ui.addproducts.model.AddProductModel;
import com.akhutornoy.shoppinglist.base.Mapper;
import com.akhutornoy.shoppinglist.domain.Product;

public class ProductMapper extends Mapper<AddProductModel, Product> {
    @Override
    public Product map(AddProductModel source) {
        throw new IllegalArgumentException("Not implemented");
    }

    @Override
    public AddProductModel mapInverse(Product source) {
        return new AddProductModel.Builder()
                .setName(source.getName())
                .setUnit(source.getMeasureType())
                .setQuantity(source.getDefaultQuantity())
                .build();
    }
}
