package com.akhutornoy.shoppinglist.tobuy.presenter;

import com.akhutornoy.shoppinglist.domain.AppDatabase;
import com.akhutornoy.shoppinglist.domain.ToBuy;
import com.akhutornoy.shoppinglist.tobuy.contract.ToBuyProductsContract;
import com.akhutornoy.shoppinglist.tobuy.mapper.ToBuyProductMapper;
import com.akhutornoy.shoppinglist.tobuy.model.ToBuyProductModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ToBuyProductsPresenter extends ToBuyProductsContract.Presenter {
    private AppDatabase mAppDatabase;
    private ToBuyProductMapper mToBuyProductMapper;

    public ToBuyProductsPresenter(AppDatabase appDatabase) {
        mAppDatabase = appDatabase;
        mToBuyProductMapper = new ToBuyProductMapper();
    }

    @Override
    public void loadProducts() {
        mAppDatabase.toBuy().getAll()
                        .map(toBuys -> mToBuyProductMapper.map(toBuys))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(toBuyProductModels -> getView().onDataLoaded(toBuyProductModels),
                        this::onError);
    }

    @Override
    public void addNew(String string) {
        getCompositeDisposable().add(
                Completable.fromAction(() -> mAppDatabase.toBuy().insertNew(getMockToBuyWithName(string)))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(() -> getView().hideProgress(),
                                this::onError)
        );
    }

    private ToBuy getMockToBuyWithName(String name) {
        return new ToBuy(name, "kg", "2");
    }

    private List<ToBuyProductModel> getMockProducts() {
        List<ToBuyProductModel> products = new ArrayList<>();
        products.add(new ToBuyProductModel.Builder()
                .setId("1")
                .setIsBought(false)
                .setName("Melon")
                .setQuantity("1")
                .setUnit("kg")
                .build());
        products.add(new ToBuyProductModel.Builder()
                .setId("2")
                .setIsBought(true)
                .setName("Milk")
                .setQuantity("1")
                .setUnit("l")
                .build());
        products.add(new ToBuyProductModel.Builder()
                .setId("3")
                .setIsBought(true)
                .setName("Bread")
                .setQuantity("1")
                .setUnit("p")
                .build());
        products.add(new ToBuyProductModel.Builder()
                .setId("4")
                .setIsBought(false)
                .setName("Cheese")
                .setQuantity("300")
                .setUnit("gr")
                .build());
        return products;
    }
}
