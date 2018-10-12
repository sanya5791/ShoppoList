package com.akhutornoy.shoppinglist.tobuy.presenter;

import com.akhutornoy.shoppinglist.domain.AppDatabase;
import com.akhutornoy.shoppinglist.domain.CurrentShopDao;
import com.akhutornoy.shoppinglist.domain.ToBuyDao;
import com.akhutornoy.shoppinglist.tobuy.contract.ToBuyProductsContract;
import com.akhutornoy.shoppinglist.tobuy.mapper.ToBuyProductMapper;
import com.akhutornoy.shoppinglist.tobuy.model.ToBuyModel;
import com.akhutornoy.shoppinglist.tobuy.model.ToBuyProductModel;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
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
    public void updateState(ToBuyProductModel toBuyModel) {
        getCompositeDisposable().add(
                Observable.fromCallable(() -> mToBuyProductMapper.mapInverse(toBuyModel))
                        .flatMapCompletable(toBuy -> Completable.fromAction(() -> mDbToBuy.insertNew(toBuy)))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(() -> {}, this::onError)
        );
    }

    @Override
    public void onShareListClicked(List<ToBuyProductModel> products) {
        StringBuilder builder = new StringBuilder();
        for (ToBuyProductModel product : products) {
            String item = String.format("%s - %s%s; %s\n", product.getName(), product.getQuantity(), product.getUnit(), product.getShopName());
            builder.append(item);

        }

        getView().shareList(builder.toString());

    }
}
