package com.akhutornoy.shoppinglist.addproducts.presenter;

import com.akhutornoy.shoppinglist.addproducts.contract.AddProductsContract;
import com.akhutornoy.shoppinglist.addproducts.mapper.AddProductModelMapper;
import com.akhutornoy.shoppinglist.domain.AppDatabase;
import com.akhutornoy.shoppinglist.domain.CurrentShopDao;
import com.akhutornoy.shoppinglist.domain.ProductDao;
import com.akhutornoy.shoppinglist.domain.ProductInShopDao;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AddProductsPresenter extends AddProductsContract.Presenter {
    private CurrentShopDao mDbCurrentShop;
    private ProductInShopDao mDbProductInShop;
    private ProductDao mDbProduct;
    private AddProductModelMapper mAddProductModelMapper;

    public AddProductsPresenter(AppDatabase appDatabase) {
        mDbCurrentShop = appDatabase.toCurrentShop();
        mDbProductInShop = appDatabase.toProductInShop();
        mDbProduct = appDatabase.toProduct();
        mAddProductModelMapper = new AddProductModelMapper();
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
}
