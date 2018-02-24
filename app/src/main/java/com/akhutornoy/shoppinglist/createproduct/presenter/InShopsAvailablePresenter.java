package com.akhutornoy.shoppinglist.createproduct.presenter;

import com.akhutornoy.shoppinglist.createproduct.contract.InShopsAvailableContract;
import com.akhutornoy.shoppinglist.createproduct.mapper.ShopModelMapper;
import com.akhutornoy.shoppinglist.createproduct.model.ShopModel;
import com.akhutornoy.shoppinglist.domain.AppDatabase;
import com.akhutornoy.shoppinglist.domain.Product;
import com.akhutornoy.shoppinglist.domain.ProductDao;
import com.akhutornoy.shoppinglist.domain.ProductInShop;
import com.akhutornoy.shoppinglist.domain.ProductInShopDao;
import com.akhutornoy.shoppinglist.domain.ShopDao;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class InShopsAvailablePresenter extends InShopsAvailableContract.Presenter {
    private ShopDao mDbShop;
    private ProductDao mDbProduct;
    private ProductInShopDao mDbProductInShop;
    private ShopModelMapper mShopModelMapper;

    public InShopsAvailablePresenter(AppDatabase appDatabase) {
        mDbShop = appDatabase.toShop();
        mDbProduct = appDatabase.toProduct();
        mDbProductInShop = appDatabase.toProductInShop();
        mShopModelMapper = new ShopModelMapper();
    }

    public void loadShops() {
        getView().showProgress();
        getCompositeDisposable().add(
                mDbShop.getAll()
                        .map(shops -> mShopModelMapper.map(shops))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                manageShopModels -> {
                                    getView().hideProgress();
                                    getView().onDataLoaded(manageShopModels);
                                }, error -> {
                                    getView().hideProgress();
                                    super.onError(error);
                                })
        );
    }

    @Override
    public void saveProductInShops(String productName, List<ShopModel> selectedShops) {
        getView().showProgress();
        getCompositeDisposable().add(
            Completable.fromAction(() ->
                        saveProductInShopToDb(productName, selectedShops))
                .subscribeOn(Schedulers.io())
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

    private void saveProductInShopToDb(String productName, List<ShopModel> selectedShops) {
        Product product = mDbProduct.getByName(productName);
        for (ShopModel shop : selectedShops) {
            ProductInShop productInShop = new ProductInShop(product.getName(), shop.getName());
            mDbProductInShop.insertNew(productInShop);
        }
    }
}
