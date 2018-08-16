package com.akhutornoy.shoppinglist.addproducts;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.view.Menu;
import android.view.MenuItem;

import com.akhutornoy.shoppinglist.R;
import com.akhutornoy.shoppinglist.addproducts.fragment.AddProductsFragment;
import com.akhutornoy.shoppinglist.base.activity.BaseToolbarActivity;
import com.akhutornoy.shoppinglist.createproduct_onescreen.CreateProductActivity;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class AddProductsActivity extends BaseToolbarActivity implements AddProductsFragment.EditProductListener {
    NavController mNavController;

    public static Intent createIntent(Context context) {
        return new Intent(context, AddProductsActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarTitle(R.string.title_add_products);
        mNavController = Navigation.findNavController(this, R.id.nav_add_products);
    }

    @Override
    @LayoutRes
    protected int getContentViewId() {
        return R.layout.activity_add_products;
    }

    @Override
    @IdRes
    protected int getToolbarResId() {
        return R.id.toolbar;
    }

    @Override
    @IdRes
    // TODO: 16-Aug-18 remove base method when finish Navigation implementation
    protected int getFragmentContainerId() {
        return 0;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_add_edit, menu);
        menu.findItem(R.id.menu_edit).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_add) {
            showNewProductScreen();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showNewProductScreen() {
        // TODO: 16-Aug-18 you should pass arguments
        mNavController.navigate(R.id.action_addProductsFragment_to_createProductActivity);
//        startActivity(CreateProductActivity.createIntent(this, null));
    }

    @Override
    public void onEditProduct(String name) {
        startActivity(CreateProductActivity.createIntent(this, name));
    }
}
