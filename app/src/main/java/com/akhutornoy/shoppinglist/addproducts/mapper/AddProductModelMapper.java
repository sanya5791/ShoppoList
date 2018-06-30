package com.akhutornoy.shoppinglist.addproducts.mapper;

import com.akhutornoy.shoppinglist.addproducts.model.AddProductModel;
import com.akhutornoy.shoppinglist.base.Mapper;
import com.akhutornoy.shoppinglist.domain.Product;
import com.akhutornoy.shoppinglist.domain.ToBuy;

import java.util.List;

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

    public List<AddProductModel> map(List<AddProductModel> addProductModels, List<ToBuy> toBuys) {
        for (AddProductModel addProduct : addProductModels) {
            for (ToBuy toBuy : toBuys) {
                if (toBuy.getName().equals(addProduct.getName())) {
                    boolean isAdded = !toBuy.isBought();
                    addProduct.setIsAdded(isAdded);
                    addProduct.setQuantity(toBuy.getQuantity());
                    break;
                }
            }
        }
        return addProductModels;
    }
}
