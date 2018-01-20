package com.akhutornoy.shoppinglist.createproduct.presenter;

import com.akhutornoy.shoppinglist.createproduct.contract.SelectProductTypeContract;
import com.akhutornoy.shoppinglist.createproduct.model.ProductTypeModel;

import java.util.ArrayList;
import java.util.List;

public class CreateProductTypePresenter extends SelectProductTypeContract.Presenter {
    @Override
    public void loadTypes() {
        getView().onDataLoaded(getMockTypes());
    }

    private List<ProductTypeModel> getMockTypes() {
        List<ProductTypeModel> products = new ArrayList<>();
        products.add(new ProductTypeModel("kg"));
        products.add(new ProductTypeModel("mg"));
        products.add(new ProductTypeModel("l"));
        products.add(new ProductTypeModel("pkg"));
        return products;
    }
}
