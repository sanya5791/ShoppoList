package com.akhutornoy.shoppinglist.managemeasuretypes.presenter;

import com.akhutornoy.shoppinglist.base.model.ItemModel;
import com.akhutornoy.shoppinglist.domain.AppDatabase;
import com.akhutornoy.shoppinglist.domain.MeasureType;
import com.akhutornoy.shoppinglist.domain.MeasureTypeDao;
import com.akhutornoy.shoppinglist.managemeasuretypes.contract.ManageMeasureTypesContract;
import com.akhutornoy.shoppinglist.managemeasuretypes.mapper.ItemModelMapper;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ManageMeasureTypesPresenter extends ManageMeasureTypesContract.Presenter {
    private MeasureTypeDao mDbMeasureType;
    private ItemModelMapper mItemModelMapper;

    public ManageMeasureTypesPresenter(AppDatabase appDatabase) {
        mDbMeasureType = appDatabase.toMeasureType();
        mItemModelMapper = new ItemModelMapper();
    }

    @Override
    public void loadItems() {
        getView().showProgress();
        getCompositeDisposable().add(
                mDbMeasureType.getAll()
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
    public void addNew(String measureType) {
        getView().showProgress();
        getCompositeDisposable().add(
                Completable.fromAction(() -> mDbMeasureType.insertNew(new MeasureType(measureType)))
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
    public void delete(ItemModel measureType) {
        getView().showProgress();
        getCompositeDisposable().add(
                Observable.fromCallable(() -> mItemModelMapper.mapInverse(measureType))
                        .flatMapCompletable(measureTypeDb -> Completable.fromAction(
                                () -> mDbMeasureType.delete(measureTypeDb)))
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
