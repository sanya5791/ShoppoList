package com.akhutornoy.shoppinglist.shops;

import android.os.Bundle;
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

public class ShopsFragment extends Fragment {

    public static ShopsFragment newInstance() {
        return new ShopsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shops, container, false);
        initShopList(view);
        return view;
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
