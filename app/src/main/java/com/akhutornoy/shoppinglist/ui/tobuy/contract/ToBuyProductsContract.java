package com.akhutornoy.shoppinglist.ui.tobuy.contract;

import com.akhutornoy.shoppinglist.ui.base.presenter.BasePresenter;
import com.akhutornoy.shoppinglist.ui.base.view.LoadableView;
import com.akhutornoy.shoppinglist.ui.tobuy.model.ToBuyModel;
import com.akhutornoy.shoppinglist.ui.tobuy.model.ToBuyProductModel;

import java.util.List;

public interface ToBuyProductsContract {
    interface View extends LoadableView<ToBuyModel> {
        void shareList(String shareText);
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void loadProducts();
        public abstract void updateState(ToBuyProductModel toBuyModel);
        public abstract void onShareListClicked(List<ToBuyProductModel> products);
    }
}
