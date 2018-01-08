package com.akhutornoy.shoppinglist.createroduct.fragment;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.akhutornoy.shoppinglist.R;
import com.akhutornoy.shoppinglist.base.presenter.BasePresenter;
import com.akhutornoy.shoppinglist.createroduct.adapter.AddProductTypeAdapter;
import com.akhutornoy.shoppinglist.createroduct.contract.CreateProductTypeContract;
import com.akhutornoy.shoppinglist.createroduct.model.ProductTypeModel;
import com.akhutornoy.shoppinglist.createroduct.presenter.CreateProductTypePresenter;

import java.util.List;

public class CreateProductTypeFragment extends BaseStepNavigationFragment implements CreateProductTypeContract.View {
    private CreateProductTypeContract.Presenter mPresenter;
    private AddProductTypeAdapter mAdapter;

    public static Fragment newInstance() {
        return new CreateProductTypeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        mPresenter = new CreateProductTypePresenter();
        initButtonNext(view);
        initProductsList(view);
        return view;
    }

    @Override
    @LayoutRes
    protected int getFragmentLayoutId() {
        return R.layout.fragment_create_product_type;
    }

    @Override
    protected BasePresenter getPresenter() {
        return mPresenter;
    }

    private void initButtonNext(View view) {
        view.findViewById(R.id.bt_next).setOnClickListener(v -> mOnStepsNavigation.onStepFinished());
    }

    private void initProductsList(View view) {
        RecyclerView rvProducts = view.findViewById(R.id.rv_products);
        rvProducts.setLayoutManager(new LinearLayoutManager(getActivity()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(),
                LinearLayout.VERTICAL);
        rvProducts.addItemDecoration(dividerItemDecoration);
        mAdapter = new AddProductTypeAdapter();
        rvProducts.setAdapter(mAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.loadTypes();
    }

    @Override
    public void onDataLoaded(List<ProductTypeModel> types) {
        mAdapter.setTypes(types);
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
