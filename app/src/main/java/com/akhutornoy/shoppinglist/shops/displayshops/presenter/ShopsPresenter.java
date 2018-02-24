package com.akhutornoy.shoppinglist.shops.displayshops.presenter;

import com.akhutornoy.shoppinglist.base.model.ItemModel;
import com.akhutornoy.shoppinglist.createproduct.mapper.ShopModelMapper;
import com.akhutornoy.shoppinglist.createproduct.model.ShopModel;
import com.akhutornoy.shoppinglist.domain.AppDatabase;
import com.akhutornoy.shoppinglist.domain.CurrentShop;
import com.akhutornoy.shoppinglist.domain.CurrentShopDao;
import com.akhutornoy.shoppinglist.domain.ShopDao;
import com.akhutornoy.shoppinglist.shops.displayshops.contract.ShopsContract;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ShopsPresenter extends ShopsContract.Presenter {
    private ShopDao mDbShop;
    private CurrentShopDao mDbCurrentShop;
    private ShopModelMapper mShopModelMapper;

    public ShopsPresenter(AppDatabase appDatabase) {
        mDbShop = appDatabase.toShop();
        mDbCurrentShop = appDatabase.toCurrentShop();
        mShopModelMapper = new ShopModelMapper();
    }

    public void loadShops() {
        getView().showProgress();
        getCompositeDisposable().add(
                mDbShop.getAll()
                        .map(shops -> {
                            mShopModelMapper.setCurrentShop(mDbCurrentShop.get());
                            return mShopModelMapper.map(shops);
                        })
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
    public void onShopSelected(ShopModel selectedShop) {
        getView().showProgress();
        getCompositeDisposable().add(
            Completable.fromAction(() -> setCurrentShop(selectedShop))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    getView().hideProgress();
                    getView().setCurrentShop(selectedShop);
                }, error -> {
                    getView().hideProgress();
                    onError(error);
                })
        );
    }

    private void setCurrentShop(ItemModel selectedShop) {
        mDbCurrentShop.deleteAll();
        mDbCurrentShop.set(new CurrentShop(selectedShop.getName()));
    }
}
