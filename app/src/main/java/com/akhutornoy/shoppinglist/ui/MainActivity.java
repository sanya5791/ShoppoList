package com.akhutornoy.shoppinglist.ui;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.akhutornoy.shoppinglist.R;
import com.akhutornoy.shoppinglist.ui.base.activity.BaseToolbarActivity;
import com.akhutornoy.shoppinglist.ui.base.activity.ToolbarTitle;
import com.akhutornoy.shoppinglist.ui.shops.displayshops.fragment.ShopsFragment;
import com.akhutornoy.shoppinglist.ui.tobuy.fragment.ToBuyFragment;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class MainActivity extends BaseToolbarActivity
        implements NavigationView.OnNavigationItemSelectedListener, ShopsFragment.OnShopsClickListener,
                    ToolbarTitle, ToBuyFragment.FabHandler {

    private DrawerLayout mDrawer;
    private FloatingActionButton mFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initNavigationDrawer(getToolbar());
        initAddProductFab();
    }

    @Override
    @LayoutRes
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    @IdRes
    protected int getToolbarResId() {
        return R.id.toolbar;
    }

    private void initNavigationDrawer(Toolbar toolbar) {
        initNavigationDrawerView(toolbar);
        initOptions();
    }

    private void initNavigationDrawerView(Toolbar toolbar) {
        mDrawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void initOptions() {
        findViewById(R.id.tv_settings).setOnClickListener(v ->
                showSettingsScreen());
        findViewById(R.id.tv_about).setOnClickListener(view ->
                Toast.makeText(this, "Sanya's freeware", Toast.LENGTH_SHORT).show());
    }

    private void showSettingsScreen() {
        closeDrawer();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_to_buy);
        navController.navigate(R.id.action_toBuyFragment_to_settingsActivity);
    }

    private void initAddProductFab() {
        mFab = findViewById(R.id.fab);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_to_buy);
        Navigation.setViewNavController(mFab, navController);
        mFab.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_toBuyFragment_to_addProductsActivity));
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        closeDrawer();
        return true;
    }

    @Override
    public void onManageShopsClick() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_shops);
        navController.navigate(R.id.action_shopsFragment_to_manageShopsActivity);
    }

    @Override
    public void onShopClick() {
        closeDrawer();
    }

    private void closeDrawer() {
        mDrawer.closeDrawer(GravityCompat.START);
    }

    @Override
    public void setToolbarTitle(String title) {
        super.setToolbarTitle(title);
    }

    @Override
    public void setToolbarSubTitle(String subTitle) {
        super.setToolbarSubTitle(subTitle);
    }

    @Override
    public void showFab() {
        mFab.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideFab() {
        mFab.setVisibility(View.GONE);
    }
}
