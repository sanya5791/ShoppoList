package com.akhutornoy.shoppinglist.shops.displayshops.presenter;

import com.akhutornoy.shoppinglist.domain.AppDatabase;
import com.akhutornoy.shoppinglist.domain.ShopDao;
import com.akhutornoy.shoppinglist.shops.BaseShopModelMapper;
import com.akhutornoy.shoppinglist.shops.displayshops.contract.ShopsContract;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ShopsPresenter extends ShopsContract.Presenter {
    private ShopDao mDbShop;
    private BaseShopModelMapper mShopModelMapper;

    public ShopsPresenter(AppDatabase appDatabase) {
        mDbShop = appDatabase.toShop();
        mShopModelMapper = new BaseShopModelMapper();
    }

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
}
