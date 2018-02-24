package com.akhutornoy.shoppinglist.createproduct.contract;

import com.akhutornoy.shoppinglist.base.presenter.BasePresenter;
import com.akhutornoy.shoppinglist.base.view.LoadableView;
import com.akhutornoy.shoppinglist.domain.Product;

public interface ProductDefaultQuantityContract {
    interface View extends LoadableView<Product> {
        void showNextScreen();
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void onDataLoad(String productName);
        public abstract void saveDefaultQuantity(String productName, String quantity);
    }
}
