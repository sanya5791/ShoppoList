package com.akhutornoy.shoppinglist.tobuy.presenter;

import com.akhutornoy.shoppinglist.domain.AppDatabase;
import com.akhutornoy.shoppinglist.domain.CurrentShopDao;
import com.akhutornoy.shoppinglist.domain.ToBuyDao;
import com.akhutornoy.shoppinglist.tobuy.contract.ToBuyProductsContract;
import com.akhutornoy.shoppinglist.tobuy.mapper.ToBuyProductMapper;
import com.akhutornoy.shoppinglist.tobuy.model.ToBuyModel;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ToBuyProductsPresenter extends ToBuyProductsContract.Presenter {
    private ToBuyDao mDbToBuy;
    private CurrentShopDao mDbCurrentShop;
    private ToBuyProductMapper mToBuyProductMapper;

    public ToBuyProductsPresenter(AppDatabase appDatabase) {
        mDbToBuy = appDatabase.toBuy();
        mDbCurrentShop = appDatabase.toCurrentShop();
        mToBuyProductMapper = new ToBuyProductMapper();
    }

    @Override
    public void loadProducts() {
        getCompositeDisposable().add(
          mDbToBuy.getAllByCurrentShop()
                  .map(toBuys -> mToBuyProductMapper.map(toBuys))
                  .map(toBuyProductModels -> new ToBuyModel(toBuyProductModels, mDbCurrentShop.get()))
                  .subscribeOn(Schedulers.io())
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribe(toBuyModel ->
                                  getView().onDataLoaded(toBuyModel),
                          this::onError)
        );
    }

    @Override
    public void addNew(String string) {}
}
