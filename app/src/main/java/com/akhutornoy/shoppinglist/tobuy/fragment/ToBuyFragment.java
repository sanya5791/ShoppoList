package com.akhutornoy.shoppinglist.tobuy.fragment;


import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.akhutornoy.shoppinglist.R;
import com.akhutornoy.shoppinglist.base.BaseFragment;
import com.akhutornoy.shoppinglist.tobuy.model.ToBuyProductModel;
import com.akhutornoy.shoppinglist.tobuy.adapter.ToBuyProductsAdapter;

import java.util.ArrayList;
import java.util.List;

public class ToBuyFragment extends BaseFragment {

    private static final String ARG_SHOP_ID = "ARG_SHOP_ID";

    private ToBuyProductsAdapter mProductsAdapter;

    public static Fragment newInstance(String shopId) {
        ToBuyFragment fragment = new ToBuyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SHOP_ID, shopId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        if (getArguments() != null) {
            String shopId = getArguments().getString(ARG_SHOP_ID);
            Snackbar.make(view, "Chosen shop: " + shopId, Snackbar.LENGTH_SHORT).show();
        } else {
            throw new IllegalArgumentException("Argument should be passed");
        }

        initProductList(view);
        return view;
    }

    @Override
    @LayoutRes
    protected int getFragmentLayoutId() {
        return R.layout.fragment_to_buy;
    }

    private void initProductList(View view) {
        RecyclerView rvProducts = view.findViewById(R.id.rv_products);
        rvProducts.setLayoutManager(new LinearLayoutManager(getActivity()));
        mProductsAdapter = new ToBuyProductsAdapter();
        rvProducts.setAdapter(mProductsAdapter);
        mProductsAdapter.setProducts(getMockProducts());
    }

    private List<ToBuyProductModel> getMockProducts() {
        List<ToBuyProductModel> products = new ArrayList<>();
        products.add(new ToBuyProductModel.Builder()
                .setId("1")
                .setIsBought(false)
                .setName("Melon")
                .setQuantity("1")
                .setUnit("kg")
                .build());
        products.add(new ToBuyProductModel.Builder()
                .setId("2")
                .setIsBought(true)
                .setName("Milk")
                .setQuantity("1")
                .setUnit("l")
                .build());
        products.add(new ToBuyProductModel.Builder()
                .setId("3")
                .setIsBought(true)
                .setName("Bread")
                .setQuantity("1")
                .setUnit("p")
                .build());
        products.add(new ToBuyProductModel.Builder()
                .setId("4")
                .setIsBought(false)
                .setName("Cheese")
                .setQuantity("300")
                .setUnit("gr")
                .build());
        return products;
    }
}
