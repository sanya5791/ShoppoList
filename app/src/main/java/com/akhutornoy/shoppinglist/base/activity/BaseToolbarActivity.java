package com.akhutornoy.shoppinglist.base.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.akhutornoy.shoppinglist.base.activity.BaseActivity;

public abstract class BaseToolbarActivity extends BaseActivity {
    private Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar();
    }

    protected void initToolbar() {
        mToolbar = findViewById(getToolbarResId());
        setSupportActionBar(mToolbar);
        if (withToolbarBackNavigation()) {
            initToolbarBackButton(mToolbar);
        }
    }

    protected boolean withToolbarBackNavigation() {
        return true;
    }

    @IdRes
    protected abstract int getToolbarResId();

    private void initToolbarBackButton(Toolbar toolbar) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
        toolbar.setNavigationOnClickListener(view -> finish());
    }

    protected Toolbar getToolbar() {
        return mToolbar;
    }

    protected void setToolbarTitle(@StringRes int title) {
        mToolbar.setTitle(title);
    }

    protected void setToolbarSubTitle(@StringRes int subTitle) {
        mToolbar.setTitle(subTitle);
    }
}
