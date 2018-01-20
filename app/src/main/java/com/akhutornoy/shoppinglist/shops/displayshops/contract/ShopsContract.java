package com.akhutornoy.shoppinglist.shops.displayshops.contract;

import com.akhutornoy.shoppinglist.domain.AppDatabase;
import com.akhutornoy.shoppinglist.shops.BaseShopsContract;

public interface ShopsContract {
    interface View extends BaseShopsContract.View {}

    abstract class Presenter extends BaseShopsContract.Presenter {
        public Presenter(AppDatabase appDatabase) {
            super(appDatabase);
        }
    }
}
