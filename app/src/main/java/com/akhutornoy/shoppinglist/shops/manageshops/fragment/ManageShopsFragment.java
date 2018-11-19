package com.akhutornoy.shoppinglist.shops.manageshops.fragment;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.akhutornoy.shoppinglist.Injections;
import com.akhutornoy.shoppinglist.R;
import com.akhutornoy.shoppinglist.base.fragment.BaseManageItemsFragment;
import com.akhutornoy.shoppinglist.base.presenter.BasePresenter;
import com.akhutornoy.shoppinglist.domain.AppDatabase;
import com.akhutornoy.shoppinglist.shops.manageshops.contract.ManageShopsContract;
import com.akhutornoy.shoppinglist.shops.manageshops.presenter.ManageShopsPresenter;
import com.akhutornoy.shoppinglist.shops.model.ShopModel;

public class ManageShopsFragment extends BaseManageItemsFragment<ShopModel>
                                implements ManageShopsContract.View {

    private ManageShopsContract.Presenter mPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mPresenter = Injections.provideManageShopsPresenter(getActivity());
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected BasePresenter getBasePresenter() {
        return mPresenter;
    }

    @Override
    protected AlertDialog.Builder getAddItemDialogBuilder(View customView, Runnable onOkPressed) {
        AlertDialog.Builder dialogBuilder = super.getAddItemDialogBuilder(customView, onOkPressed);
        return dialogBuilder.setTitle(R.string.title_new_shop_name);
    }
}
