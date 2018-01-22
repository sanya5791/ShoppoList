package com.akhutornoy.shoppinglist.createproduct.presenter;

import com.akhutornoy.shoppinglist.createproduct.contract.SelectProductTypeContract;
import com.akhutornoy.shoppinglist.createproduct.mapper.ProductTypeModelMapper;
import com.akhutornoy.shoppinglist.createproduct.model.ProductTypeModel;
import com.akhutornoy.shoppinglist.domain.AppDatabase;
import com.akhutornoy.shoppinglist.domain.Product;
import com.akhutornoy.shoppinglist.domain.ProductDao;
import com.akhutornoy.shoppinglist.domain.ProductTypeDao;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SelectProductTypePresenter extends SelectProductTypeContract.Presenter {
    private final ProductTypeDao mProductTypeDao;
    private final ProductDao mProductDao;
    private final ProductTypeModelMapper mProductTypeMapper;

    public SelectProductTypePresenter(AppDatabase appDatabase) {
        mProductTypeDao = appDatabase.toProductType();
        mProductDao = appDatabase.toProduct();
        mProductTypeMapper = new ProductTypeModelMapper();
    }

    @Override
    public void loadTypes(String productName) {
        getView().showProgress();
        getCompositeDisposable().add(
                mProductTypeDao.getAll()
                        .map(mProductTypeMapper::map)
                        .map(productTypeModels -> setPresetType(productTypeModels, productName))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(productTypeModels -> {
                            getView().hideProgress();
                            getView().onDataLoaded(productTypeModels);
                        }, error -> {
                            getView().hideProgress();
                            super.onError(error);
                        })
        );
    }

    private List<ProductTypeModel> setPresetType(List<ProductTypeModel> typeModels, String productName) {
        String presetType = getPresetType(productName);
        if (presetType.isEmpty()) {
            return typeModels;
        }

        for (ProductTypeModel typeModel : typeModels) {
            String typeName = typeModel.getName();
            typeModel.setChecked(presetType.equals(typeName));
        }
        return typeModels;
    }

    private String getPresetType(String productName) {
        Product product = mProductDao.getByName(productName);
        return (product.getProductType() == null) ? "" : product.getProductType();
    }

    @Override
    public void setType(String productName, String selectedProductType) {
        getView().showProgress();
        getCompositeDisposable().add(
                Observable.fromCallable(() -> mProductDao.getByName(productName))
                        .flatMapCompletable(product -> {
                            product.setProductType(selectedProductType);
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
