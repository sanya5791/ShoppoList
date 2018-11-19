package com.akhutornoy.shoppinglist.ui.shops.displayshops.presenter;

import com.akhutornoy.shoppinglist.ui.base.model.ItemModel;
import com.akhutornoy.shoppinglist.ui.shops.mapper.ShopModelMapper;
import com.akhutornoy.shoppinglist.ui.shops.model.ShopModel;
import com.akhutornoy.shoppinglist.domain.CurrentShop;
import com.akhutornoy.shoppinglist.domain.CurrentShopDao;
import com.akhutornoy.shoppinglist.domain.ShopDao;
import com.akhutornoy.shoppinglist.ui.shops.displayshops.contract.ShopsContract;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ShopsPresenter extends ShopsContract.Presenter {
    private final ShopDao mDbShop;
    private final CurrentShopDao mDbCurrentShop;
    private final ShopModelMapper mShopModelMapper;

    public ShopsPresenter(ShopDao dbShop, CurrentShopDao dbCurrentShop, ShopModelMapper shopModelMapper) {
        mDbShop = dbShop;
        mDbCurrentShop = dbCurrentShop;
        mShopModelMapper = shopModelMapper;
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
