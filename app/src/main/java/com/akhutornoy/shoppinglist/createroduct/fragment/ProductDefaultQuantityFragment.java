package com.akhutornoy.shoppinglist.createroduct.fragment;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.akhutornoy.shoppinglist.R;
import com.akhutornoy.shoppinglist.base.presenter.BasePresenter;
import com.akhutornoy.shoppinglist.createroduct.contract.ProductDefaultQuantityContract;
import com.akhutornoy.shoppinglist.createroduct.presenter.ProductDefaultQuantityPresenter;

public class ProductDefaultQuantityFragment extends BaseStepNavigationFragment implements ProductDefaultQuantityContract.View {
    private ProductDefaultQuantityContract.Presenter mPresenter;

    public static Fragment newInstance() {
        return new ProductDefaultQuantityFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        mPresenter = new ProductDefaultQuantityPresenter();
        initButtonNext(view);
        return view;
    }

    @Override
    @LayoutRes
    protected int getFragmentLayoutId() {
        return R.layout.fragment_create_product_default_quantity;
    }

    @Override
    protected BasePresenter getPresenter() {
        return mPresenter;
    }

    private void initButtonNext(View view) {
        view.findViewById(R.id.bt_next).setOnClickListener(v -> mOnStepsNavigation.onStepFinished());
    }

    @Override
    public void onError(String errorMsg) {
        Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_SHORT).show();
    }
}
