package com.akhutornoy.shoppinglist.ui.shops.manageshops.presenter;

import com.akhutornoy.shoppinglist.ui.base.model.ItemModel;
import com.akhutornoy.shoppinglist.domain.Shop;
import com.akhutornoy.shoppinglist.domain.ShopDao;
import com.akhutornoy.shoppinglist.ui.shops.manageshops.contract.ManageShopsContract;
import com.akhutornoy.shoppinglist.ui.shops.mapper.ItemModelMapper;
import com.akhutornoy.shoppinglist.ui.shops.mapper.ShopModelMapper;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ManageShopsPresenter extends ManageShopsContract.Presenter {
    private final ShopDao mDbShop;
    private final ShopModelMapper mShopModelMapper;
    private final ItemModelMapper mItemModelMapper;

    public ManageShopsPresenter(ShopDao dbShop, ShopModelMapper shopModelMapper, ItemModelMapper itemModelMapper) {
        mDbShop = dbShop;
        mShopModelMapper = shopModelMapper;
        mItemModelMapper = itemModelMapper;
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
