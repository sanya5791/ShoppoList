package com.akhutornoy.shoppinglist.ui.addproducts.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.akhutornoy.shoppinglist.Injections;
import com.akhutornoy.shoppinglist.R;
import com.akhutornoy.shoppinglist.ui.addproducts.adapter.AddProductsAdapter;
import com.akhutornoy.shoppinglist.ui.addproducts.contract.AddProductsContract;
import com.akhutornoy.shoppinglist.ui.addproducts.model.AddProductModel;
import com.akhutornoy.shoppinglist.ui.base.fragment.BaseFragment;
import com.akhutornoy.shoppinglist.ui.base.presenter.BasePresenter;
import com.akhutornoy.shoppinglist.ui.util.KeyboardUtils;
import com.akhutornoy.shoppinglist.ui.util.ui.AlertDialogUtils;

import java.util.List;
import java.util.Objects;

public class AddProductsFragment extends BaseFragment implements AddProductsContract.View {

    private AddProductsContract.Presenter mPresenter;
    private AddProductsAdapter mAdapter;
    private EditProductListener mEditProductListenerCallback;

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
        mPresenter = Injections.provideAddProductsPresenter(getActivity());
        initDoneButton(view);
        initProductsList(view);
        return view;
    }

    @Override
    @LayoutRes
    protected int getFragmentLayoutId() {
        return R.layout.fragment_add_products;
    }

    @Override
    protected BasePresenter getBasePresenter() {
        return mPresenter;
    }

    private void initDoneButton(View view) {
        view.findViewById(R.id.fab_done).setOnClickListener(v -> onDoneButtonClicked());
    }

    private void onDoneButtonClicked() {
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
        KeyboardUtils.showKeyboard(Objects.requireNonNull(getView()));
    }

    private void onProductEdit(AddProductModel productModel) {
        mEditProductListenerCallback.onEditProduct(productModel.getName());
    }

    private void onNewQuantityEntered(AddProductModel addProductModel, String value) {
        if (!value.isEmpty()) {
            addProductModel.setQuantity(value);
            mAdapter.updateProductQuantity(addProductModel);
        }
        KeyboardUtils.hideKeyboard(Objects.requireNonNull(getView()));
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
