package com.akhutornoy.shoppinglist.createproduct.fragment;

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
import com.akhutornoy.shoppinglist.createproduct.adapter.InShopsAvailableAdapter;
import com.akhutornoy.shoppinglist.createproduct.contract.InShopsAvailableContract;
import com.akhutornoy.shoppinglist.createproduct.model.ShopModel;
import com.akhutornoy.shoppinglist.createproduct.presenter.InShopsAvailablePresenter;
import com.akhutornoy.shoppinglist.domain.AppDatabase;

import java.util.List;

public class InShopsAvailableFragment extends BaseStepNavigationFragment implements InShopsAvailableContract.View {
    private InShopsAvailableContract.Presenter mPresenter;
    private InShopsAvailableAdapter mAdapter;

    public static Fragment newInstance(String productName) {
        Bundle bundle = new Bundle();
        bundle.putString(ARG_PRODUCT_NAME, productName);
        Fragment fragment = new InShopsAvailableFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        mPresenter = new InShopsAvailablePresenter(AppDatabase.getInstance(getActivity()));
        initAddButton(view);
        initProductsList(view);
        return view;
    }

    @Override
    @LayoutRes
    protected int getFragmentLayoutId() {
        return R.layout.fragment_in_shops_available;
    }

    @Override
    protected BasePresenter getBasePresenter() {
        return mPresenter;
    }

    private void initAddButton(View view) {
        view.findViewById(R.id.bt_add).setOnClickListener(v -> addToShops());
    }

    private void addToShops() {
        List<ShopModel> selectedShops = mAdapter.getSelectedShops();
        if (selectedShops.isEmpty()) {
            showNotification(getString(R.string.you_should_select_at_least_one_shop));
            return;
        }

        mPresenter.saveProductInShops(getProductName(), selectedShops);
    }

    private void showNotification(String errorMessage) {
        Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
    }

    private void initProductsList(View view) {
        RecyclerView rvProducts = view.findViewById(R.id.rv_products);
        rvProducts.setLayoutManager(new LinearLayoutManager(getActivity()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(),
                LinearLayout.VERTICAL);
        rvProducts.addItemDecoration(dividerItemDecoration);
        mAdapter = new InShopsAvailableAdapter();
        rvProducts.setAdapter(mAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.loadShops();
    }

    @Override
    public void onDataLoaded(List<ShopModel> shops) {
        mAdapter.setShops(shops);
    }

    @Override
    public void showProgress() {}

    @Override
    public void hideProgress() {}

    @Override
    public void onError(String errorMsg) {
        Toast.makeText(getContext(), errorMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showNextScreen() {
        mOnStepsNavigation.onStepFinished();
    }
}
