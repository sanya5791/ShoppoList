package com.akhutornoy.shoppinglist.createproduct_onescreen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Menu;

import com.akhutornoy.shoppinglist.R;
import com.akhutornoy.shoppinglist.base.activity.BaseToolbarActivity;
import com.akhutornoy.shoppinglist.createproduct_onescreen.fragment.CreateProductFragment;
import com.akhutornoy.shoppinglist.editproduct.fragment.EditProductFragment;

public class CreateProductActivity extends BaseToolbarActivity {

    private static final String ARG_EDIT_PRODUCT_NAME = "ARG_EDIT_PRODUCT_NAME";

    public static Intent createIntent(Context context, @Nullable String editProductName) {
        Intent intent = new Intent(context, CreateProductActivity.class);
        intent.putExtra(ARG_EDIT_PRODUCT_NAME, editProductName);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarTitle(R.string.title_create_product);
        initViews();
        showFragment(getFragment());
    }

    private Fragment getFragment() {
        String argProductName = getIntent().getStringExtra(ARG_EDIT_PRODUCT_NAME);
        return argProductName == null
                ? CreateProductFragment.newInstance()
                : EditProductFragment.newInstance(argProductName);
    }

    @Override
    @LayoutRes
    protected int getContentViewId() {
        return R.layout.activity_create_product_one_screen;
    }

    @Override
    @LayoutRes
    protected int getFragmentContainerId() {
        return R.id.fragment_container;
    }

    @Override
    @IdRes
    protected int getToolbarResId() {
        return R.id.toolbar;
    }

    private void initViews() {}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_edit, menu);
        menu.findItem(R.id.menu_add).setVisible(false);
        menu.findItem(R.id.menu_edit).setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }
}
