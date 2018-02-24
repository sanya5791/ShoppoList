package com.akhutornoy.shoppinglist.shops.displayshops.contract;

import com.akhutornoy.shoppinglist.base.presenter.BasePresenter;
import com.akhutornoy.shoppinglist.base.view.LoadableView;
import com.akhutornoy.shoppinglist.createproduct.model.ShopModel;

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
