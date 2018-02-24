package com.akhutornoy.shoppinglist;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.akhutornoy.shoppinglist.addproducts.AddProductsActivity;
import com.akhutornoy.shoppinglist.base.activity.BaseToolbarActivity;
import com.akhutornoy.shoppinglist.shops.manageshops.ManageShopsActivity;
import com.akhutornoy.shoppinglist.shops.displayshops.fragment.ShopsFragment;
import com.akhutornoy.shoppinglist.tobuy.fragment.ToBuyFragment;

public class MainActivity extends BaseToolbarActivity
        implements NavigationView.OnNavigationItemSelectedListener, ShopsFragment.OnManageShopsClickListener  {

    private View mContentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContentView = findViewById(android.R.id.content);
        initNavigationDrawer(getToolbar());
        initAddProductFab();
        showShopsFragment();
        showToByScreen();
    }

    @Override
    @LayoutRes
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    @IdRes
    protected int getFragmentContainerId() {
        return R.id.fragment_container;
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
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
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

    }

    private void initAddProductFab() {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> showAddProductsScreen());
    }

    private void showAddProductsScreen() {
        startActivity(AddProductsActivity.createIntent(this));
    }

    private void showShopsFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_shops, ShopsFragment.newInstance())
                .commit();
    }

    private void showToByScreen() {
        setToolbarTitle(R.string.title_to_by_list);
        showFragment(ToBuyFragment.newInstance());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onManageShopsClick() {
        startActivity(ManageShopsActivity.createIntent(this));
    }
}
