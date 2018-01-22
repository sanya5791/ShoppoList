package com.akhutornoy.shoppinglist.createproduct.presenter;

import com.akhutornoy.shoppinglist.createproduct.contract.CreateProductNameContract;
import com.akhutornoy.shoppinglist.domain.Product;
import com.akhutornoy.shoppinglist.domain.ProductDao;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CreateProductNamePresenter extends CreateProductNameContract.Presenter {
    private final ProductDao mProductDao;

    public CreateProductNamePresenter(ProductDao productDao) {
        mProductDao = productDao;
    }

    @Override
    public void createName(String newName) {
        getView().showProgress();
        getCompositeDisposable().add(
                Observable.fromCallable(() -> mProductDao.getByName(newName))
                        .flatMapCompletable(foundProduct -> {
                            if (foundProduct == null) {
                                return Completable.fromAction(() ->
                                        mProductDao.insertNew(new Product(newName)));
                            }
                            throw new ProductAlreadyExistsException();
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    getView().hideProgress();
                    getView().nameCreated(newName);
                }, error -> {
                    getView().hideProgress();
                    if (error instanceof ProductAlreadyExistsException) {
                        getView().showNameAlreadyExistsDialog(newName);
                        return;
                    }
                    super.onError(error);
                })
        );
    }

    private class ProductAlreadyExistsException extends RuntimeException {}
}
