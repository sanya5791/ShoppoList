package com.akhutornoy.shoppinglist.shops.manageshops.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.akhutornoy.shoppinglist.base.fragment.BaseManageItemsFragment;
import com.akhutornoy.shoppinglist.base.presenter.BasePresenter;
import com.akhutornoy.shoppinglist.base.model.ItemModel;
import com.akhutornoy.shoppinglist.domain.AppDatabase;
import com.akhutornoy.shoppinglist.shops.manageshops.contract.ManageShopsContract;
import com.akhutornoy.shoppinglist.shops.manageshops.presenter.ManageShopsPresenter;

public class ManageShopsFragment extends BaseManageItemsFragment<ItemModel>
                                implements ManageShopsContract.View {

    private ManageShopsContract.Presenter mPresenter;

    public static Fragment newInstance() {
        return new ManageShopsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mPresenter = new ManageShopsPresenter(AppDatabase.getInstance(getActivity()));
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected BasePresenter getPresenter() {
        return mPresenter;
    }
}
