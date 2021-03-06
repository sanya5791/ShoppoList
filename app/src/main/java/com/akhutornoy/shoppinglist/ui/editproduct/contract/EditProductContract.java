package com.akhutornoy.shoppinglist.ui.editproduct.contract;

import com.akhutornoy.shoppinglist.ui.createproduct_onescreen.contract.CreateProductContract;
import com.akhutornoy.shoppinglist.ui.createproduct_onescreen.model.CreateProductOutputModel;

public interface EditProductContract extends CreateProductContract {
    interface View extends CreateProductContract.View {
        void onProductLoaded(CreateProductOutputModel createProductOutputModel);
        void onProductDeleted();
    }

    abstract class Presenter extends CreateProductContract.Presenter<View> {
        public abstract void loadProduct(String productName);
        public abstract void deleteProduct(String productName);
    }
}
