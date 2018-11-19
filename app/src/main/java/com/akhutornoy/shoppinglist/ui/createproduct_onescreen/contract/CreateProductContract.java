package com.akhutornoy.shoppinglist.ui.createproduct_onescreen.contract;

import com.akhutornoy.shoppinglist.ui.base.presenter.BasePresenter;
import com.akhutornoy.shoppinglist.ui.base.view.LoadableView;
import com.akhutornoy.shoppinglist.ui.createproduct_onescreen.model.CreateProductInputDataModel;
import com.akhutornoy.shoppinglist.ui.createproduct_onescreen.model.CreateProductOutputModel;

public interface CreateProductContract {
    interface View extends LoadableView<CreateProductInputDataModel> {
        void onProductCreated();
        void showProductAlreadyExistsError(String productName);
    }

    abstract class Presenter<V extends View> extends BasePresenter<V> {
        public abstract void loadInputData();
        public abstract void createProduct(CreateProductOutputModel productModel);
    }
}
