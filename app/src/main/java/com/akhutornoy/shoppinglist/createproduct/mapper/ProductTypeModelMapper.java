package com.akhutornoy.shoppinglist.createproduct.mapper;

import com.akhutornoy.shoppinglist.base.Mapper;
import com.akhutornoy.shoppinglist.createproduct.model.ProductTypeModel;
import com.akhutornoy.shoppinglist.domain.ProductType;

public class ProductTypeModelMapper extends Mapper<ProductType, ProductTypeModel> {
    @Override
    public ProductTypeModel map(ProductType source) {
        return new ProductTypeModel(source.getName(), source.getSortNumber());
    }

    @Override
    public ProductType mapInverse(ProductTypeModel source) {
        throw new UnsupportedOperationException();
    }
}
