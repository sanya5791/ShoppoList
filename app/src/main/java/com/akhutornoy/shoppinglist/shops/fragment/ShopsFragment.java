package com.akhutornoy.shoppinglist.shops.fragment;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.akhutornoy.shoppinglist.R;
import com.akhutornoy.shoppinglist.base.BaseFragment;
import com.akhutornoy.shoppinglist.shops.adapter.ShopsAdapter;
import com.akhutornoy.shoppinglist.shops.model.ShopModel;

import java.util.ArrayList;
import java.util.List;

public class ShopsFragment extends BaseFragment {

    public static ShopsFragment newInstance() {
        return new ShopsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        initShopList(view);
        return view;
    }

    @Override
    @LayoutRes
    protected int getFragmentLayoutId() {
        return R.layout.fragment_shops;
    }

    private void initShopList(View view) {
        RecyclerView rvShops = view.findViewById(R.id.rv_shops);
        rvShops.setLayoutManager(new LinearLayoutManager(getActivity()));
        ShopsAdapter shopsAdapter = new ShopsAdapter(shopModel ->
                showNotImplementedMessage());
        rvShops.setAdapter(shopsAdapter);
        shopsAdapter.setShopTypes(getMockShopTypes());
    }

    private List<ShopModel> getMockShopTypes() {
        List<ShopModel> mocks = new ArrayList<>();
        mocks.add(new ShopModel("1",  "Supermarket"));
        mocks.add(new ShopModel("2", "Market"));
        return mocks;
    }

    private void showNotImplementedMessage() {
        Snackbar.make(getView(), "Not implemented yet", Snackbar.LENGTH_SHORT).show();
    }
}
