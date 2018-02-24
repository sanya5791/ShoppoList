package com.akhutornoy.shoppinglist.createproduct.fragment;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.akhutornoy.shoppinglist.R;
import com.akhutornoy.shoppinglist.base.presenter.BasePresenter;
import com.akhutornoy.shoppinglist.createproduct.contract.ProductDefaultQuantityContract;
import com.akhutornoy.shoppinglist.createproduct.presenter.ProductDefaultQuantityPresenter;
import com.akhutornoy.shoppinglist.domain.AppDatabase;
import com.akhutornoy.shoppinglist.domain.Product;

public class ProductDefaultQuantityFragment extends BaseStepNavigationFragment implements ProductDefaultQuantityContract.View {
    private static final String ARG_PRODUCT_NAME = "ARG_PRODUCT_NAME";

    private ProductDefaultQuantityContract.Presenter mPresenter;

    private TextView mTvMeasureType;
    private View mProgressView;
    private EditText mQuantityInput;

    public static Fragment newInstance(String productName) {
        Bundle bundle = new Bundle();
        bundle.putString(ARG_PRODUCT_NAME, productName);
        Fragment fragment = new ProductDefaultQuantityFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        mPresenter = new ProductDefaultQuantityPresenter(AppDatabase.getInstance(getActivity()));
        initViews(view);
        return view;
    }

    @Override
    @LayoutRes
    protected int getFragmentLayoutId() {
        return R.layout.fragment_create_product_default_quantity;
    }

    @Override
    protected BasePresenter getPresenter() {
        return mPresenter;
    }

    private void initViews(View view) {
        mTvMeasureType = view.findViewById(R.id.tv_type);
        mQuantityInput = view.findViewById(R.id.et_default_value);
        mProgressView = view.findViewById(R.id.pb);
        initButtonNext(view);
    }

    private void initButtonNext(View view) {
        view.findViewById(R.id.bt_next).setOnClickListener(v -> saveDefaultQuantity());
    }

    private void saveDefaultQuantity() {
        String quantity = mQuantityInput.getText().toString();
        if (quantity.isEmpty()) {
            showError(getString(R.string.please_enter_default_quantity));
            return;
        }

        mPresenter.saveDefaultQuantity(getProductName(), quantity);
    }

    private void showError(String errorMessage) {
        Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.onProductLoad(getProductName());
    }

    private String getProductName() {
        return getArgumentString(ARG_PRODUCT_NAME);
    }

    @Override
    public void onError(String errorMsg) {
        Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setProduct(Product product) {
        mTvMeasureType.setText(product.getMeasureType());
        mQuantityInput.setText(product.getDefaultQuantity());
    }

    @Override
    public void showNextScreen() {
        mOnStepsNavigation.onStepFinished();
    }

    @Override
    public void showProgress() {
        mProgressView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressView.setVisibility(View.GONE);
    }
}
