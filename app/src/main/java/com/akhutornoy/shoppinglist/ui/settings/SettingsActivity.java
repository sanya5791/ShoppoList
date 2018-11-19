package com.akhutornoy.shoppinglist.ui.settings;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;

import com.akhutornoy.shoppinglist.R;
import com.akhutornoy.shoppinglist.ui.base.activity.BaseToolbarActivity;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class SettingsActivity extends BaseToolbarActivity {
    NavController mNavController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarTitle(R.string.settings);
        mNavController = Navigation.findNavController(this, R.id.nav_settings);
    }

    @Override
    @LayoutRes
    protected int getContentViewId() {
        return R.layout.activity_settings;
    }

    @Override
    @IdRes
    protected int getToolbarResId() {
        return R.id.toolbar;
    }
}
