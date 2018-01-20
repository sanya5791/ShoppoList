package com.akhutornoy.shoppinglist.shops;

import com.akhutornoy.shoppinglist.base.presenter.BasePresenter;
import com.akhutornoy.shoppinglist.base.view.LoadableView;
import com.akhutornoy.shoppinglist.domain.AppDatabase;
import com.akhutornoy.shoppinglist.domain.ShopDao;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public interface BaseShopsContract {
    interface View extends LoadableView<List<BaseShopModel>> {}

    abstract class Presenter extends BasePresenter<View> {
        protected ShopDao mDbShop;
        protected BaseShopModelMapper mShopModelMapper;

        public Presenter(AppDatabase appDatabase) {
            mDbShop = appDatabase.toShop();
            mShopModelMapper = new BaseShopModelMapper();
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
    }
}
