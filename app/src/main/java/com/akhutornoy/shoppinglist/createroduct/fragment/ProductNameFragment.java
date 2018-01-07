package com.akhutornoy.shoppinglist.createroduct.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.akhutornoy.shoppinglist.R;

public class ProductNameFragment extends BaseStepNavigationFragment {

    public static Fragment newInstance() {
        return new ProductNameFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_product_name, container, false);
        initButtonNext(view);
        return view;
    }

    private void initButtonNext(View view) {
        view.findViewById(R.id.bt_next).setOnClickListener(v -> mOnStepsNavigation.onStepFinished());
    }
}
