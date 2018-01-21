package com.akhutornoy.shoppinglist.shops.manageshops.contract;

import com.akhutornoy.shoppinglist.base.contract.BaseManageItemsContract;
import com.akhutornoy.shoppinglist.base.model.ItemModel;

public interface ManageShopsContract {
    interface View extends BaseManageItemsContract.View<ItemModel> {}

    abstract class Presenter extends BaseManageItemsContract.Presenter<View> {}
}
