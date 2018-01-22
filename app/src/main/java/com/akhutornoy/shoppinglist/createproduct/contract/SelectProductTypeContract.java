package com.akhutornoy.shoppinglist.createproduct.contract;

import com.akhutornoy.shoppinglist.base.presenter.BasePresenter;
import com.akhutornoy.shoppinglist.base.view.LoadableView;
import com.akhutornoy.shoppinglist.createproduct.model.ProductTypeModel;

import java.util.List;

public interface SelectProductTypeContract {
    interface View extends LoadableView<List<ProductTypeModel>> {
        void onTypeSet();
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void loadTypes(String productName);
        public abstract void setType(String productName, String selectedProductType);
    }
}
