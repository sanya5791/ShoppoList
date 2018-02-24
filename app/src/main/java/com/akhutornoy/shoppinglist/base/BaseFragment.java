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
        getPresenter().attachView(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        getPresenter().detachView();
    }

    protected String getArgumentString(String argKey) {
        String arg = getArguments().getString(argKey);
        if (arg == null) {
            throw new IllegalArgumentException("Arguments NOT found for the Fragment");
        }
        return arg;
    }

    protected abstract BasePresenter getPresenter();
}
