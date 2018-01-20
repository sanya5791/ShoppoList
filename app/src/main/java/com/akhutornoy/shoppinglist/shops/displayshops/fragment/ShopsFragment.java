package com.akhutornoy.shoppinglist.shops.displayshops.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.design.widget.Snackbar;
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
import com.akhutornoy.shoppinglist.shops.BaseShopModel;
import com.akhutornoy.shoppinglist.shops.displayshops.adapter.ShopsAdapter;
import com.akhutornoy.shoppinglist.shops.displayshops.contract.ShopsContract;
import com.akhutornoy.shoppinglist.shops.displayshops.presenter.ShopsPresenter;

import java.util.List;

public class ShopsFragment extends BaseFragment implements ShopsContract.View {
    private ShopsContract.Presenter mPresenter;
    private ShopsAdapter mShopsAdapter;
    private OnManageShopsClickListener mOnManageShopsCallback;

    public interface OnManageShopsClickListener {
        void onManageShopsClick();
    }

    public static ShopsFragment newInstance() {
        return new ShopsFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnManageShopsClickListener) {
            mOnManageShopsCallback = (OnManageShopsClickListener) context;
        } else {
            throw new IllegalArgumentException("Host Activity for fragment should implement " + OnManageShopsClickListener.class.getSimpleName());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        mPresenter = new ShopsPresenter(AppDatabase.getInstance(getActivity()));
        initManageShopsButton(view);
        initShopList(view);
        return view;
    }

    private void initManageShopsButton(View view) {
        view.findViewById(R.id.iv_manage_shops).setOnClickListener(v ->
                mOnManageShopsCallback.onManageShopsClick());
    }

    @Override
    @LayoutRes
    protected int getFragmentLayoutId() {
        return R.layout.fragment_shops;
    }

    @Override
    protected BasePresenter getPresenter() {
        return mPresenter;
    }

    private void initShopList(View view) {
        RecyclerView rvShops = view.findViewById(R.id.rv_shops);
        rvShops.setLayoutManager(new LinearLayoutManager(getActivity()));
        mShopsAdapter = new ShopsAdapter(shopModel ->
                showNotImplementedMessage());
        rvShops.setAdapter(mShopsAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.loadShops();
    }

    @Override
    public void onDataLoaded(List<BaseShopModel> shops) {
        mShopsAdapter.setShops(shops);
    }

    private void showNotImplementedMessage() {
        Snackbar.make(getView(), "Not implemented yet", Snackbar.LENGTH_SHORT).show();
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
