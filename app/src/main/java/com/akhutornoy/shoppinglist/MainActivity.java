package com.akhutornoy.shoppinglist;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.akhutornoy.shoppinglist.addproducts.AddProductsActivity;
import com.akhutornoy.shoppinglist.tobuy.ToBuyFragment;
import com.akhutornoy.shoppinglist.shops.ShopsFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private View mContentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContentView = findViewById(android.R.id.content);
        Toolbar toolbar = initToolbar();
        initNavigationDrawer(toolbar);
        initAddProductFab();
        showShopsFragment();
        showToBuyScreen();
    }

    private Toolbar initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        return toolbar;
    }

    private void initAddProductFab() {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> showAddProductsScreen());
    }

    private void showAddProductsScreen() {
        startActivity(AddProductsActivity.createIntent(this));
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
                showNotImplementedMessage());
        findViewById(R.id.tv_about).setOnClickListener(view ->
                showNotImplementedMessage());
    }

    private void showShopsFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_shops, ShopsFragment.newInstance())
                .commit();
    }

    private void showToBuyScreen() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, ToBuyFragment.newInstance(getCurrentShop()))
                .commit();
    }

    private String getCurrentShop() {
        return "1";
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

    private void showNotImplementedMessage() {
        Snackbar.make(mContentView, "Not implemented yet", Snackbar.LENGTH_SHORT).show();
    }
}
