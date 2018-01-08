package com.akhutornoy.shoppinglist.addproducts.fragment;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.akhutornoy.shoppinglist.R;
import com.akhutornoy.shoppinglist.addproducts.model.AddProductModel;
import com.akhutornoy.shoppinglist.addproducts.adapter.AddProductsAdapter;
import com.akhutornoy.shoppinglist.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class AddProductsFragment extends BaseFragment {

    private AddProductsAdapter mAdapter;

    public static Fragment newInstance() {
        return new AddProductsFragment();
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
        return R.layout.fragment_add_products;
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
        mAdapter = new AddProductsAdapter();
        rvProducts.setAdapter(mAdapter);
        mAdapter.setProducts(getMockProducts());
    }

    private List<AddProductModel> getMockProducts() {
        List<AddProductModel> products = new ArrayList<>();
        products.add(new AddProductModel.Builder()
                .setId("1")
                .setIsAdded(false)
                .setName("Melon")
                .setQuantity("1")
                .setUnit("kg")
                .build());
        products.add(new AddProductModel.Builder()
                .setId("2")
                .setIsAdded(true)
                .setName("Milk")
                .setQuantity("1")
                .setUnit("l")
                .build());
        products.add(new AddProductModel.Builder()
                .setId("3")
                .setIsAdded(true)
                .setName("Bread")
                .setQuantity("1")
                .setUnit("p")
                .build());
        products.add(new AddProductModel.Builder()
                .setId("4")
                .setIsAdded(false)
                .setName("Cheese")
                .setQuantity("300")
                .setUnit("gr")
                .build());
        return products;
    }
}
