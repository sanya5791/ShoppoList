package com.akhutornoy.shoppinglist.manageshops.contract;

import com.akhutornoy.shoppinglist.base.presenter.BasePresenter;
import com.akhutornoy.shoppinglist.base.view.LoadableView;
import com.akhutornoy.shoppinglist.manageshops.model.ManageShopModel;

import java.util.List;

public interface ManageShopsContract {
    interface View extends LoadableView<List<ManageShopModel>> {}

    abstract class Presenter extends BasePresenter<View> {
        public abstract void loadShops();
    }
}
