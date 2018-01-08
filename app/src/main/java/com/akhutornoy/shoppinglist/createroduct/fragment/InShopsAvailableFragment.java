package com.akhutornoy.shoppinglist.createroduct.fragment;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.akhutornoy.shoppinglist.R;
import com.akhutornoy.shoppinglist.createroduct.adapter.InShopsAvailableAdapter;
import com.akhutornoy.shoppinglist.createroduct.model.ShopModel;

import java.util.ArrayList;
import java.util.List;

public class InShopsAvailableFragment extends BaseStepNavigationFragment {

    private InShopsAvailableAdapter mAdapter;
    public static Fragment newInstance() {
        return new InShopsAvailableFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        initAddButton(view);
        initProductsList(view);
        return view;
    }

    @Override
    @LayoutRes
    protected int getFragmentLayoutId() {
        return R.layout.fragment_in_shops_available;
    }

    private void initAddButton(View view) {
        view.findViewById(R.id.bt_add).setOnClickListener(v -> mOnStepsNavigation.onStepFinished());
    }

    private void initProductsList(View view) {
        RecyclerView rvProducts = view.findViewById(R.id.rv_products);
        rvProducts.setLayoutManager(new LinearLayoutManager(getActivity()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(),
                LinearLayout.VERTICAL);
        rvProducts.addItemDecoration(dividerItemDecoration);
        mAdapter = new InShopsAvailableAdapter();
        rvProducts.setAdapter(mAdapter);
        mAdapter.setShops(getMockShops());
    }

    private List<ShopModel> getMockShops() {
        List<ShopModel> products = new ArrayList<>();
        products.add(new ShopModel("Market"));
        products.add(new ShopModel("ATB"));
        products.add(new ShopModel("Silpo"));
        products.add(new ShopModel("Supermarket"));
        return products;
    }
}
