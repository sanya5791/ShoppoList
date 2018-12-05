package com.akhutornoy.shoppinglist.ui.managemeasuretypes.activity;

import com.akhutornoy.shoppinglist.R;
import com.akhutornoy.shoppinglist.ui.base.activity.BaseManageItemsActivity;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class ManageMeasureTypesActivity extends BaseManageItemsActivity {

    @Override
    protected void showManageItemsScreen() {
        setToolbarTitle(R.string.title_manage_product_types);
        NavController navController = Navigation.findNavController(this, R.id.nav_host);
        navController.navigate(R.id.manageMeasureTypesFragment);

    }
}
