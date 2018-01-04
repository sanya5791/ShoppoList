package com.akhutornoy.shoppinglist.addproducts;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.akhutornoy.shoppinglist.R;

public class AddProductsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_products);

        initToolbar();
        initFab();
        showAddProductsFragment();
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
        toolbar.setNavigationOnClickListener(view -> finish());
    }

    private void initFab() {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view1 ->
                Snackbar.make(view1, "Not implemented yet", Snackbar.LENGTH_LONG).show());
    }

    private void showAddProductsFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, AddProductsFragment.newInstance())
                .commit();
    }
}
