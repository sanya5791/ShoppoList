package com.akhutornoy.shoppinglist.manageshops.presenter;

import com.akhutornoy.shoppinglist.domain.AppDatabase;
import com.akhutornoy.shoppinglist.domain.Shop;
import com.akhutornoy.shoppinglist.domain.ShopDao;
import com.akhutornoy.shoppinglist.manageshops.contract.ManageShopsContract;
import com.akhutornoy.shoppinglist.manageshops.mapper.ManageShopModelMapper;
import com.akhutornoy.shoppinglist.manageshops.model.ManageShopModel;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ManageShopsPresenter extends ManageShopsContract.Presenter {
    private ShopDao mDbShop;
    private ManageShopModelMapper mShopModelMapper;

    public ManageShopsPresenter(AppDatabase appDatabase) {
        mDbShop = appDatabase.toShop();
        mShopModelMapper = new ManageShopModelMapper();
    }

    @Override
    public void loadShops() {
        getView().showProgress();
        getCompositeDisposable().add(
                mDbShop.getAll()
                        .map(shops -> mShopModelMapper.map(shops))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                manageShopModels -> {
                                    getView().hideProgress();
                                    getView().onDataLoaded(manageShopModels);
                                }, error -> {
                                    getView().hideProgress();
                                    super.onError(error);
                                })
        );
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
    public void delete(ManageShopModel shopModel) {
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
