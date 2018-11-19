package com.akhutornoy.shoppinglist.addproducts.presenter;

import com.akhutornoy.shoppinglist.addproducts.contract.AddProductsContract;
import com.akhutornoy.shoppinglist.addproducts.mapper.AddProductModelMapper;
import com.akhutornoy.shoppinglist.addproducts.mapper.ToBuyMapper;
import com.akhutornoy.shoppinglist.addproducts.model.AddProductModel;
import com.akhutornoy.shoppinglist.domain.CurrentShopDao;
import com.akhutornoy.shoppinglist.domain.ProductDao;
import com.akhutornoy.shoppinglist.domain.ProductInShopDao;
import com.akhutornoy.shoppinglist.domain.ToBuy;
import com.akhutornoy.shoppinglist.domain.ToBuyDao;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AddProductsPresenter extends AddProductsContract.Presenter {
    private final CurrentShopDao mDbCurrentShop;
    private final ProductInShopDao mDbProductInShop;
    private final ProductDao mDbProduct;
    private final ToBuyDao mDbToBuy;
    private final AddProductModelMapper mAddProductModelMapper;
    private final ToBuyMapper mToBuyMapper;

    public AddProductsPresenter(CurrentShopDao dbCurrentShop,
                                ProductInShopDao dbProductInShop,
                                ProductDao dbProduct,
                                ToBuyDao dbToBuy,
                                AddProductModelMapper addProductModelMapper,
                                ToBuyMapper toBuyMapper) {
        mDbCurrentShop = dbCurrentShop;
        mDbProductInShop = dbProductInShop;
        mDbProduct = dbProduct;
        mDbToBuy = dbToBuy;
        mAddProductModelMapper = addProductModelMapper;
        mToBuyMapper = toBuyMapper;
    }

    @Override
    public void loadProducts() {
        getCompositeDisposable().add(
                Observable.fromCallable(() -> mDbCurrentShop.get())
                        .flatMapIterable(currentShop -> mDbProductInShop.getByShop(currentShop))
                        .map(productInShop -> mDbProduct.getByName(productInShop.getProduct()))
                        .map(productDb -> mAddProductModelMapper.map(productDb))
                        .toList()
                        .zipWith(mDbToBuy.getAllByCurrentShop().firstOrError(), this::zipAddProductsWithToBuys)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(ignored -> getView().showProgress())
                        .doFinally(() -> getView().hideProgress())
                        .subscribe(products -> getView().onDataLoaded(products), this::onError)
        );
    }

    private List<AddProductModel> zipAddProductsWithToBuys(List<AddProductModel> addProductModels, List<ToBuy> toBuys) {
        return mAddProductModelMapper.map(addProductModels, toBuys);
    }

    @Override
    public void saveSelectedProducts(List<AddProductModel> selectedProducts) {
        getView().showProgress();
        getCompositeDisposable().add(
                Single.fromCallable(() -> mDbCurrentShop.get())
                        .doOnSuccess(mDbToBuy::deleteAllByShop)
                        .map(currentShop -> {
                            mToBuyMapper.setShopName(currentShop);
                            return mToBuyMapper.map(selectedProducts);
                        })
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
                        })
        );
    }
}
