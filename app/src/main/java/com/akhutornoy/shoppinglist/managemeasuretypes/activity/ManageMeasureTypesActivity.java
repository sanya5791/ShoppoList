package com.akhutornoy.shoppinglist.managemeasuretypes.activity;

import android.content.Context;
import android.content.Intent;

import com.akhutornoy.shoppinglist.R;
import com.akhutornoy.shoppinglist.base.activity.BaseManageItemsActivity;
import com.akhutornoy.shoppinglist.managemeasuretypes.fragment.ManageMeasureTypesFragment;

public class ManageMeasureTypesActivity extends BaseManageItemsActivity {

    public static Intent createIntent(Context context) {
        return new Intent(context, ManageMeasureTypesActivity.class);
    }

    @Override
    protected void showManageItemsScreen() {
        setToolbarTitle(R.string.title_manage_product_types);
        showFragment(ManageMeasureTypesFragment.newInstance());
    }
}
