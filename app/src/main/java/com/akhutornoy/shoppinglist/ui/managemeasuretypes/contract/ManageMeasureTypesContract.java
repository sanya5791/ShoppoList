package com.akhutornoy.shoppinglist.ui.managemeasuretypes.contract;

import com.akhutornoy.shoppinglist.ui.base.contract.BaseManageItemsContract;
import com.akhutornoy.shoppinglist.ui.base.model.ItemModel;

public interface ManageMeasureTypesContract {
    interface View extends BaseManageItemsContract.View<ItemModel> {}

    abstract class Presenter extends BaseManageItemsContract.Presenter<View> {}
}
