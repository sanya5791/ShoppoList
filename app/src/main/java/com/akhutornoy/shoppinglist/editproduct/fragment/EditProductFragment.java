package com.akhutornoy.shoppinglist.editproduct.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.akhutornoy.shoppinglist.createproduct_onescreen.contract.CreateProductContract;
import com.akhutornoy.shoppinglist.createproduct_onescreen.fragment.CreateProductFragment;
import com.akhutornoy.shoppinglist.createproduct_onescreen.model.CreateProductInputDataModel;
import com.akhutornoy.shoppinglist.createproduct_onescreen.model.CreateProductOutputModel;
import com.akhutornoy.shoppinglist.domain.AppDatabase;
import com.akhutornoy.shoppinglist.editproduct.contract.EditProductContract;
import com.akhutornoy.shoppinglist.editproduct.presenter.EditProductPresenter;

public class EditProductFragment extends CreateProductFragment implements EditProductContract.View {
    private static final String ARG_EDIT_PRODUCT_NAME = "ARG_EDIT_PRODUCT_NAME";

    private EditProductContract.Presenter mPresenter;

    public static Fragment newInstance(@NonNull String argProductName) {
        Fragment fragment = new EditProductFragment();
        Bundle args = new Bundle();
        args.putString(ARG_EDIT_PRODUCT_NAME, argProductName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected CreateProductContract.Presenter createPresenter() {
        mPresenter = new EditProductPresenter(AppDatabase.getInstance(getActivity()));
        return mPresenter;
    }

    @Override
    public void onDataLoaded(CreateProductInputDataModel data) {
        super.onDataLoaded(data);
        mPresenter.loadProduct(getArgumentString(ARG_EDIT_PRODUCT_NAME));
    }

    @Override
    public void onProductLoaded(CreateProductOutputModel product) {
        setProductName(product.getName());
        setQuantity(product.getDefaultQuantity());
        setShopsSelected(product.getShopsSelected());
        setQuantityTypeSelected(product.getQuantityTypeSelected());
    }
}
