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
import com.akhutornoy.shoppinglist.base.model.ItemModel;

import java.util.List;

import static com.akhutornoy.shoppinglist.base.adapter.BaseManageItemsAdapter.*;

public abstract class BaseManageItemsFragment<T extends ItemModel> extends BaseFragment
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
        initItemsList(view);
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

    private void showAddItemDialog() {
        // TODO: 15-Jan-18 replace with Fragment dialog

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.view_input, null);
        EditText editText = view.findViewById(R.id.edit_text);
        Runnable onOkPressed = () -> {
            String itemName = editText.getText().toString();
            if (!itemName.isEmpty()) {
                mPresenter.addNew(itemName);
            }
        };
        AlertDialog.Builder builder = getAddItemDialogBuilder(view, onOkPressed);
        builder.show();
    }

    protected AlertDialog.Builder getAddItemDialogBuilder(View customView, Runnable onOkPressed) {
        return new AlertDialog.Builder(getActivity())
                    .setTitle(R.string.title_new_item_name)
                    .setView(customView)
                    .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                        onOkPressed.run();
                        dialog.dismiss();
                    })
                    .setNegativeButton(android.R.string.cancel, (dialog, which) -> dialog.cancel());
    }

    private void initProgressBar(View view) {
        view.findViewById(R.id.pb);
    }

    private void initItemsList(View view) {
        RecyclerView rvItems = view.findViewById(R.id.recycler_view);
        rvItems.setLayoutManager(new LinearLayoutManager(getActivity()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(),
                LinearLayout.VERTICAL);
        rvItems.addItemDecoration(dividerItemDecoration);
        mAdapter = new BaseManageItemsAdapter<T>(new OnItemClickListener() {
            @Override
            public void onEditItemClicked(ItemModel itemModel) {
                Toast.makeText(getActivity(), "Edit Shop is Not implemented", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDeleteItemClicked(ItemModel itemModel) {
                mPresenter.delete(itemModel);
            }
        });
        rvItems.setAdapter(mAdapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_manage_items, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_delete:
                mAdapter.setMode(Mode.DELETE);
                return true;
            case R.id.menu_add_or_edit:
                mAdapter.setMode(Mode.EDIT);
                showAddItemDialog();
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
