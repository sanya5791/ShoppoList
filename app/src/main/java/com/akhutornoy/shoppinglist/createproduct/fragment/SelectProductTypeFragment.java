package com.akhutornoy.shoppinglist.createproduct.fragment;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.akhutornoy.shoppinglist.R;
import com.akhutornoy.shoppinglist.base.presenter.BasePresenter;
import com.akhutornoy.shoppinglist.createproduct.adapter.SelectProductTypeAdapter;
import com.akhutornoy.shoppinglist.createproduct.contract.SelectProductTypeContract;
import com.akhutornoy.shoppinglist.createproduct.model.ProductTypeModel;
import com.akhutornoy.shoppinglist.createproduct.presenter.SelectProductTypePresenter;
import com.akhutornoy.shoppinglist.domain.AppDatabase;
import com.akhutornoy.shoppinglist.manageproducttypes.activity.ManageProductTypesActivity;

import java.util.List;

public class SelectProductTypeFragment extends BaseStepNavigationFragment implements SelectProductTypeContract.View {
    private static final String ARG_PRODUCT_NAME = "ARG_PRODUCT_NAME";

    private SelectProductTypeContract.Presenter mPresenter;
    private SelectProductTypeAdapter mAdapter;

    private MenuItem mMenuAdd;

    public static Fragment newInstance(String name) {
        Bundle bundle = new Bundle();
        bundle.putString(ARG_PRODUCT_NAME, name);
        SelectProductTypeFragment fragment = new SelectProductTypeFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        mPresenter = new SelectProductTypePresenter(AppDatabase.getInstance(getContext()));
        setHasOptionsMenu(true);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        initTextViewInfo(view);
        initButtonNext(view);
        initProductsList(view);
    }

    @Override
    @LayoutRes
    protected int getFragmentLayoutId() {
        return R.layout.fragment_select_product_type;
    }

    @Override
    protected BasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        mMenuAdd = menu.findItem(R.id.menu_add_or_edit);
        mMenuAdd.setVisible(true);
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_add_or_edit) {
            showCreateProductTypeScreen();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showCreateProductTypeScreen() {
        getActivity().startActivity(ManageProductTypesActivity.createIntent(getActivity()));
    }

    private void initTextViewInfo(View view) {
        TextView textView = view.findViewById(R.id.tv_info);
        String text = getResources().getString(R.string.label_set_quantity_type_of_new_product, getProductName());
        textView.setText(text);
    }

    private String getProductName() {
        String name = getArguments().getString(ARG_PRODUCT_NAME, "");
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Arguments NOT found for the Fragment");
        }
        return name;
    }

    private void initButtonNext(View view) {
        view.findViewById(R.id.bt_next).setOnClickListener(v ->
                mPresenter.setType(getProductName(), mAdapter.getSelectedType().getName()));
    }

    private void initProductsList(View view) {
        RecyclerView rvProducts = view.findViewById(R.id.rv_products);
        rvProducts.setLayoutManager(new LinearLayoutManager(getActivity()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(),
                LinearLayout.VERTICAL);
        rvProducts.addItemDecoration(dividerItemDecoration);
        mAdapter = new SelectProductTypeAdapter();
        rvProducts.setAdapter(mAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.loadTypes(getProductName());
    }

    @Override
    public void onStop() {
        super.onStop();
        mMenuAdd.setVisible(false);
    }

    @Override
    public void onDataLoaded(List<ProductTypeModel> types) {
        mAdapter.setTypes(types);
    }

    @Override
    public void onTypeSet() {
        mOnStepsNavigation.onStepFinished();
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
