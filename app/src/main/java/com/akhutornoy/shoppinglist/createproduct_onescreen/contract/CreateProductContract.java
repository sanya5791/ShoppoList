package com.akhutornoy.shoppinglist.createproduct_onescreen.contract;

import com.akhutornoy.shoppinglist.base.presenter.BasePresenter;
import com.akhutornoy.shoppinglist.base.view.LoadableView;
import com.akhutornoy.shoppinglist.createproduct_onescreen.model.CreateProductInputDataModel;
import com.akhutornoy.shoppinglist.createproduct_onescreen.model.CreateProductOutputModel;

public interface CreateProductContract {
    interface View extends LoadableView<CreateProductInputDataModel> {
        void onProductCreated();
        void showProductAlreadyExistsError(String productName);
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void loadInputData();
        public abstract void createProduct(CreateProductOutputModel productModel);
    }
}
