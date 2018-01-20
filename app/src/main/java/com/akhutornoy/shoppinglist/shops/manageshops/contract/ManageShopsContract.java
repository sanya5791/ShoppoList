package com.akhutornoy.shoppinglist.shops.manageshops.contract;

import com.akhutornoy.shoppinglist.domain.AppDatabase;
import com.akhutornoy.shoppinglist.base.model.BaseShopModel;
import com.akhutornoy.shoppinglist.shops.BaseShopsContract;

public interface ManageShopsContract {
    interface View extends BaseShopsContract.View {
    }

    abstract class Presenter extends BaseShopsContract.Presenter {
        public Presenter(AppDatabase appDatabase) {
            super(appDatabase);
        }

        public abstract void addNew(String shopName);
        public abstract void delete(BaseShopModel shopModel);
    }
}
