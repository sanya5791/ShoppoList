package com.akhutornoy.shoppinglist.ui.managemeasuretypes.fragment;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.akhutornoy.shoppinglist.R;
import com.akhutornoy.shoppinglist.ui.base.fragment.BaseManageItemsFragment;
import com.akhutornoy.shoppinglist.ui.base.model.ItemModel;
import com.akhutornoy.shoppinglist.ui.base.presenter.BasePresenter;
import com.akhutornoy.shoppinglist.domain.AppDatabase;
import com.akhutornoy.shoppinglist.ui.managemeasuretypes.contract.ManageMeasureTypesContract;
import com.akhutornoy.shoppinglist.ui.managemeasuretypes.presenter.ManageMeasureTypesPresenter;

public class ManageMeasureTypesFragment extends BaseManageItemsFragment<ItemModel>
                                implements ManageMeasureTypesContract.View {

    private static final int NEW_TYPE_DIALOG_MAX_INPUT_LENGTH = 3;

    private ManageMeasureTypesContract.Presenter mPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mPresenter = new ManageMeasureTypesPresenter(AppDatabase.getInstance(getActivity()));
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected BasePresenter getBasePresenter() {
        return mPresenter;
    }

    @Override
    protected AlertDialog.Builder getAddItemDialogBuilder(View customView, Runnable onOkPressed) {
        setDialogInputMaxLength(customView);
        return super.getAddItemDialogBuilder(customView, onOkPressed)
                .setTitle(R.string.title_new_product_type);
    }

    private void setDialogInputMaxLength(View customView) {
        EditText editText = customView.findViewById(R.id.edit_text);
        InputFilter[] filters = {new InputFilter.LengthFilter(NEW_TYPE_DIALOG_MAX_INPUT_LENGTH)};
        editText.setFilters(filters);
    }
}
