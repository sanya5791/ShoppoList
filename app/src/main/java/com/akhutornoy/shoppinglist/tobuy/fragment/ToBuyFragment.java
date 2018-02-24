package com.akhutornoy.shoppinglist.tobuy.fragment;


import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.akhutornoy.shoppinglist.R;
import com.akhutornoy.shoppinglist.base.BaseFragment;
import com.akhutornoy.shoppinglist.base.presenter.BasePresenter;
import com.akhutornoy.shoppinglist.domain.AppDatabase;
import com.akhutornoy.shoppinglist.tobuy.adapter.ToBuyProductsAdapter;
import com.akhutornoy.shoppinglist.tobuy.contract.ToBuyProductsContract;
import com.akhutornoy.shoppinglist.tobuy.model.ToBuyProductModel;
import com.akhutornoy.shoppinglist.tobuy.presenter.ToBuyProductsPresenter;

import java.util.List;

public class ToBuyFragment extends BaseFragment implements ToBuyProductsContract.View {

    private ToBuyProductsContract.Presenter mPresenter;
    private ToBuyProductsAdapter mProductsAdapter;

    public static Fragment newInstance() {
        return new ToBuyFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        mPresenter = new ToBuyProductsPresenter(AppDatabase.getInstance(getActivity()));
        initProductList(view);
        return view;
    }

    @Override
    @LayoutRes
    protected int getFragmentLayoutId() {
        return R.layout.fragment_to_buy;
    }

    @Override
    protected BasePresenter getPresenter() {
        return mPresenter;
    }

    private void initProductList(View view) {
        RecyclerView rvProducts = view.findViewById(R.id.rv_products);
        rvProducts.setLayoutManager(new LinearLayoutManager(getActivity()));
        mProductsAdapter = new ToBuyProductsAdapter();
        rvProducts.setAdapter(mProductsAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.loadProducts();
    }

    @Override
    public void onDataLoaded(List<ToBuyProductModel> products) {
        mProductsAdapter.setProducts(products);
    }

    @Override
    public void showProgress() {}

    @Override
    public void hideProgress() {}

    @Override
    public void onError(String errorMsg) {
        Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_SHORT).show();
    }
}
