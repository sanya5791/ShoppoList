package com.akhutornoy.shoppinglist.shops.presenter;

import com.akhutornoy.shoppinglist.shops.contract.ShopsContract;
import com.akhutornoy.shoppinglist.shops.model.ShopModel;

import java.util.ArrayList;
import java.util.List;

public class ShopsPresenter extends ShopsContract.Presenter {
    @Override
    public void loadShops() {
        getView().onDataLoaded(getMockShopTypes());
    }

    private List<ShopModel> getMockShopTypes() {
        List<ShopModel> mocks = new ArrayList<>();
        mocks.add(new ShopModel("1",  "Supermarket"));
        mocks.add(new ShopModel("2", "Market"));
        return mocks;
    }
}
