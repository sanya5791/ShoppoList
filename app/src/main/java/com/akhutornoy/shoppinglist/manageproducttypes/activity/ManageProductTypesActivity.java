package com.akhutornoy.shoppinglist.manageproducttypes.activity;

import android.content.Context;
import android.content.Intent;

import com.akhutornoy.shoppinglist.R;
import com.akhutornoy.shoppinglist.base.activity.BaseManageItemsActivity;
import com.akhutornoy.shoppinglist.manageproducttypes.fragment.ManageProductTypesFragment;

public class ManageProductTypesActivity extends BaseManageItemsActivity {

    public static Intent createIntent(Context context) {
        return new Intent(context, ManageProductTypesActivity.class);
    }

    @Override
    protected void showManageItemsScreen() {
        setToolbarTitle(R.string.title_manage_product_types);
        showFragment(ManageProductTypesFragment.newInstance());
    }
}
