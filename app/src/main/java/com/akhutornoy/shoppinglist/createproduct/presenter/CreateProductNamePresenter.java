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
                Completable.fromAction(() -> tryInsertNewProduct(newName))
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

    private void tryInsertNewProduct(String newName) {
        Product foundProduct = mProductDao.getByName(newName);
        if (foundProduct != null) {
            throw new ProductAlreadyExistsException();
        }
        mProductDao.insertNew(new Product(newName));
    }

    private class ProductAlreadyExistsException extends RuntimeException {}
}
