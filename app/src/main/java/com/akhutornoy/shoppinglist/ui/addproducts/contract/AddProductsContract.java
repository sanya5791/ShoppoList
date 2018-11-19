package com.akhutornoy.shoppinglist.ui.addproducts.contract;

import com.akhutornoy.shoppinglist.ui.addproducts.model.AddProductModel;
import com.akhutornoy.shoppinglist.ui.base.presenter.BasePresenter;
import com.akhutornoy.shoppinglist.ui.base.view.LoadableView;

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
