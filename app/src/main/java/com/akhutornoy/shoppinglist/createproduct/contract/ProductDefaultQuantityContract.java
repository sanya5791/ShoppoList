package com.akhutornoy.shoppinglist.createproduct.contract;

import com.akhutornoy.shoppinglist.base.presenter.BasePresenter;
import com.akhutornoy.shoppinglist.base.view.ProgressView;
import com.akhutornoy.shoppinglist.domain.Product;

public interface ProductDefaultQuantityContract {
    interface View extends ProgressView {
        void setProduct(Product product);
        void showNextScreen();
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void onProductLoad(String productName);
        public abstract void saveDefaultQuantity(String productName, String quantity);
    }
}
