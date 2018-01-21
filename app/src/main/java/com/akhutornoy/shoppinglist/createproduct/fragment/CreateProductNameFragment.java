package com.akhutornoy.shoppinglist.createproduct.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.akhutornoy.shoppinglist.R;
import com.akhutornoy.shoppinglist.base.presenter.BasePresenter;
import com.akhutornoy.shoppinglist.createproduct.OnProductNameCreated;
import com.akhutornoy.shoppinglist.createproduct.contract.CreateProductNameContract;
import com.akhutornoy.shoppinglist.createproduct.presenter.CreateProductNamePresenter;
import com.akhutornoy.shoppinglist.domain.AppDatabase;

public class CreateProductNameFragment extends BaseStepNavigationFragment implements CreateProductNameContract.View {

    private OnProductNameCreated mOnProductNameCreated;

    private CreateProductNameContract.Presenter mPresenter;

    private ProgressBar mProgressBar;

    public static Fragment newInstance() {
        return new CreateProductNameFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        mPresenter = new CreateProductNamePresenter(AppDatabase.getInstance(getContext()).toProduct());

        initViews(view);
        return view;
    }

    @Override
    @LayoutRes
    protected int getFragmentLayoutId() {
        return R.layout.fragment_create_product_name;
    }

    @Override
    protected BasePresenter getPresenter() {
        return mPresenter;
    }

    private void initViews(View view) {
        mProgressBar = view.findViewById(R.id.pb);
        EditText etName = view.findViewById(R.id.et_name);
        view.findViewById(R.id.bt_next).setOnClickListener(v ->
                mPresenter.createName(etName.getText().toString()));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnProductNameCreated) {
            mOnProductNameCreated = (OnProductNameCreated) context;
        } else {
            throw new IllegalArgumentException("Host Activity for fragment should implement " + OnProductNameCreated.class.getSimpleName());
        }
    }

    @Override
    public void onError(String errorMsg) {
        Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showNameAlreadyExistsDialog(String name) {
        String message = getResources().getString(R.string.product_exists_message, name);
        new AlertDialog.Builder(getActivity())
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, (dialog, which) ->
                        nameCreated(name))
                .setNegativeButton(android.R.string.cancel, null)
                .show();
    }

    @Override
    public void nameCreated(String name) {
        mOnProductNameCreated.onProductNameCreated(name);
        mOnStepsNavigation.onStepFinished();
    }
}
