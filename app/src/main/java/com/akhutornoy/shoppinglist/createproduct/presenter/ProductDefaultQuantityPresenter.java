package com.akhutornoy.shoppinglist.createproduct.presenter;

import com.akhutornoy.shoppinglist.createproduct.contract.ProductDefaultQuantityContract;
import com.akhutornoy.shoppinglist.domain.AppDatabase;
import com.akhutornoy.shoppinglist.domain.Product;
import com.akhutornoy.shoppinglist.domain.ProductDao;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ProductDefaultQuantityPresenter extends ProductDefaultQuantityContract.Presenter {
    private final ProductDao mProductDao;

    public ProductDefaultQuantityPresenter(AppDatabase appDatabase) {
        this.mProductDao = appDatabase.toProduct();
    }

    @Override
    public void onDataLoad(String productName) {
        getView().showProgress();

        getCompositeDisposable().add(
            Single.fromCallable(() -> mProductDao.getByName(productName))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(product -> {
                    getView().hideProgress();
                    getView().onDataLoaded(product);
                }, error -> {
                    getView().hideProgress();
                    onError(error);
                })
        );
    }

    @Override
    public void saveDefaultQuantity(String productName, String quantity) {

        getView().showProgress();
        getCompositeDisposable().add(
                Completable.fromAction(() -> {
                            Product product = mProductDao.getByName(productName);
                            product.setDefaultQuantity(quantity);
                            mProductDao.update(product);
                        }).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(() -> {
                            getView().hideProgress();
                            getView().showNextScreen();
                        }, error -> {
                            getView().hideProgress();
                            onError(error);
                        })
        );
    }
}
