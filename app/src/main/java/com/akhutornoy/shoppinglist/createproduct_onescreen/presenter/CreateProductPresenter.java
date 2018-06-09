package com.akhutornoy.shoppinglist.createproduct_onescreen.presenter;

import com.akhutornoy.shoppinglist.createproduct_onescreen.contract.CreateProductContract;
import com.akhutornoy.shoppinglist.createproduct_onescreen.model.CreateProductInputDataModel;
import com.akhutornoy.shoppinglist.createproduct_onescreen.model.CreateProductOutputModel;
import com.akhutornoy.shoppinglist.domain.AppDatabase;

import java.util.ArrayList;
import java.util.List;

public class CreateProductPresenter extends CreateProductContract.Presenter {

    public CreateProductPresenter(AppDatabase db) {}

    @Override
    public void loadInputData() {
        CreateProductInputDataModel inputDataModel = new CreateProductInputDataModel(
                getMockTypes(), getMockShops()
        );
        getView().onDataLoaded(inputDataModel);
    }

    private List<String> getMockTypes() {
        List<String> types = new ArrayList<>();
        types.add("kg");
        types.add("gr");
        types.add("bot");
        types.add("m");
        types.add("sm");
        return types;
    }

    private List<String> getMockShops() {
        List<String> mockShops = new ArrayList<>();
        mockShops.add("Shop 1");
        mockShops.add("Shop 2");
        mockShops.add("Shop 3");
        return mockShops;
    }

    @Override
    public void createProduct(CreateProductOutputModel productModel) {}
}
