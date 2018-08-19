package com.akhutornoy.shoppinglist.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.akhutornoy.shoppinglist.base.presenter.BasePresenter;
import com.akhutornoy.shoppinglist.base.view.BaseView;

public abstract class BaseFragment extends Fragment implements BaseView {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(getFragmentLayoutId(), container, false);
    }

    @LayoutRes
    protected abstract int getFragmentLayoutId();

    @Override
    @SuppressWarnings("unchecked")
    public void onStart() {
        super.onStart();
        getBasePresenter().attachView(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        getBasePresenter().detachView();
    }

    protected abstract BasePresenter getBasePresenter();
}
