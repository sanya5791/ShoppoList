package com.akhutornoy.shoppinglist.ui.editproduct.presenter;

import android.database.sqlite.SQLiteConstraintException;
import android.util.Pair;

import com.akhutornoy.shoppinglist.domain.CurrentShopDao;
import com.akhutornoy.shoppinglist.ui.createproduct_onescreen.mapper.CreateProductInputDataModelMapper;
import com.akhutornoy.shoppinglist.ui.createproduct_onescreen.mapper.ProductInShopMapper;
import com.akhutornoy.shoppinglist.ui.createproduct_onescreen.mapper.ProductMapper;
import com.akhutornoy.shoppinglist.ui.createproduct_onescreen.model.CreateProductOutputModel;
import com.akhutornoy.shoppinglist.domain.ConstantString;
import com.akhutornoy.shoppinglist.domain.ConstantStringDao;
import com.akhutornoy.shoppinglist.domain.MeasureTypeDao;
import com.akhutornoy.shoppinglist.domain.Product;
import com.akhutornoy.shoppinglist.domain.ProductDao;
import com.akhutornoy.shoppinglist.domain.ProductInShop;
import com.akhutornoy.shoppinglist.domain.ProductInShopDao;
import com.akhutornoy.shoppinglist.domain.ShopDao;
import com.akhutornoy.shoppinglist.ui.editproduct.contract.EditProductContract;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class EditProductPresenter extends EditProductContract.Presenter {
    private final ProductDao productDao;
    private final ProductInShopDao productInShopDao;
    private final ProductMapper productMapper;
    private final MeasureTypeDao measureTypeDao;
    private final ShopDao shopDao;
    private final CreateProductInputDataModelMapper inputDataModelMapper;
    private final ProductInShopMapper productInShopMapper;
    private final ConstantStringDao constantStringDao;
    private final CurrentShopDao currentShopDao;

    public EditProductPresenter(
            ProductDao productDao,
            ProductInShopDao productInShopDao,
            ProductInShopMapper productInShopMapper,
            ProductMapper productMapper,
            MeasureTypeDao measureTypeDao,
            ShopDao shopDao,
            CreateProductInputDataModelMapper inputDataModelMapper,
            ConstantStringDao constantStringDao,
            CurrentShopDao currentShopDao) {
        this.productDao = productDao;
        this.productInShopDao = productInShopDao;
        this.productInShopMapper = productInShopMapper;
        this.productMapper = productMapper;
        this.measureTypeDao = measureTypeDao;
        this.shopDao = shopDao;
        this.inputDataModelMapper = inputDataModelMapper;
        this.constantStringDao = constantStringDao;
        this.currentShopDao = currentShopDao;
    }

    @Override
    public void loadInputData() {
        getCompositeDisposable().add(
                initMappers()
                        .andThen(measureTypeDao.getAll())
                        .zipWith(shopDao.getAll(), Pair::new)
                        .zipWith(currentShopDao.getFlowable(), (measureTypeAndShops, currentShop)
                                -> inputDataModelMapper.map(measureTypeAndShops.first, measureTypeAndShops.second, currentShop))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                createProductInputDataModel ->
                                        getView().onDataLoaded(createProductInputDataModel),
                                this::onError)
        );
    }

    private Completable initMappers() {
        return Completable.fromAction(() -> {
            String shopNameAll = constantStringDao.getByName(ConstantString.SHOP_NAME_ALL);
            productInShopMapper.setShopNameAll(shopNameAll);
            inputDataModelMapper.setShopNameAll(shopNameAll);
        });
    }

    @Override
    public void createProduct(CreateProductOutputModel productModel) {
        getCompositeDisposable().add(
                Single.fromCallable(() -> productMapper.map(productModel))
                        .doOnSuccess(productDao::insertNewWithReplace)
                        .flatMapCompletable(this::removeFromProductInShop)
                        .andThen(Observable.fromIterable(productInShopMapper.map(productModel)))
                        .flatMapCompletable(productInShop -> Completable.fromAction(() -> productInShopDao.insertNew(productInShop)))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> getView().onProductCreated(),
                                error -> onCreateProductError(productModel.getName(), error))
        );
    }

    private Completable removeFromProductInShop(Product product) {
        return Completable.fromAction(() -> {
            List<ProductInShop> inShopList = productInShopDao.getByProduct(product.getName());
            productInShopDao.delete(inShopList);
        });
    }

    private void onCreateProductError(String productName, Throwable error) {
        if (error instanceof SQLiteConstraintException) {
            getView().showProductAlreadyExistsError(productName);
            return;
        }

        onError(error);
    }

    @Override
    public void loadProduct(String productName) {
        getCompositeDisposable().add(
                Single.fromCallable(() -> {
                    Product product = productDao.getByName(productName);
                    List<ProductInShop> productInShops = productInShopDao.getByProduct(productName);
                    return productInShopMapper.map(product, productInShops);
                })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                createProductOutputModel -> getView().onProductLoaded(createProductOutputModel),
                                this::onError)
        );
    }

    @Override
    public void deleteProduct(String productName) {
        getCompositeDisposable().add(
                Completable.fromAction(() -> {
                            List<ProductInShop> inShops = productInShopDao.getByProduct(productName);
                            productInShopDao.delete(inShops);

                            Product product = productDao.getByName(productName);
                            productDao.delete(product);
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> getView().onProductDeleted(),
                                this::onError)
        );
    }
}
