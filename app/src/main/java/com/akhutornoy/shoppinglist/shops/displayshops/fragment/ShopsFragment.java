package com.akhutornoy.shoppinglist.shops.displayshops.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.akhutornoy.shoppinglist.R;
import com.akhutornoy.shoppinglist.base.BaseFragment;
import com.akhutornoy.shoppinglist.base.presenter.BasePresenter;
import com.akhutornoy.shoppinglist.createproduct.model.ShopModel;
import com.akhutornoy.shoppinglist.domain.AppDatabase;
import com.akhutornoy.shoppinglist.shops.displayshops.adapter.ShopsAdapter;
import com.akhutornoy.shoppinglist.shops.displayshops.contract.ShopsContract;
import com.akhutornoy.shoppinglist.shops.displayshops.presenter.ShopsPresenter;

import java.util.List;

public class ShopsFragment extends BaseFragment implements ShopsContract.View {
    private ShopsContract.Presenter mPresenter;
    private ShopsAdapter mShopsAdapter;
    private OnShopsClickListener mOnShopsCallback;

    public interface OnShopsClickListener {
        void onManageShopsClick();
        void onShopClick();
    }

    public static ShopsFragment newInstance() {
        return new ShopsFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnShopsClickListener) {
            mOnShopsCallback = (OnShopsClickListener) context;
        } else {
            throw new IllegalArgumentException("Host Activity for fragment should implement " + OnShopsClickListener.class.getSimpleName());
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
                mOnShopsCallback.onManageShopsClick());
    }

    @Override
    @LayoutRes
    protected int getFragmentLayoutId() {
        return R.layout.fragment_shops;
    }

    @Override
    protected BasePresenter getBasePresenter() {
        return mPresenter;
    }

    private void initShopList(View view) {
        RecyclerView rvShops = view.findViewById(R.id.rv_shops);
        rvShops.setLayoutManager(new LinearLayoutManager(getActivity()));
        mShopsAdapter = new ShopsAdapter(shopModel ->
                mPresenter.onShopSelected(shopModel));
        rvShops.setAdapter(mShopsAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.loadShops();
    }

    @Override
    public void onDataLoaded(List<ShopModel> shops) {
        mShopsAdapter.setShops(shops);
    }

    @Override
    public void showProgress() {}

    @Override
    public void hideProgress() {}

    @Override
    public void setCurrentShop(ShopModel currentShop) {
        mOnShopsCallback.onShopClick();
    }

    @Override
    public void onError(String errorMsg) {
        Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_SHORT).show();
    }
}
