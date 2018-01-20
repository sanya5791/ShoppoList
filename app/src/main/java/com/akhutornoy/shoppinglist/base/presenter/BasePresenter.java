package com.akhutornoy.shoppinglist.base.presenter;

import android.util.Log;

import com.akhutornoy.shoppinglist.base.view.BaseView;

import io.reactivex.disposables.CompositeDisposable;

public class BasePresenter<V extends BaseView> {

    private final CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private V mView;

    public void attachView(V view) {
        this.mView = view;
    }

    public void detachView() {
        mView = null;
        mCompositeDisposable.clear();
    }

    protected V getView() {
        return mView;
    }

    protected CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }

    public void onError(Throwable throwable) {
        Log.e(this.getClass().getSimpleName(), "onError: ", throwable);
        mView.onError(throwable.getMessage());
    }
}
