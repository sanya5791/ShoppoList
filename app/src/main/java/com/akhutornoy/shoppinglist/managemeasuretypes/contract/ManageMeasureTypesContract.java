package com.akhutornoy.shoppinglist.managemeasuretypes.contract;

import com.akhutornoy.shoppinglist.base.contract.BaseManageItemsContract;
import com.akhutornoy.shoppinglist.base.model.ItemModel;

public interface ManageMeasureTypesContract {
    interface View extends BaseManageItemsContract.View<ItemModel> {}

    abstract class Presenter extends BaseManageItemsContract.Presenter<View> {}
}
