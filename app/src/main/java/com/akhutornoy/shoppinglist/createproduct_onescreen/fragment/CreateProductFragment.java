package com.akhutornoy.shoppinglist.createproduct_onescreen.fragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import com.akhutornoy.shoppinglist.createproduct_onescreen.model.CreateProductOutputModel;
import com.akhutornoy.shoppinglist.createproduct_onescreen.presenter.CreateProductPresenter;
import com.akhutornoy.shoppinglist.domain.AppDatabase;

import java.util.List;
import java.util.Objects;

public class CreateProductFragment extends BaseFragment implements CreateProductContract.View {

    private EditText mName;
    private View mButtonMinus;
    private View mButtonPlus;
    private EditText mEditTextQuantity;
    private View mProgressBar;

    private CreateProductContract.Presenter mPresenter;

    private QuantityTypeAdapter mQuantityTypeAdapter;
    private ShopsAdapter mShopsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        setHasOptionsMenu(true);
        initViews(view);
        initListeners();
        initAdapters(view);
        mPresenter = createPresenter();
        return view;
    }

    protected CreateProductContract.Presenter createPresenter() {
        return new CreateProductPresenter(AppDatabase.getInstance(getActivity()));
    }

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_create_product;
    }

    @Override
    protected BasePresenter getBasePresenter() {
        return mPresenter;
    }

    private void initViews(View view) {
        mName = view.findViewById(R.id.et_name);
        mButtonMinus = view.findViewById(R.id.iv_quantity_minus);
        mButtonPlus = view.findViewById(R.id.iv_quantity_plus);
        mEditTextQuantity = view.findViewById(R.id.et_quantity);
        mProgressBar = view.findViewById(R.id.pb);
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
        mQuantityTypeAdapter = new QuantityTypeAdapter();
        rv.setAdapter(mQuantityTypeAdapter);
    }

    private void initShopsAdapter(View view) {
        RecyclerView rv = view.findViewById(R.id.rv_in_shops_available);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));
        mShopsAdapter = new ShopsAdapter(getActivity());
        rv.setAdapter(mShopsAdapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_done, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_done:
                onDoneButtonClick();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void onDoneButtonClick() {
        validateSelectedData();
    }

    private void validateSelectedData() {
        boolean isValid = true;

        String productName = mName.getText().toString();
        if (productName.isEmpty()) {
            mName.setError(getString(R.string.error_cant_be_empty));
            isValid = false;
        }

        String quantity = mEditTextQuantity.getText().toString();
        if (quantity.isEmpty()) {
            Toast.makeText(getActivity(), getString(R.string.error_default_quantity_cannot_be_empty), Toast.LENGTH_SHORT).show();
            isValid = false;
        }

        String selectedQuantityType = mQuantityTypeAdapter.getSelectedQuantityType();
        if (selectedQuantityType.isEmpty()) {
            Toast.makeText(getActivity(), getString(R.string.error_measure_type_cant_be_empty), Toast.LENGTH_SHORT).show();
            isValid = false;
        }

        List<String> shops = mShopsAdapter.getSelectedShops();
        if (shops.isEmpty()) {
            Toast.makeText(getActivity(), getString(R.string.error_shop_should_be_selected), Toast.LENGTH_SHORT).show();
            isValid = false;
        }

        if (!isValid) {
            return;
        }

        CreateProductOutputModel outputModel =
                new CreateProductOutputModel.Builder().setName(productName).setQuantityTypeSelected(selectedQuantityType).setDefaultQuantity(quantity).setShopsSelected(shops).build();

        mPresenter.createProduct(outputModel);
    }

    protected void setProductName(String name) {
        mName.setText(name);
    }

    protected void setQuantity(String quantity) {
        mEditTextQuantity.setText(quantity);

    }

    protected void setShopsSelected(List<String> shops) {
        mShopsAdapter.setShopsSelected(shops);
    }

    protected void setQuantityTypeSelected(String type) {
        mQuantityTypeAdapter.setSelected(type);
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
    public void onProductCreated() {
        closeScreen();
    }

    protected void closeScreen() {
        Objects.requireNonNull(getActivity()).onBackPressed();
    }

    @Override
    public void showProductAlreadyExistsError(String productName) {
        String message = getResources().getString(R.string.product_exists_choose_another_name_message, productName);
        new AlertDialog.Builder(getActivity())
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }

    @Override
    public void onDataLoaded(CreateProductInputDataModel data) {
        mQuantityTypeAdapter.setQuantityTypes(data.getMeasureTypes());
        mShopsAdapter.setShops(data.getShopsAvailable());
    }

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
    }
}
