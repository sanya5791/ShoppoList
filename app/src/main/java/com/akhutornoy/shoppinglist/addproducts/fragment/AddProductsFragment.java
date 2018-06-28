package com.akhutornoy.shoppinglist.addproducts.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.akhutornoy.shoppinglist.R;
import com.akhutornoy.shoppinglist.addproducts.adapter.AddProductsAdapter;
import com.akhutornoy.shoppinglist.addproducts.contract.AddProductsContract;
import com.akhutornoy.shoppinglist.addproducts.model.AddProductModel;
import com.akhutornoy.shoppinglist.addproducts.presenter.AddProductsPresenter;
import com.akhutornoy.shoppinglist.base.BaseFragment;
import com.akhutornoy.shoppinglist.base.presenter.BasePresenter;
import com.akhutornoy.shoppinglist.domain.AppDatabase;
import com.akhutornoy.shoppinglist.util.ui.AlertDialogUtils;

import java.util.List;

public class AddProductsFragment extends BaseFragment implements AddProductsContract.View {

    private AddProductsContract.Presenter mPresenter;
    private AddProductsAdapter mAdapter;
    private EditProductListener mEditProductListenerCallback;

    public static Fragment newInstance() {
        return new AddProductsFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (!(context instanceof EditProductListener)) {
            throw new IllegalArgumentException(String.format("'%s' should implement '%s' interface",
                    context.getClass().getName(), EditProductListener.class.getName()));
        }

        mEditProductListenerCallback = (EditProductListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        mPresenter = new AddProductsPresenter(AppDatabase.getInstance(getActivity()));
        initAddButton(view);
        initProductsList(view);
        return view;
    }

    @Override
    @LayoutRes
    protected int getFragmentLayoutId() {
        return R.layout.fragment_add_products;
    }

    @Override
    protected BasePresenter getPresenter() {
        return mPresenter;
    }

    private void initAddButton(View view) {
        view.findViewById(R.id.bt_add).setOnClickListener(v -> onAddButtonClicked());
    }

    private void onAddButtonClicked() {
        List<AddProductModel> selectedProducts = mAdapter.getSelected();
        mPresenter.saveSelectedProducts(selectedProducts);
    }

    private void initProductsList(View view) {
        RecyclerView rvProducts = view.findViewById(R.id.rv_products);
        rvProducts.setLayoutManager(new LinearLayoutManager(getActivity()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(),
                LinearLayout.VERTICAL);
        rvProducts.addItemDecoration(dividerItemDecoration);
        mAdapter = new AddProductsAdapter(this::onEditProductQuantity, this::onProductEdit);
        rvProducts.setAdapter(mAdapter);
    }

    private void onEditProductQuantity(AddProductModel addProductModel) {
        AlertDialog.Builder builder = AlertDialogUtils.getNumberWithSuffixDialog(
                getActivity(),
                addProductModel.getUnit(),
                newValue -> onNewQuantityEntered(addProductModel, newValue));

        builder.setMessage(getString(R.string.quantity_for, addProductModel.getName()))
                .show();
    }

    private void onProductEdit(AddProductModel productModel) {
        mEditProductListenerCallback.onEditProduct(productModel.getName());
    }

    private void onNewQuantityEntered(AddProductModel addProductModel, String value) {
        if (!value.isEmpty()) {
            addProductModel.setQuantity(value);
            mAdapter.updateProductQuantity(addProductModel);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.loadProducts();
    }

    @Override
    public void onDataLoaded(List<AddProductModel> products) {
        mAdapter.setProducts(products);
    }

    @Override
    public void showProgress() {}

    @Override
    public void hideProgress() {}

    @Override
    public void onError(String errorMsg) {
        Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void finishScreen() {
        getActivity().finish();
    }

    public interface EditProductListener {
        void onEditProduct(String name);
    }
}
