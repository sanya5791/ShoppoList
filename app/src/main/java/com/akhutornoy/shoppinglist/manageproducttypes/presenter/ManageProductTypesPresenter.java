package com.akhutornoy.shoppinglist.manageproducttypes.presenter;

import com.akhutornoy.shoppinglist.base.model.BaseShopModel;
import com.akhutornoy.shoppinglist.domain.AppDatabase;
import com.akhutornoy.shoppinglist.domain.Shop;
import com.akhutornoy.shoppinglist.domain.ShopDao;
import com.akhutornoy.shoppinglist.manageproducttypes.contract.ManageProductTypesContract;
import com.akhutornoy.shoppinglist.shops.BaseShopModelMapper;
import com.akhutornoy.shoppinglist.shops.manageshops.contract.ManageShopsContract;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ManageProductTypesPresenter extends ManageProductTypesContract.Presenter {
    private ShopDao mDbShop;
    private BaseShopModelMapper mShopModelMapper;

    public ManageProductTypesPresenter(AppDatabase appDatabase) {
        mDbShop = appDatabase.toShop();
        mShopModelMapper = new BaseShopModelMapper();
    }

    @Override
    public void loadItems() {
        getView().showProgress();
//        getCompositeDisposable().add(
//                mDbShop.getAll()
//                        .map(shops -> mShopModelMapper.map(shops))
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(
//                                manageShopModels -> {
//                                    getView().hideProgress();
//                                    getView().onDataLoaded(manageShopModels);
//                                }, error -> {
//                                    getView().hideProgress();
//                                    super.onError(error);
//                                })
//        );
    }

    @Override
    public void addNew(String shopName) {
//        getView().showProgress();
//        getCompositeDisposable().add(
//                Completable.fromAction(() -> mDbShop.insertNew(new Shop(shopName)))
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(() -> {
//                        getView().hideProgress();
//                    }, error -> {
//                        getView().hideProgress();
//                        super.onError(error);
//                    })
//
//        );
    }

    @Override
    public void delete(BaseShopModel shopModel) {
//        getView().showProgress();
//        getCompositeDisposable().add(
//                Observable.fromCallable(() -> mShopModelMapper.mapInverse(shopModel))
//                        .flatMapCompletable(shop -> Completable.fromAction(
//                                () -> mDbShop.delete(shop)))
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(() -> {
//                            getView().hideProgress();
//                        }, error -> {
//                            getView().hideProgress();
//                            super.onError(error);
//                        })
//        );
    }
}
