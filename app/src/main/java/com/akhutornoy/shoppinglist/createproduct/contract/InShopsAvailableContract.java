package com.akhutornoy.shoppinglist.createproduct.contract;

import com.akhutornoy.shoppinglist.base.presenter.BasePresenter;
import com.akhutornoy.shoppinglist.base.view.LoadableView;
import com.akhutornoy.shoppinglist.createproduct.model.ShopModel;

import java.util.List;

public interface InShopsAvailableContract {
    interface View extends LoadableView<List<ShopModel>> {
        void showNextScreen();
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void loadShops();
        public abstract void saveProductInShops(String productName, List<ShopModel> selectedShops);
    }
}
