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
import com.akhutornoy.shoppinglist.createroduct.adapter.AddProductTypeAdapter;
import com.akhutornoy.shoppinglist.createroduct.model.ProductTypeModel;

import java.util.ArrayList;
import java.util.List;

public class CreateProductTypeFragment extends BaseStepNavigationFragment {

    private AddProductTypeAdapter mAdapter;
    public static Fragment newInstance() {
        return new CreateProductTypeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_product_type, container, false);
        initButtonNext(view);
        initProductsList(view);
        return view;
    }

    private void initButtonNext(View view) {
        view.findViewById(R.id.bt_next).setOnClickListener(v -> mOnStepsNavigation.onStepFinished());
    }

    private void initProductsList(View view) {
        RecyclerView rvProducts = view.findViewById(R.id.rv_products);
        rvProducts.setLayoutManager(new LinearLayoutManager(getActivity()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(),
                LinearLayout.VERTICAL);
        rvProducts.addItemDecoration(dividerItemDecoration);
        mAdapter = new AddProductTypeAdapter();
        rvProducts.setAdapter(mAdapter);
        mAdapter.setTypes(getMockProducts());
    }

    private List<ProductTypeModel> getMockProducts() {
        List<ProductTypeModel> products = new ArrayList<>();
        products.add(new ProductTypeModel("kg"));
        products.add(new ProductTypeModel("mg"));
        products.add(new ProductTypeModel("l"));
        products.add(new ProductTypeModel("pkg"));
        return products;
    }
}
