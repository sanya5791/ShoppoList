package com.akhutornoy.shoppinglist.manageproducttypes.contract;

import com.akhutornoy.shoppinglist.base.contract.BaseManageItemsContract;
import com.akhutornoy.shoppinglist.base.model.BaseShopModel;

public interface ManageProductTypesContract {
    interface View extends BaseManageItemsContract.View<BaseShopModel> {}

    abstract class Presenter extends BaseManageItemsContract.Presenter<View> {}
}
