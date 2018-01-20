package com.akhutornoy.shoppinglist.shops.displayshops.contract;

import com.akhutornoy.shoppinglist.base.model.BaseShopModel;
import com.akhutornoy.shoppinglist.base.presenter.BasePresenter;
import com.akhutornoy.shoppinglist.base.view.LoadableView;

import java.util.List;

public interface ShopsContract {
    interface View extends LoadableView<List<BaseShopModel>> {}

    abstract class Presenter extends BasePresenter<View> {
        public abstract void loadShops();
    }
}
