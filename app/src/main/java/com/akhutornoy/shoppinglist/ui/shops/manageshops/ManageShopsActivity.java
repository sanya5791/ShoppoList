package com.akhutornoy.shoppinglist.ui.shops.manageshops;

import com.akhutornoy.shoppinglist.R;
import com.akhutornoy.shoppinglist.ui.base.activity.BaseManageItemsActivity;

public class ManageShopsActivity extends BaseManageItemsActivity {

    @Override
    protected void showManageItemsScreen() {
        setToolbarTitle(R.string.title_manage_shops);
    }
}
