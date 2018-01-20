package com.akhutornoy.shoppinglist.shops.manageshops;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;

import com.akhutornoy.shoppinglist.R;
import com.akhutornoy.shoppinglist.base.activity.BaseToolbarActivity;
import com.akhutornoy.shoppinglist.shops.manageshops.fragment.ManageShopsFragment;

public class ManageShopsActivity extends BaseToolbarActivity {

    public static Intent createIntent(Context context) {
        return new Intent(context, ManageShopsActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showManageShopsScreen();
    }

    @Override
    @LayoutRes
    protected int getContentViewId() {
        return R.layout.activity_manage_shops;
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

    private void showManageShopsScreen() {
        setToolbarTitle(R.string.title_manage_shops);
        showFragment(ManageShopsFragment.newInstance());
    }
}
