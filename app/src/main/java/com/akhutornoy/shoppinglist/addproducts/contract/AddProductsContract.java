package com.akhutornoy.shoppinglist.addproducts.contract;

import com.akhutornoy.shoppinglist.addproducts.model.AddProductModel;
import com.akhutornoy.shoppinglist.base.presenter.BasePresenter;
import com.akhutornoy.shoppinglist.base.view.LoadableView;

import java.util.List;

public interface AddProductsContract {
    interface View extends LoadableView<List<AddProductModel>> {
        void finishScreen();
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void loadProducts();
        public abstract void saveSelectedProducts(List<AddProductModel> selectedProducts);
    }
}
