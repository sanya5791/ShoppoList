package com.akhutornoy.shoppinglist.base.fragment;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.akhutornoy.shoppinglist.R;
import com.akhutornoy.shoppinglist.base.BaseFragment;
import com.akhutornoy.shoppinglist.base.adapter.BaseManageItemsAdapter;
import com.akhutornoy.shoppinglist.base.contract.BaseManageItemsContract;
import com.akhutornoy.shoppinglist.base.model.BaseShopModel;

import java.util.List;

import static com.akhutornoy.shoppinglist.base.adapter.BaseManageItemsAdapter.*;

public abstract class BaseManageItemsFragment<T extends BaseShopModel> extends BaseFragment
        implements BaseManageItemsContract.View<T> {

    private BaseManageItemsContract.Presenter mPresenter;
    private BaseManageItemsAdapter<T> mAdapter;
    private ProgressBar mProgressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        mPresenter = getManageItemsPresenter();
        setHasOptionsMenu(true);
        mProgressBar = view.findViewById(R.id.pb);
        initDoneButton(view);
        initProgressBar(view);
        initShopsList(view);
        return view;
    }

    private BaseManageItemsContract.Presenter getManageItemsPresenter() {
        if (!(getPresenter() instanceof BaseManageItemsContract.Presenter)) {
            throw new IllegalArgumentException("Presenter should be a type of '" + BaseManageItemsContract.Presenter.class.getSimpleName() + "'");
        }

        return (BaseManageItemsContract.Presenter) getPresenter();
    }

    @Override
    @LayoutRes
    protected int getFragmentLayoutId() {
        return R.layout.fragment_manage_items;
    }

    private void initDoneButton(View view) {
        view.findViewById(R.id.bt_done).setOnClickListener(v -> getActivity().finish());
    }

    private void showAddShopDialog() {
        // TODO: 15-Jan-18 replace with Fragment dialog

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.view_input, null);
        EditText editText = view.findViewById(R.id.edit_text);
        new AlertDialog.Builder(getActivity())
                .setTitle(R.string.title_new_shop_name)
                .setView(view)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                    String shopName = editText.getText().toString();
                    if (!shopName.isEmpty()) {
                        mPresenter.addNew(shopName);
                    }
                    dialog.dismiss();
                })
                .setNegativeButton(android.R.string.cancel, (dialog, which) -> dialog.cancel())
                .show();
    }

    private void initProgressBar(View view) {
        view.findViewById(R.id.pb);
    }

    private void initShopsList(View view) {
        RecyclerView rvProducts = view.findViewById(R.id.recycler_view);
        rvProducts.setLayoutManager(new LinearLayoutManager(getActivity()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(),
                LinearLayout.VERTICAL);
        rvProducts.addItemDecoration(dividerItemDecoration);
        mAdapter = new BaseManageItemsAdapter<T>(new OnItemClickListener() {
            @Override
            public void onEditItemClicked(BaseShopModel shopModel) {
                Toast.makeText(getActivity(), "Edit Shop is Not implemented", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDeleteItemClicked(BaseShopModel shopModel) {
                mPresenter.delete(shopModel);
            }
        });
        rvProducts.setAdapter(mAdapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_manage_shops, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_delete:
                mAdapter.setMode(Mode.DELETE);
                return true;
            case R.id.menu_add:
                mAdapter.setMode(Mode.EDIT);
                showAddShopDialog();
                return true;
            case R.id.menu_resort:
                mAdapter.setMode(Mode.RESORT);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.loadItems();
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
    public void onError(String errorMsg) {
        Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDataLoaded(List<T> items) {
        mAdapter.setProducts(items);
    }
}
