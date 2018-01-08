package com.akhutornoy.shoppinglist.addproducts;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.design.widget.FloatingActionButton;

import com.akhutornoy.shoppinglist.R;
import com.akhutornoy.shoppinglist.addproducts.fragment.AddProductsFragment;
import com.akhutornoy.shoppinglist.base.activity.BaseToolbarActivity;
import com.akhutornoy.shoppinglist.createroduct.CreateProductActivityBase;

public class AddProductsActivityBase extends BaseToolbarActivity {

    public static Intent createIntent(Context context) {
        return new Intent(context, AddProductsActivityBase.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFab();
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

    private void initFab() {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> showNewProductScreen());
    }

    private void showNewProductScreen() {
        startActivity(CreateProductActivityBase.createIntent(this));
    }

    private void showAddProductsScreen() {
        setToolbarTitle(R.string.title_add_products);
        showFragment(AddProductsFragment.newInstance());
    }
}
