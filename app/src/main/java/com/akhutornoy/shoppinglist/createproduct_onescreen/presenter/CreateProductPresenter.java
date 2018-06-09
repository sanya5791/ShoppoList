package com.akhutornoy.shoppinglist.createproduct_onescreen.presenter;

import com.akhutornoy.shoppinglist.createproduct_onescreen.contract.CreateProductContract;
import com.akhutornoy.shoppinglist.createproduct_onescreen.mapper.CreateProductInputDataModelMapper;
import com.akhutornoy.shoppinglist.createproduct_onescreen.model.CreateProductInputDataModel;
import com.akhutornoy.shoppinglist.createproduct_onescreen.model.CreateProductOutputModel;
import com.akhutornoy.shoppinglist.domain.AppDatabase;
import com.akhutornoy.shoppinglist.domain.MeasureTypeDao;
import com.akhutornoy.shoppinglist.domain.ShopDao;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CreateProductPresenter extends CreateProductContract.Presenter {
    private final MeasureTypeDao measureTypeDao;
    private final ShopDao shopDao;
    private final CreateProductInputDataModelMapper inputDataModelMapper;

    public CreateProductPresenter(AppDatabase db) {
        measureTypeDao = db.toMeasureType();
        shopDao = db.toShop();
        inputDataModelMapper = new CreateProductInputDataModelMapper();
    }

    @Override
    public void loadInputData() {
        getCompositeDisposable().add(
                measureTypeDao.getAll()
                        .zipWith(shopDao.getAll(), inputDataModelMapper::map)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                createProductInputDataModel ->
                                        getView().onDataLoaded(createProductInputDataModel),
                                this::onError)
        );
    }

    @Override
    public void createProduct(CreateProductOutputModel productModel) {}
}
