package com.akhutornoy.shoppinglist.ui.shops.manageshops.presenter;

import com.akhutornoy.shoppinglist.domain.ConstantString;
import com.akhutornoy.shoppinglist.domain.ConstantStringDao;
import com.akhutornoy.shoppinglist.domain.Shop;
import com.akhutornoy.shoppinglist.domain.ShopDao;
import com.akhutornoy.shoppinglist.ui.base.model.ItemModel;
import com.akhutornoy.shoppinglist.ui.shops.manageshops.contract.ManageShopsContract;
import com.akhutornoy.shoppinglist.ui.shops.mapper.ItemModelMapper;
import com.akhutornoy.shoppinglist.ui.shops.mapper.ShopModelFilteredMapper;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ManageShopsPresenter extends ManageShopsContract.Presenter {
    private final ShopDao mDbShop;
    private final ConstantStringDao mDbConstantString;
    private final ShopModelFilteredMapper mShopModelMapper;
    private final ItemModelMapper mItemModelMapper;

    public ManageShopsPresenter(ShopDao dbShop, ConstantStringDao constantStringDao, ShopModelFilteredMapper shopModelMapper, ItemModelMapper itemModelMapper) {
        mDbShop = dbShop;
        mDbConstantString = constantStringDao;
        mShopModelMapper = shopModelMapper;
        mItemModelMapper = itemModelMapper;
    }

    @Override
    public void loadItems() {
        getCompositeDisposable().add(
                initShopModelMapper()
                        .andThen(mDbShop.getAll())
                        .map(mShopModelMapper::map)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(ignored -> getView().showProgress())
                        .doOnError(ignored -> getView().showProgress())
                        .doAfterNext(ignored -> getView().hideProgress())
                        .subscribe(
                                manageShopModels -> getView().onDataLoaded(manageShopModels),
                                super::onError)
        );
    }

    private Completable initShopModelMapper() {
        return Single.fromCallable(() -> mDbConstantString.getByName(ConstantString.SHOP_NAME_ALL))
                .flatMapCompletable(all1 -> Completable.fromAction(() -> mShopModelMapper.setFilter(all1)));
    }

    @Override
    public void addNew(String shopName) {
        getCompositeDisposable().add(
                Completable.fromAction(() -> mDbShop.insertNew(new Shop(shopName)))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe(ignored -> getView().showProgress())
                    .doOnComplete(() -> getView().showProgress())
                    .subscribe(
                            () -> {},
                            super::onError)
        );
    }

    @Override
    public void delete(ItemModel shopModel) {
        getCompositeDisposable().add(
                Observable.fromCallable(() -> mItemModelMapper.mapInverse(shopModel))
                        .flatMapCompletable(shop -> Completable.fromAction(
                                () -> mDbShop.delete(shop)))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(ignored -> getView().showProgress())
                        .doOnComplete(() -> getView().showProgress())
                        .subscribe(
                                () -> {},
                                super::onError)
        );
    }
}
