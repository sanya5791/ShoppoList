package com.akhutornoy.shoppinglist.ui.createproduct_onescreen.mapper;

import com.akhutornoy.shoppinglist.base.Mapper;
import com.akhutornoy.shoppinglist.ui.createproduct_onescreen.model.CreateProductOutputModel;
import com.akhutornoy.shoppinglist.domain.Product;

public class ProductMapper extends Mapper<CreateProductOutputModel, Product> {

    @Override
    public Product map(CreateProductOutputModel source) {
        return new Product(source.getName(), source.getDefaultQuantity(), source.getQuantityTypeSelected());
    }

    @Override
    public CreateProductOutputModel mapInverse(Product source) {
        return null;
    }
}
