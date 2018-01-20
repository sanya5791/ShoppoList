package com.akhutornoy.shoppinglist.shops.displayshops.presenter;

import com.akhutornoy.shoppinglist.domain.AppDatabase;
import com.akhutornoy.shoppinglist.shops.displayshops.contract.ShopsContract;

public class ShopsPresenter extends ShopsContract.Presenter {
    public ShopsPresenter(AppDatabase appDatabase) {
        super(appDatabase);
    }
}
