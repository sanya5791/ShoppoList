package com.akhutornoy.shoppinglist.manageproducttypes.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.akhutornoy.shoppinglist.base.fragment.BaseManageItemsFragment;
import com.akhutornoy.shoppinglist.base.model.BaseShopModel;
import com.akhutornoy.shoppinglist.base.presenter.BasePresenter;
import com.akhutornoy.shoppinglist.domain.AppDatabase;
import com.akhutornoy.shoppinglist.manageproducttypes.contract.ManageProductTypesContract;
import com.akhutornoy.shoppinglist.manageproducttypes.presenter.ManageProductTypesPresenter;

public class ManageProductTypesFragment extends BaseManageItemsFragment<BaseShopModel>
                                implements ManageProductTypesContract.View {

    private ManageProductTypesContract.Presenter mPresenter;

    public static Fragment newInstance() {
        return new ManageProductTypesFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mPresenter = new ManageProductTypesPresenter(AppDatabase.getInstance(getActivity()));
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected BasePresenter getPresenter() {
        return mPresenter;
    }
}
