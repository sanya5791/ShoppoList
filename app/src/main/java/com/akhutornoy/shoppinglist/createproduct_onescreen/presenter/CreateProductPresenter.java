package com.akhutornoy.shoppinglist.createproduct_onescreen.presenter;

import android.database.sqlite.SQLiteConstraintException;

import com.akhutornoy.shoppinglist.createproduct_onescreen.contract.CreateProductContract;
import com.akhutornoy.shoppinglist.createproduct_onescreen.mapper.CreateProductInputDataModelMapper;
import com.akhutornoy.shoppinglist.createproduct_onescreen.mapper.ProductInShopMapper;
import com.akhutornoy.shoppinglist.createproduct_onescreen.mapper.ProductMapper;
import com.akhutornoy.shoppinglist.createproduct_onescreen.model.CreateProductOutputModel;
import com.akhutornoy.shoppinglist.domain.AppDatabase;
import com.akhutornoy.shoppinglist.domain.ConstantString;
import com.akhutornoy.shoppinglist.domain.ConstantStringDao;
import com.akhutornoy.shoppinglist.domain.MeasureTypeDao;
import com.akhutornoy.shoppinglist.domain.ProductDao;
import com.akhutornoy.shoppinglist.domain.ProductInShopDao;
import com.akhutornoy.shoppinglist.domain.ShopDao;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CreateProductPresenter extends CreateProductContract.Presenter {
    private final ProductDao productDao;
    private final ProductInShopDao productInShopDao;
    private final ProductMapper productMapper;
    private final MeasureTypeDao measureTypeDao;
    private final ShopDao shopDao;
    private final CreateProductInputDataModelMapper inputDataModelMapper;
    private ProductInShopMapper productInShopMapper;

    public CreateProductPresenter(AppDatabase db) {
        productDao = db.toProduct();
        productInShopDao = db.toProductInShop();
        measureTypeDao = db.toMeasureType();
        shopDao = db.toShop();
        inputDataModelMapper = new CreateProductInputDataModelMapper();
        productMapper = new ProductMapper();

        ConstantStringDao constantStringDao = db.toConstantString();
        getCompositeDisposable().add(
                Single.fromCallable(() -> constantStringDao.getByName(ConstantString.SHOP_NAME_ALL))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            shopNameAll -> productInShopMapper = new ProductInShopMapper(shopNameAll),
                            this::onError)
        );
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
                                error -> onCreateProductError(productModel.getName(), error))
        );
    }

    private void onCreateProductError(String productName, Throwable error) {
        if (error instanceof SQLiteConstraintException) {
            getView().showProductAlreadyExistsError(productName);
            return;
        }

        onError(error);
    }
}
