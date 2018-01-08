package com.akhutornoy.shoppinglist.tobuy.presenter;

import com.akhutornoy.shoppinglist.tobuy.contract.ToBuyProductsContract;
import com.akhutornoy.shoppinglist.tobuy.model.ToBuyProductModel;

import java.util.ArrayList;
import java.util.List;

public class ToBuyProductsPresenter extends ToBuyProductsContract.Presenter {
    @Override
    public void loadProducts() {
        getView().onDataLoaded(getMockProducts());
    }

    private List<ToBuyProductModel> getMockProducts() {
        List<ToBuyProductModel> products = new ArrayList<>();
        products.add(new ToBuyProductModel.Builder()
                .setId("1")
                .setIsBought(false)
                .setName("Melon")
                .setQuantity("1")
                .setUnit("kg")
                .build());
        products.add(new ToBuyProductModel.Builder()
                .setId("2")
                .setIsBought(true)
                .setName("Milk")
                .setQuantity("1")
                .setUnit("l")
                .build());
        products.add(new ToBuyProductModel.Builder()
                .setId("3")
                .setIsBought(true)
                .setName("Bread")
                .setQuantity("1")
                .setUnit("p")
                .build());
        products.add(new ToBuyProductModel.Builder()
                .setId("4")
                .setIsBought(false)
                .setName("Cheese")
                .setQuantity("300")
                .setUnit("gr")
                .build());
        return products;
    }
}
