package com.akhutornoy.shoppinglist.createproduct_onescreen;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.view.Menu;

import com.akhutornoy.shoppinglist.R;
import com.akhutornoy.shoppinglist.base.activity.BaseToolbarActivity;
import com.akhutornoy.shoppinglist.createproduct_onescreen.fragment.CreateProductFragment;
import com.akhutornoy.shoppinglist.editproduct.fragment.EditProductFragment;

import timber.log.Timber;

public class CreateProductActivity extends BaseToolbarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarTitle(R.string.title_create_product);
        initViews();
        showFragment(getFragment());
    }

    private Fragment getFragment() {
        String editProductName = null;
        try {
            Bundle extras = getIntent().getExtras();
            editProductName = CreateProductActivityArgs.fromBundle(extras).getEditProductName();
        } catch (Exception e) {
            Timber.d("getFragment: need to create new Product");
        }
        return editProductName == null
                ? CreateProductFragment.newInstance()
                : EditProductFragment.newInstance(editProductName);
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
