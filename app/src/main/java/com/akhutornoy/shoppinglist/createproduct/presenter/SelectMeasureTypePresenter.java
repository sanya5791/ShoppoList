package com.akhutornoy.shoppinglist.createproduct.presenter;

import com.akhutornoy.shoppinglist.createproduct.contract.SelectMeasureTypeContract;
import com.akhutornoy.shoppinglist.createproduct.mapper.MeasureTypeModelMapper;
import com.akhutornoy.shoppinglist.createproduct.model.MeasureTypeModel;
import com.akhutornoy.shoppinglist.domain.AppDatabase;
import com.akhutornoy.shoppinglist.domain.Product;
import com.akhutornoy.shoppinglist.domain.ProductDao;
import com.akhutornoy.shoppinglist.domain.MeasureTypeDao;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SelectMeasureTypePresenter extends SelectMeasureTypeContract.Presenter {
    private final MeasureTypeDao mMeasureTypeDao;
    private final ProductDao mProductDao;
    private final MeasureTypeModelMapper mMeasureTypeMapper;

    public SelectMeasureTypePresenter(AppDatabase appDatabase) {
        mMeasureTypeDao = appDatabase.toMeasureType();
        mProductDao = appDatabase.toProduct();
        mMeasureTypeMapper = new MeasureTypeModelMapper();
    }

    @Override
    public void loadTypes(String productName) {
        getView().showProgress();
        getCompositeDisposable().add(
                mMeasureTypeDao.getAll()
                        .map(mMeasureTypeMapper::map)
                        .map(measureTypeModels -> setPresetType(measureTypeModels, productName))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(measureTypeModels -> {
                            getView().hideProgress();
                            getView().onDataLoaded(measureTypeModels);
                        }, error -> {
                            getView().hideProgress();
                            super.onError(error);
                        })
        );
    }

    private List<MeasureTypeModel> setPresetType(List<MeasureTypeModel> typeModels, String productName) {
        String presetType = getPresetType(productName);
        if (presetType.isEmpty()) {
            return typeModels;
        }

        for (MeasureTypeModel typeModel : typeModels) {
            String typeName = typeModel.getName();
            typeModel.setChecked(presetType.equals(typeName));
        }
        return typeModels;
    }

    private String getPresetType(String productName) {
        Product product = mProductDao.getByName(productName);
        return (product.getMeasureType() == null) ? "" : product.getMeasureType();
    }

    @Override
    public void setType(String productName, String selectedMeasureType) {
        getView().showProgress();
        getCompositeDisposable().add(
                Observable.fromCallable(() -> mProductDao.getByName(productName))
                        .flatMapCompletable(product -> {
                            product.setMeasureType(selectedMeasureType);
                            return Completable.fromAction(() -> mProductDao.update(product));
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(() -> {
                            getView().hideProgress();
                            getView().onTypeSet();
                        }, super::onError)
        );
    }
}
