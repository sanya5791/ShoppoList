package com.akhutornoy.shoppinglist.addproducts.presenter;

import com.akhutornoy.shoppinglist.addproducts.contract.AddProductsContract;
import com.akhutornoy.shoppinglist.addproducts.model.AddProductModel;

import java.util.ArrayList;
import java.util.List;

public class AddProductsPresenter extends AddProductsContract.Presenter {
    @Override
    public void loadProducts() {
        getView().onDataLoaded(getMockProducts());
    }

    private List<AddProductModel> getMockProducts() {
        List<AddProductModel> products = new ArrayList<>();
        products.add(new AddProductModel.Builder()
                .setId("1")
                .setIsAdded(false)
                .setName("Melon")
                .setQuantity("1")
                .setUnit("kg")
                .build());
        products.add(new AddProductModel.Builder()
                .setId("2")
                .setIsAdded(true)
                .setName("Milk")
                .setQuantity("1")
                .setUnit("l")
                .build());
        products.add(new AddProductModel.Builder()
                .setId("3")
                .setIsAdded(true)
                .setName("Bread")
                .setQuantity("1")
                .setUnit("p")
                .build());
        products.add(new AddProductModel.Builder()
                .setId("4")
                .setIsAdded(false)
                .setName("Cheese")
                .setQuantity("300")
                .setUnit("gr")
                .build());
        return products;
    }
}
