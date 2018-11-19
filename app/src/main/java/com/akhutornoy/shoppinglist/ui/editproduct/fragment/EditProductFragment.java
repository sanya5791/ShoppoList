package com.akhutornoy.shoppinglist.ui.editproduct.fragment;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.akhutornoy.shoppinglist.Injections;
import com.akhutornoy.shoppinglist.R;
import com.akhutornoy.shoppinglist.ui.createproduct_onescreen.contract.CreateProductContract;
import com.akhutornoy.shoppinglist.ui.createproduct_onescreen.fragment.CreateProductFragment;
import com.akhutornoy.shoppinglist.ui.createproduct_onescreen.model.CreateProductInputDataModel;
import com.akhutornoy.shoppinglist.ui.createproduct_onescreen.model.CreateProductOutputModel;
import com.akhutornoy.shoppinglist.domain.AppDatabase;
import com.akhutornoy.shoppinglist.ui.editproduct.contract.EditProductContract;
import com.akhutornoy.shoppinglist.ui.editproduct.presenter.EditProductPresenter;

import java.security.InvalidKeyException;

public class EditProductFragment extends CreateProductFragment implements EditProductContract.View {
    private EditProductContract.Presenter mPresenter;

    @Override
    protected CreateProductContract.Presenter createPresenter() {
        mPresenter = Injections.provideEditProductPresenter(getActivity());
        return mPresenter;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_delete_done, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_done:
                onDoneButtonClick();
                return true;
            case R.id.menu_delete:
                onDeleteButtonClick();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void onDeleteButtonClick() {
        mPresenter.deleteProduct(getProductName());
    }

    @Override
    public void onDataLoaded(CreateProductInputDataModel data) {
        super.onDataLoaded(data);
        mPresenter.loadProduct(getProductName());
    }

    private String getProductName() {
        return EditProductFragmentArgs.fromBundle(getArguments()).getEditProductName();
    }

    @Override
    public void onProductLoaded(CreateProductOutputModel product) {
        setProductName(product.getName());
        setQuantity(product.getDefaultQuantity());
        setShopsSelected(product.getShopsSelected());
        setQuantityTypeSelected(product.getQuantityTypeSelected());
    }

    @Override
    public void onProductDeleted() {
        closeScreen();
    }
}
