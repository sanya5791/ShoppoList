package com.akhutornoy.shoppinglist.tobuy;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.akhutornoy.shoppinglist.R;

import java.util.ArrayList;
import java.util.List;

public class ToBuyFragment extends Fragment {

    private static final String ARG_SHOP_ID = "ARG_SHOP_ID";

    private ToBuyProductsAdapter mProductsAdapter;

    public static ToBuyFragment newInstance(String shopId) {
        ToBuyFragment fragment = new ToBuyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SHOP_ID, shopId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_to_buy, container, false);

        if (getArguments() != null) {
            String shopId = getArguments().getString(ARG_SHOP_ID);
            Snackbar.make(view, "Chosen shop: " + shopId, Snackbar.LENGTH_SHORT).show();
        } else {
            throw new IllegalArgumentException("Argument should be passed");
        }

        initProductList(view);
        initAddProductFab(view);
        return view;
    }

    private void initProductList(View view) {
        RecyclerView rvShops = view.findViewById(R.id.rv_products);
        rvShops.setLayoutManager(new LinearLayoutManager(getActivity()));
        mProductsAdapter = new ToBuyProductsAdapter();
        rvShops.setAdapter(mProductsAdapter);
        mProductsAdapter.setProducts(getMockProducts());
    }

    private List<ToBuyProductModel> getMockProducts() {
        List<ToBuyProductModel> products = new ArrayList<>();
        products.add(new ToBuyProductModel.Builder()
                .setId("1")
                .setIsBought(false)
                .setName("Melon")
                .build());
        products.add(new ToBuyProductModel.Builder()
                .setId("2")
                .setIsBought(true)
                .setName("Milk")
                .build());
        products.add(new ToBuyProductModel.Builder()
                .setId("3")
                .setIsBought(true)
                .setName("Bread")
                .build());
        products.add(new ToBuyProductModel.Builder()
                .setId("4")
                .setIsBought(false)
                .setName("Cheese")
                .build());
        return products;
    }

    private void initAddProductFab(View rootView) {
        FloatingActionButton fab = rootView.findViewById(R.id.fab);
        fab.setOnClickListener(v -> Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());
    }
}
