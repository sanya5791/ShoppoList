package com.akhutornoy.shoppinglist.ui.base.presenter;

import com.akhutornoy.shoppinglist.ui.base.view.BaseView;

import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

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
        Timber.e(throwable, "onError: ");
        mView.onError(throwable.getMessage());
    }
}
