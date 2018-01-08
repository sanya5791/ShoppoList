package com.akhutornoy.shoppinglist.createroduct.contract;

import com.akhutornoy.shoppinglist.base.presenter.BasePresenter;
import com.akhutornoy.shoppinglist.base.view.LoadableView;
import com.akhutornoy.shoppinglist.createroduct.model.ShopModel;

import java.util.List;

public interface InShopsAvailableContract {
    interface View extends LoadableView<List<ShopModel>> {}

    abstract class Presenter extends BasePresenter<View> {
        public abstract void loadShops();
    }
}
