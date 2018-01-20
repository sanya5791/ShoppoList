package com.akhutornoy.shoppinglist.shops.manageshops;

import android.content.Context;
import android.content.Intent;

import com.akhutornoy.shoppinglist.R;
import com.akhutornoy.shoppinglist.base.activity.BaseManageItemsActivity;
import com.akhutornoy.shoppinglist.shops.manageshops.fragment.ManageShopsFragment;

public class ManageShopsActivity extends BaseManageItemsActivity {

    public static Intent createIntent(Context context) {
        return new Intent(context, ManageShopsActivity.class);
    }

    @Override
    protected void showManageItemsScreen() {
        setToolbarTitle(R.string.title_manage_shops);
        showFragment(ManageShopsFragment.newInstance());
    }
}
