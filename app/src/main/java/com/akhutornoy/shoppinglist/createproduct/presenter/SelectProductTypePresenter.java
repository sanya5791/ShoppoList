package com.akhutornoy.shoppinglist.createproduct.presenter;

import com.akhutornoy.shoppinglist.createproduct.contract.SelectProductTypeContract;
import com.akhutornoy.shoppinglist.createproduct.mapper.ProductTypeModelMapper;
import com.akhutornoy.shoppinglist.domain.ProductTypeDao;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SelectProductTypePresenter extends SelectProductTypeContract.Presenter {
    private final ProductTypeDao mDbProductType;
    private final ProductTypeModelMapper mProductTypeMapper;

    public SelectProductTypePresenter(ProductTypeDao productTypeDao) {
        mDbProductType = productTypeDao;
        mProductTypeMapper = new ProductTypeModelMapper();
    }

    @Override
    public void loadTypes() {
        getView().showProgress();
        getCompositeDisposable().add(
                mDbProductType.getAll()
                        .map(mProductTypeMapper::map)
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
}
