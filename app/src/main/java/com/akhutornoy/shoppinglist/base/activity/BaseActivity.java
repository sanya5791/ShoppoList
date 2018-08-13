package com.akhutornoy.shoppinglist.base.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
    }

    @Deprecated
    protected void showFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(getFragmentContainerId(), fragment)
                .commit();
    }

    @LayoutRes
    @Deprecated
    protected abstract int getContentViewId();

    @IdRes
    protected abstract int getFragmentContainerId();
}
