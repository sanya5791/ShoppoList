package com.akhutornoy.shoppinglist.shops.manageshops;

import com.akhutornoy.shoppinglist.R;
import com.akhutornoy.shoppinglist.base.activity.BaseManageItemsActivity;

public class ManageShopsActivity extends BaseManageItemsActivity {

    @Override
    protected void showManageItemsScreen() {
        setToolbarTitle(R.string.title_manage_shops);
    }
}
