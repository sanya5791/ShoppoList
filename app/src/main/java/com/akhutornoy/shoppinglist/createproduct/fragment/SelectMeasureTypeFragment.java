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
import com.akhutornoy.shoppinglist.createproduct.adapter.SelectMeasureTypeAdapter;
import com.akhutornoy.shoppinglist.createproduct.contract.SelectMeasureTypeContract;
import com.akhutornoy.shoppinglist.createproduct.model.MeasureTypeModel;
import com.akhutornoy.shoppinglist.createproduct.presenter.SelectMeasureTypePresenter;
import com.akhutornoy.shoppinglist.domain.AppDatabase;
import com.akhutornoy.shoppinglist.managemeasuretypes.activity.ManageMeasureTypesActivity;

import java.util.List;

public class SelectMeasureTypeFragment extends BaseStepNavigationFragment implements SelectMeasureTypeContract.View {

    private SelectMeasureTypeContract.Presenter mPresenter;
    private SelectMeasureTypeAdapter mAdapter;

    private MenuItem mMenuEdit;

    public static Fragment newInstance(String name) {
        Bundle bundle = new Bundle();
        bundle.putString(ARG_PRODUCT_NAME, name);
        SelectMeasureTypeFragment fragment = new SelectMeasureTypeFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        mPresenter = new SelectMeasureTypePresenter(AppDatabase.getInstance(getContext()));
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
        return R.layout.fragment_select_measure_type;
    }

    @Override
    protected BasePresenter getBasePresenter() {
        return mPresenter;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        mMenuEdit = menu.findItem(R.id.menu_edit);
        mMenuEdit.setVisible(true);
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_edit) {
            showCreateMeasureTypeScreen();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showCreateMeasureTypeScreen() {
        getActivity().startActivity(ManageMeasureTypesActivity.createIntent(getActivity()));
    }

    private void initTextViewInfo(View view) {
        TextView textView = view.findViewById(R.id.tv_info);
        String text = getResources().getString(R.string.label_set_quantity_type_of_new_product, getProductName());
        textView.setText(text);
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
        mAdapter = new SelectMeasureTypeAdapter();
        rvProducts.setAdapter(mAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mMenuEdit != null) {
            mMenuEdit.setVisible(true);
        }
        mPresenter.loadTypes(getProductName());
    }

    @Override
    public void onStop() {
        super.onStop();
        mMenuEdit.setVisible(false);
    }

    @Override
    public void onDataLoaded(List<MeasureTypeModel> types) {
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
