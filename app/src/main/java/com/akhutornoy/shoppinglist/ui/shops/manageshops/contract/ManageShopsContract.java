package com.akhutornoy.shoppinglist.ui.shops.manageshops.contract;

import com.akhutornoy.shoppinglist.ui.base.contract.BaseManageItemsContract;
import com.akhutornoy.shoppinglist.ui.shops.model.ShopModel;

public interface ManageShopsContract {
    interface View extends BaseManageItemsContract.View<ShopModel> {}

    abstract class Presenter extends BaseManageItemsContract.Presenter<View> {}
}
