package com.akhutornoy.shoppinglist.createproduct_onescreen.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.akhutornoy.shoppinglist.R;
import com.akhutornoy.shoppinglist.base.BaseFragment;
import com.akhutornoy.shoppinglist.base.presenter.BasePresenter;
import com.akhutornoy.shoppinglist.createproduct_onescreen.adapter.QuantityTypeAdapter;
import com.akhutornoy.shoppinglist.createproduct_onescreen.adapter.ShopsAdapter;
import com.akhutornoy.shoppinglist.createproduct_onescreen.contract.CreateProductContract;
import com.akhutornoy.shoppinglist.createproduct_onescreen.model.CreateProductInputDataModel;
import com.akhutornoy.shoppinglist.createproduct_onescreen.presenter.CreateProductPresenter;
import com.akhutornoy.shoppinglist.domain.AppDatabase;

public class CreateProductFragment extends BaseFragment implements CreateProductContract.View {

    private View mButtonMinus;
    private View mButtonPlus;
    private EditText mEditTextQuantity;

    private CreateProductPresenter mPresenter;

    private QuantityTypeAdapter quantityTypeAdapter;
    private ShopsAdapter shopsAdapter;

    public static Fragment newInstance() {
        return new CreateProductFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        initViews(view);
        initListeners();
        initAdapters(view);
        mPresenter = new CreateProductPresenter(AppDatabase.getInstance(getActivity()));
        return view;
    }

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_create_product;
    }

    @Override
    protected BasePresenter getPresenter() {
        return mPresenter;
    }

    private void initViews(View view) {
        mButtonMinus = view.findViewById(R.id.iv_quantity_minus);
        mButtonPlus = view.findViewById(R.id.iv_quantity_plus);
        mEditTextQuantity = view.findViewById(R.id.et_quantity);
    }

    private void initListeners() {
        mButtonMinus.setOnClickListener(this::onMinusClicked);
        mButtonPlus.setOnClickListener(this::onPlusClicked);
    }

    private void onMinusClicked(View view) {
        inDeCrement(false);
    }

    private void onPlusClicked(View view) {
        inDeCrement(true);
    }

    private void inDeCrement(boolean isIncrement) {
        String quantityString = mEditTextQuantity.getText().toString();
        float quantity = Float.parseFloat(quantityString);
        quantity = isIncrement ? ++quantity : --quantity;
        quantityString = String.valueOf(quantity);
        mEditTextQuantity.setText(quantityString);

    }

    private void initAdapters(View view) {
        initQuantityTypeAdapter(view);
        initShopsAdapter(view);
    }

    private void initQuantityTypeAdapter(View view) {
        RecyclerView rv = view.findViewById(R.id.rv_measure_types);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));
        quantityTypeAdapter = new QuantityTypeAdapter();
        rv.setAdapter(quantityTypeAdapter);
    }

    private void initShopsAdapter(View view) {
        RecyclerView rv = view.findViewById(R.id.rv_in_shops_available);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));
        shopsAdapter = new ShopsAdapter(getActivity());
        rv.setAdapter(shopsAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.loadInputData();
    }

    @Override
    public void onError(String errorMsg) {
        Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void productCreated(String name) {
        // TODO: 28-May-18 collect all data and send to mPresenter
    }

    @Override
    public void onDataLoaded(CreateProductInputDataModel data) {
        quantityTypeAdapter.setQuantityTypes(data.getMeasureTypes());
        shopsAdapter.setShops(data.getShopsAvailable());
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }
}
