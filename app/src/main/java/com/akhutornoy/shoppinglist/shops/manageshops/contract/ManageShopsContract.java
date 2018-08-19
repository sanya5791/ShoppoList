package com.akhutornoy.shoppinglist.shops.manageshops.contract;

import com.akhutornoy.shoppinglist.base.contract.BaseManageItemsContract;
import com.akhutornoy.shoppinglist.shops.model.ShopModel;

public interface ManageShopsContract {
    interface View extends BaseManageItemsContract.View<ShopModel> {}

    abstract class Presenter extends BaseManageItemsContract.Presenter<View> {}
}
