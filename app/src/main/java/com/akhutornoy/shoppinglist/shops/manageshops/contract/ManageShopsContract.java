package com.akhutornoy.shoppinglist.shops.manageshops.contract;

import com.akhutornoy.shoppinglist.base.contract.BaseManageItemsContract;
import com.akhutornoy.shoppinglist.base.model.BaseShopModel;

public interface ManageShopsContract {
    interface View extends BaseManageItemsContract.View<BaseShopModel> {}

    abstract class Presenter extends BaseManageItemsContract.Presenter<View> {}
}
