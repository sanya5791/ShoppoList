package com.akhutornoy.shoppinglist.createroduct.presenter;

import com.akhutornoy.shoppinglist.createroduct.contract.InShopsAvailableContract;
import com.akhutornoy.shoppinglist.createroduct.model.ShopModel;

import java.util.ArrayList;
import java.util.List;

public class InShopsAvailablePresenter extends InShopsAvailableContract.Presenter {
    @Override
    public void loadShops() {
        getView().onDataLoaded(getMockShops());
    }

    private List<ShopModel> getMockShops() {
        List<ShopModel> products = new ArrayList<>();
        products.add(new ShopModel("Market"));
        products.add(new ShopModel("ATB"));
        products.add(new ShopModel("Silpo"));
        products.add(new ShopModel("Supermarket"));
        return products;
    }
}
