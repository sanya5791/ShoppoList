package com.akhutornoy.shoppinglist.createproduct.contract;

import com.akhutornoy.shoppinglist.base.presenter.BasePresenter;
import com.akhutornoy.shoppinglist.base.view.ProgressView;

public interface CreateProductNameContract {
    interface View extends ProgressView {
        void showNameAlreadyExistsDialog(String name);
        void nameCreated(String name);
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void createName(String newName);
    }
}
