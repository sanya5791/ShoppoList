package com.akhutornoy.shoppinglist.ui.shops.displayshops.contract;

import com.akhutornoy.shoppinglist.ui.base.presenter.BasePresenter;
import com.akhutornoy.shoppinglist.ui.base.view.LoadableView;
import com.akhutornoy.shoppinglist.ui.shops.model.ShopModel;

import java.util.List;

public interface ShopsContract {
    interface View extends LoadableView<List<ShopModel>> {
        void setCurrentShop(ShopModel currentShop);
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void loadShops();
        public abstract void onShopSelected(ShopModel selectedShop);
    }
}
