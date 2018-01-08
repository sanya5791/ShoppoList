package com.akhutornoy.shoppinglist.createroduct.fragment;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.akhutornoy.shoppinglist.R;

public class ProductDefaultQuantityFragment extends BaseStepNavigationFragment {

    public static Fragment newInstance() {
        return new ProductDefaultQuantityFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        initButtonNext(view);
        return view;
    }

    @Override
    @LayoutRes
    protected int getFragmentLayoutId() {
        return R.layout.fragment_create_product_default_quantity;
    }

    private void initButtonNext(View view) {
        view.findViewById(R.id.bt_next).setOnClickListener(v -> mOnStepsNavigation.onStepFinished());
    }
}
