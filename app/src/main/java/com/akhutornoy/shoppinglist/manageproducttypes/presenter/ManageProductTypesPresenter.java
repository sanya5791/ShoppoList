package com.akhutornoy.shoppinglist.manageproducttypes.presenter;

import com.akhutornoy.shoppinglist.base.model.ItemModel;
import com.akhutornoy.shoppinglist.domain.AppDatabase;
import com.akhutornoy.shoppinglist.domain.ProductType;
import com.akhutornoy.shoppinglist.domain.ProductTypeDao;
import com.akhutornoy.shoppinglist.manageproducttypes.contract.ManageProductTypesContract;
import com.akhutornoy.shoppinglist.manageproducttypes.mapper.ItemModelMapper;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ManageProductTypesPresenter extends ManageProductTypesContract.Presenter {
    private ProductTypeDao mDbProductType;
    private ItemModelMapper mItemModelMapper;

    public ManageProductTypesPresenter(AppDatabase appDatabase) {
        mDbProductType = appDatabase.toProductType();
        mItemModelMapper = new ItemModelMapper();
    }

    @Override
    public void loadItems() {
        getView().showProgress();
        getCompositeDisposable().add(
                mDbProductType.getAll()
                        .map(shops -> mItemModelMapper.map(shops))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                models -> {
                                    getView().hideProgress();
                                    getView().onDataLoaded(models);
                                }, error -> {
                                    getView().hideProgress();
                                    super.onError(error);
                                })
        );
    }

    @Override
    public void addNew(String productType) {
        getView().showProgress();
        getCompositeDisposable().add(
                Completable.fromAction(() -> mDbProductType.insertNew(new ProductType(productType)))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(() -> {
                        getView().hideProgress();
                    }, error -> {
                        getView().hideProgress();
                        super.onError(error);
                    })

        );
    }

    @Override
    public void delete(ItemModel productType) {
        getView().showProgress();
        getCompositeDisposable().add(
                Observable.fromCallable(() -> mItemModelMapper.mapInverse(productType))
                        .flatMapCompletable(productTypeDb -> Completable.fromAction(
                                () -> mDbProductType.delete(productTypeDb)))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(() -> {
                            getView().hideProgress();
                        }, error -> {
                            getView().hideProgress();
                            super.onError(error);
                        })
        );
    }
}
