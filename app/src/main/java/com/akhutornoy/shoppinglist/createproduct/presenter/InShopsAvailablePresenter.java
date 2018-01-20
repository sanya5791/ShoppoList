package com.akhutornoy.shoppinglist.createproduct.presenter;

import com.akhutornoy.shoppinglist.createproduct.contract.InShopsAvailableContract;
import com.akhutornoy.shoppinglist.createproduct.mapper.ShopModelMapper;
import com.akhutornoy.shoppinglist.domain.ShopDao;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class InShopsAvailablePresenter extends InShopsAvailableContract.Presenter {
    private ShopDao mDbShop;
    private ShopModelMapper mShopModelMapper;

    public InShopsAvailablePresenter(ShopDao dbShop) {
        this.mDbShop = dbShop;
        this.mShopModelMapper = new ShopModelMapper();
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
