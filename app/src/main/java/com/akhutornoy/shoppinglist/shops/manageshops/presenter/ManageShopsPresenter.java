package com.akhutornoy.shoppinglist.shops.manageshops.presenter;

import com.akhutornoy.shoppinglist.base.model.ItemModel;
import com.akhutornoy.shoppinglist.createproduct.mapper.ShopModelMapper;
import com.akhutornoy.shoppinglist.domain.AppDatabase;
import com.akhutornoy.shoppinglist.domain.Shop;
import com.akhutornoy.shoppinglist.domain.ShopDao;
import com.akhutornoy.shoppinglist.shops.manageshops.contract.ManageShopsContract;
import com.akhutornoy.shoppinglist.shops.mapper.ItemModelMapper;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ManageShopsPresenter extends ManageShopsContract.Presenter {
    private ShopDao mDbShop;
    private ShopModelMapper mShopModelMapper;
    private ItemModelMapper mItemModelMapper;

    public ManageShopsPresenter(AppDatabase appDatabase) {
        mDbShop = appDatabase.toShop();
        mShopModelMapper = new ShopModelMapper();
        mItemModelMapper = new ItemModelMapper();
    }

    @Override
    public void loadItems() {
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
    public void delete(ItemModel shopModel) {
        getView().showProgress();
        getCompositeDisposable().add(
                Observable.fromCallable(() -> mItemModelMapper.mapInverse(shopModel))
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
