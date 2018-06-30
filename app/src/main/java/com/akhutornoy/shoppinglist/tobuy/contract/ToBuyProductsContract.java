package com.akhutornoy.shoppinglist.tobuy.contract;

import com.akhutornoy.shoppinglist.base.presenter.BasePresenter;
import com.akhutornoy.shoppinglist.base.view.LoadableView;
import com.akhutornoy.shoppinglist.tobuy.model.ToBuyModel;
import com.akhutornoy.shoppinglist.tobuy.model.ToBuyProductModel;

public interface ToBuyProductsContract {
    interface View extends LoadableView<ToBuyModel> {}

    abstract class Presenter extends BasePresenter<View> {
        public abstract void loadProducts();
        public abstract void updateState(ToBuyProductModel toBuyModel);
    }
}
