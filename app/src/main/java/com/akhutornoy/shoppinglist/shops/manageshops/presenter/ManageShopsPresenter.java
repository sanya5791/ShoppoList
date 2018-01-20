package com.akhutornoy.shoppinglist.shops.manageshops.presenter;

import com.akhutornoy.shoppinglist.domain.AppDatabase;
import com.akhutornoy.shoppinglist.domain.Shop;
import com.akhutornoy.shoppinglist.base.model.BaseShopModel;
import com.akhutornoy.shoppinglist.shops.manageshops.contract.ManageShopsContract;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ManageShopsPresenter extends ManageShopsContract.Presenter {

    public ManageShopsPresenter(AppDatabase appDatabase) {
        super(appDatabase);
    }

    @Override
    public void addNew(String shopName) {
        getView().showProgress();
        getCompositeDisposable().add(
                Completable.fromAction(() -> mDbShop.insertNew(new Shop(shopName)))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(() -> {
                        getView().hideProgress();
                    }, error -> {
                        getView().hideProgress();
                        super.onError(error);
                    })

        );
    }

    @Override
    public void delete(BaseShopModel shopModel) {
        getView().showProgress();
        getCompositeDisposable().add(
                Observable.fromCallable(() -> mShopModelMapper.mapInverse(shopModel))
                        .flatMapCompletable(shop -> Completable.fromAction(
                                () -> mDbShop.delete(shop)))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(() -> {
                            getView().hideProgress();
                        }, error -> {
                            getView().hideProgress();
                            super.onError(error);
                        })
        );
    }
}
