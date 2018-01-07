package com.akhutornoy.shoppinglist.addproducts;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.akhutornoy.shoppinglist.R;
import com.akhutornoy.shoppinglist.createroduct.CreateProductActivity;

public class AddProductsActivity extends AppCompatActivity {

    public static Intent createIntent(Context context) {
        return new Intent(context, AddProductsActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_products);

        initToolbar();
        initFab();
        showAddProductsScreen();
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initToolbarBackButton(toolbar);
    }

    private void initToolbarBackButton(Toolbar toolbar) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
        toolbar.setNavigationOnClickListener(view -> finish());
    }

    private void initFab() {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> showNewProductScreen());
    }

    private void showNewProductScreen() {
        startActivity(CreateProductActivity.createIntent(this));
    }

    private void showAddProductsScreen() {
        showFragment(AddProductsFragment.newInstance());
    }

    private void showFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}
