package com.akhutornoy.shoppinglist.ui.base.contract;

import com.akhutornoy.shoppinglist.ui.base.model.ItemModel;
import com.akhutornoy.shoppinglist.ui.base.presenter.BasePresenter;
import com.akhutornoy.shoppinglist.ui.base.view.LoadableView;

import java.util.List;

public interface BaseManageItemsContract {
    interface View<T extends ItemModel> extends LoadableView<List<T>> {}

    abstract class Presenter<V extends View> extends BasePresenter<V> {
        public abstract void loadItems();
        public abstract void addNew(String shopName);
        public abstract void delete(ItemModel shopModel);
    }
}
