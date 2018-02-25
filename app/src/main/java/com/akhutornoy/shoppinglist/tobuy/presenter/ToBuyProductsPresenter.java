package com.akhutornoy.shoppinglist.tobuy.presenter;

import com.akhutornoy.shoppinglist.domain.AppDatabase;
import com.akhutornoy.shoppinglist.tobuy.contract.ToBuyProductsContract;
import com.akhutornoy.shoppinglist.tobuy.mapper.ToBuyProductMapper;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ToBuyProductsPresenter extends ToBuyProductsContract.Presenter {
    private AppDatabase mAppDatabase;
    private ToBuyProductMapper mToBuyProductMapper;

    public ToBuyProductsPresenter(AppDatabase appDatabase) {
        mAppDatabase = appDatabase;
        mToBuyProductMapper = new ToBuyProductMapper();
    }

    @Override
    public void loadProducts() {
        getCompositeDisposable().add(
                mAppDatabase.toBuy().getAll()
                        .map(toBuys -> mToBuyProductMapper.map(toBuys))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(toBuyProductModels -> getView().onDataLoaded(toBuyProductModels),
                                this::onError)
        );
    }

    @Override
    public void addNew(String string) {}
}
