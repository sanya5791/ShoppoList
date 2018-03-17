package com.akhutornoy.shoppinglist.addproducts;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.design.widget.FloatingActionButton;
import android.view.Menu;
import android.view.MenuItem;

import com.akhutornoy.shoppinglist.R;
import com.akhutornoy.shoppinglist.addproducts.fragment.AddProductsFragment;
import com.akhutornoy.shoppinglist.base.activity.BaseToolbarActivity;
import com.akhutornoy.shoppinglist.createproduct.CreateProductActivity;

public class AddProductsActivity extends BaseToolbarActivity {

    public static Intent createIntent(Context context) {
        return new Intent(context, AddProductsActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showAddProductsScreen();
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
    protected int getFragmentContainerId() {
        return R.id.fragment_container;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_add_or_edit) {
            showNewProductScreen();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showNewProductScreen() {
        startActivity(CreateProductActivity.createIntent(this));
    }

    private void showAddProductsScreen() {
        setToolbarTitle(R.string.title_add_products);
        showFragment(AddProductsFragment.newInstance());
    }
}
