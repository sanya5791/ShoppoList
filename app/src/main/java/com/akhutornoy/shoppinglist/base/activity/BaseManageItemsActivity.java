package com.akhutornoy.shoppinglist.base.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;

import com.akhutornoy.shoppinglist.R;

public abstract class BaseManageItemsActivity extends BaseToolbarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showManageItemsScreen();
    }

    protected abstract void showManageItemsScreen();

    @Override
    @LayoutRes
    protected int getContentViewId() {
        return R.layout.activity_manage_items;
    }

    @Override
    @IdRes
    protected int getToolbarResId() {
        return R.id.toolbar;
    }

    @Override
    @IdRes
    protected int getFragmentContainerId() {
        return 0;
    }
}
