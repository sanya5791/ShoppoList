package com.akhutornoy.shoppinglist.base.contract;

import com.akhutornoy.shoppinglist.base.model.ItemModel;
import com.akhutornoy.shoppinglist.base.presenter.BasePresenter;
import com.akhutornoy.shoppinglist.base.view.LoadableView;

import java.util.List;

public interface BaseManageItemsContract {
    interface View<T extends ItemModel> extends LoadableView<List<T>> {}

    abstract class Presenter<V extends View> extends BasePresenter<V> {
        public abstract void loadItems();
        public abstract void addNew(String shopName);
        public abstract void delete(ItemModel shopModel);
    }
}
