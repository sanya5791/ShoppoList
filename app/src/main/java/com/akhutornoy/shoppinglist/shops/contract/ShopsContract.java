package com.akhutornoy.shoppinglist.shops.contract;

import com.akhutornoy.shoppinglist.base.presenter.BasePresenter;
import com.akhutornoy.shoppinglist.base.view.LoadableView;
import com.akhutornoy.shoppinglist.shops.model.ShopModel;

import java.util.List;

public interface ShopsContract {
    interface View extends LoadableView<List<ShopModel>> {}

    abstract class Presenter extends BasePresenter<View> {
        public abstract void loadShops();
    }
}
