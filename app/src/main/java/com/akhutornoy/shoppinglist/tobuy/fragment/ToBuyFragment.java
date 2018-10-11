package com.akhutornoy.shoppinglist.tobuy.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.akhutornoy.shoppinglist.R;
import com.akhutornoy.shoppinglist.base.fragment.BaseFragment;
import com.akhutornoy.shoppinglist.base.activity.ToolbarTitle;
import com.akhutornoy.shoppinglist.base.presenter.BasePresenter;
import com.akhutornoy.shoppinglist.domain.AppDatabase;
import com.akhutornoy.shoppinglist.tobuy.adapter.ToBuyProductsAdapter;
import com.akhutornoy.shoppinglist.tobuy.contract.ToBuyProductsContract;
import com.akhutornoy.shoppinglist.tobuy.model.ToBuyModel;
import com.akhutornoy.shoppinglist.tobuy.presenter.ToBuyProductsPresenter;

public class ToBuyFragment extends BaseFragment implements ToBuyProductsContract.View {

    private ToBuyProductsContract.Presenter mPresenter;
    private ToBuyProductsAdapter mProductsAdapter;
    private ToolbarTitle mToolbarTitle;
    private FabHandler mFabHandler;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ToolbarTitle) {
            mToolbarTitle = (ToolbarTitle) context;
        } else {
            throw new IllegalArgumentException(String.format("Host Activity: '%s' should implement '%s' interface",
                    context.getClass().getSimpleName(), ToolbarTitle.class.getSimpleName()));
        }

        if (context instanceof FabHandler) {
            mFabHandler = (FabHandler) context;
        } else {
            throw new IllegalArgumentException(String.format("Host Activity: '%s' should implement '%s' interface",
                    context.getClass().getSimpleName(), FabHandler.class.getSimpleName()));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        setToolbarTitle();
        mPresenter = new ToBuyProductsPresenter(AppDatabase.getInstance(getActivity()));
        initProductList(view);
        return view;
    }

    private void setToolbarTitle() {
        mToolbarTitle.setToolbarTitle(getString(R.string.title_to_by_list));
    }

    @Override
    @LayoutRes
    protected int getFragmentLayoutId() {
        return R.layout.fragment_to_buy;
    }

    @Override
    protected BasePresenter getBasePresenter() {
        return mPresenter;
    }

    private void initProductList(View view) {
        RecyclerView rvProducts = view.findViewById(R.id.rv_products);
        rvProducts.setLayoutManager(new LinearLayoutManager(getActivity()));
        mProductsAdapter = new ToBuyProductsAdapter(toBuyModel -> mPresenter.updateState(toBuyModel));
        rvProducts.setAdapter(mProductsAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.loadProducts();
    }

    @Override
    public void onDataLoaded(ToBuyModel toBuyModel) {
        setToolbarSubTitle(toBuyModel.getCurrentShop());
        mProductsAdapter.setProducts(toBuyModel.getToBuyModels());

        if (isCurrentShopNameAll(toBuyModel)) {
            mFabHandler.hideFab();
        } else {
            mFabHandler.showFab();
        }
    }

    private void setToolbarSubTitle(String subtitle) {
        if (!subtitle.isEmpty()) {
            mToolbarTitle.setToolbarSubTitle(subtitle);
        }
    }

    private boolean isCurrentShopNameAll(ToBuyModel toBuyModel) {
        return toBuyModel.getCurrentShop().equals(getString(R.string.all));
    }

    @Override
    public void showProgress() {}

    @Override
    public void hideProgress() {}

    @Override
    public void onError(String errorMsg) {
        Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_SHORT).show();
    }

    public interface FabHandler {
        void showFab();
        void hideFab();
    }
}
