package com.akhutornoy.shoppinglist.tobuy.presenter;

import com.akhutornoy.shoppinglist.domain.AppDatabase;
import com.akhutornoy.shoppinglist.domain.CurrentShopDao;
import com.akhutornoy.shoppinglist.domain.ToBuyDao;
import com.akhutornoy.shoppinglist.tobuy.contract.ToBuyProductsContract;
import com.akhutornoy.shoppinglist.tobuy.mapper.ToBuyProductMapper;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ToBuyProductsPresenter extends ToBuyProductsContract.Presenter {
    private CurrentShopDao mDbCurrentShop;
    private ToBuyDao mDbToBuy;
    private ToBuyProductMapper mToBuyProductMapper;

    public ToBuyProductsPresenter(AppDatabase appDatabase) {
        mDbCurrentShop = appDatabase.toCurrentShop();
        mDbToBuy = appDatabase.toBuy();
        mToBuyProductMapper = new ToBuyProductMapper();
    }

    @Override
    public void loadProducts() {
        getCompositeDisposable().add(
                mDbCurrentShop.getFlowable()
                .map(currentShop -> mDbToBuy.getAllByShop(currentShop))
                .flatMap(toBuysFlowable -> toBuysFlowable)
                .map(toBuys -> mToBuyProductMapper.map(toBuys))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(toBuyProductModels ->
                                getView().onDataLoaded(toBuyProductModels),
                        this::onError)
        );
    }

    @Override
    public void addNew(String string) {}
}
