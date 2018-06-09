package com.akhutornoy.shoppinglist.createproduct_onescreen.presenter;

import android.content.Context;

import com.akhutornoy.shoppinglist.createproduct_onescreen.contract.CreateProductContract;
import com.akhutornoy.shoppinglist.createproduct_onescreen.mapper.CreateProductInputDataModelMapper;
import com.akhutornoy.shoppinglist.createproduct_onescreen.mapper.ProductInShopMapper;
import com.akhutornoy.shoppinglist.createproduct_onescreen.mapper.ProductMapper;
import com.akhutornoy.shoppinglist.createproduct_onescreen.model.CreateProductInputDataModel;
import com.akhutornoy.shoppinglist.createproduct_onescreen.model.CreateProductOutputModel;
import com.akhutornoy.shoppinglist.domain.AppDatabase;
import com.akhutornoy.shoppinglist.domain.MeasureTypeDao;
import com.akhutornoy.shoppinglist.domain.Product;
import com.akhutornoy.shoppinglist.domain.ProductDao;
import com.akhutornoy.shoppinglist.domain.ProductInShopDao;
import com.akhutornoy.shoppinglist.domain.ShopDao;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CreateProductPresenter extends CreateProductContract.Presenter {
    private final ProductDao productDao;
    private final ProductInShopDao productInShopDao;
    private final ProductMapper productMapper;
    private final ProductInShopMapper productInShopMapper;
    private final MeasureTypeDao measureTypeDao;
    private final ShopDao shopDao;
    private final CreateProductInputDataModelMapper inputDataModelMapper;

    public CreateProductPresenter(String all, AppDatabase db) {
        productDao = db.toProduct();
        productInShopDao = db.toProductInShop();
        measureTypeDao = db.toMeasureType();
        shopDao = db.toShop();
        inputDataModelMapper = new CreateProductInputDataModelMapper();
        productMapper = new ProductMapper();
        productInShopMapper = new ProductInShopMapper(all);
    }

    @Override
    public void loadInputData() {
        getCompositeDisposable().add(
                measureTypeDao.getAll()
                        .zipWith(shopDao.getAll(), inputDataModelMapper::map)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                createProductInputDataModel ->
                                        getView().onDataLoaded(createProductInputDataModel),
                                this::onError)
        );
    }

    @Override
    public void createProduct(CreateProductOutputModel productModel) {
        getCompositeDisposable().add(
                Single.fromCallable(() -> productMapper.map(productModel))
                        .doOnSuccess(productDao::insertNew)
                        .map(ignored -> productInShopMapper.map(productModel))
                        .toObservable()
                        .flatMap(Observable::fromIterable)
                        .flatMapCompletable(productInShop -> Completable.fromAction(() -> productInShopDao.insertNew(productInShop)))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> getView().onProductCreated(),
                                this::onError)
        );
    }
}
