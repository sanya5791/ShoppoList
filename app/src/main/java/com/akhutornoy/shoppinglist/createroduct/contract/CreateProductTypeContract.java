package com.akhutornoy.shoppinglist.createroduct.contract;

import com.akhutornoy.shoppinglist.base.presenter.BasePresenter;
import com.akhutornoy.shoppinglist.base.view.LoadableView;
import com.akhutornoy.shoppinglist.createroduct.model.ProductTypeModel;

import java.util.List;

public interface CreateProductTypeContract {
    interface View extends LoadableView<List<ProductTypeModel>> {}

    abstract class Presenter extends BasePresenter<View> {
        public abstract void loadTypes();
    }
}
