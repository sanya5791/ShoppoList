package com.akhutornoy.shoppinglist.manageshops.presenter;

import com.akhutornoy.shoppinglist.manageshops.contract.ManageShopsContract;
import com.akhutornoy.shoppinglist.manageshops.model.ManageShopModel;

import java.util.ArrayList;
import java.util.List;

public class ManageShopsPresenter extends ManageShopsContract.Presenter {

    @Override
    public void loadShops() {
        getView().onDataLoaded(getMockShops());
    }

    public List<ManageShopModel> getMockShops() {
        List<ManageShopModel> mocks = new ArrayList<>();
        mocks.add(new ManageShopModel("1",  "Supermarket"));
        mocks.add(new ManageShopModel("2", "Market"));
        return mocks;
    }
}
