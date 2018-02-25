package com.akhutornoy.shoppinglist.addproducts.presenter;

import com.akhutornoy.shoppinglist.addproducts.contract.AddProductsContract;
import com.akhutornoy.shoppinglist.addproducts.mapper.AddProductModelMapper;
import com.akhutornoy.shoppinglist.addproducts.mapper.ToBuyMapper;
import com.akhutornoy.shoppinglist.addproducts.model.AddProductModel;
import com.akhutornoy.shoppinglist.domain.AppDatabase;
import com.akhutornoy.shoppinglist.domain.CurrentShopDao;
import com.akhutornoy.shoppinglist.domain.ProductDao;
import com.akhutornoy.shoppinglist.domain.ProductInShopDao;
import com.akhutornoy.shoppinglist.domain.ToBuyDao;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AddProductsPresenter extends AddProductsContract.Presenter {
    private CurrentShopDao mDbCurrentShop;
    private ProductInShopDao mDbProductInShop;
    private ProductDao mDbProduct;
    private ToBuyDao mDbToBuy;
    private AddProductModelMapper mAddProductModelMapper;
    private ToBuyMapper mToBuyMapper;

    public AddProductsPresenter(AppDatabase appDatabase) {
        mDbCurrentShop = appDatabase.toCurrentShop();
        mDbProductInShop = appDatabase.toProductInShop();
        mDbProduct = appDatabase.toProduct();
        mDbToBuy = appDatabase.toBuy();
        mAddProductModelMapper = new AddProductModelMapper();
        mToBuyMapper = new ToBuyMapper();
    }

    @Override
    public void loadProducts() {
        getView().showProgress();

        getCompositeDisposable().add(
                Observable.fromCallable(() -> mDbCurrentShop.get())
                        .map(mDbCurrentShop -> mDbProductInShop.getByShop(mDbCurrentShop))
                        .flatMapIterable(productInShops -> productInShops)
                        .map(productInShop -> mDbProduct.getByName(productInShop.getProduct()))
                        .map(productsDb -> mAddProductModelMapper.map(productsDb))
                        .toList()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(products -> {
                            getView().hideProgress();
                            getView().onDataLoaded(products);
                        }, error -> {
                            getView().hideProgress();
                            onError(error);
                        })
        );
    }

    @Override
    public void saveSelectedProducts(List<AddProductModel> selectedProducts) {
        getView().showProgress();
        Completable.fromAction(() -> mDbToBuy.deleteAll())
                .andThen(Observable.fromIterable(selectedProducts))
                .map(addProductModel -> mDbProduct.getByName(addProductModel.getName()))
                .map(mToBuyMapper::map)
                .toList()
                .flatMapCompletable(toBuys ->
                        Completable.fromAction(() -> mDbToBuy.insertNew(toBuys)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    getView().hideProgress();
                    getView().finishScreen();
                }, error -> {
                    getView().hideProgress();
                    onError(error);
                });
    }
}
