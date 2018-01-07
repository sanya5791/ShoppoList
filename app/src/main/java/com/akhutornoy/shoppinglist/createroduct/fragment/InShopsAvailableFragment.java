package com.akhutornoy.shoppinglist.createroduct.fragment;

import android.os.Bundle;
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

public class InShopsAvailableFragment extends Fragment {

    private InShopsAvailableAdapter mAdapter;
    public static Fragment newInstance() {
        return new InShopsAvailableFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_in_shops_available, container, false);
        initAddButton(view);
        initProductsList(view);
        return view;
    }

    private void initAddButton(View view) {
        view.findViewById(R.id.bt_add).setOnClickListener(v -> getActivity().finish());
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
