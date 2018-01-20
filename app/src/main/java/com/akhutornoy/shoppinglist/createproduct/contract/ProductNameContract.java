package com.akhutornoy.shoppinglist.createproduct.contract;

import com.akhutornoy.shoppinglist.base.presenter.BasePresenter;
import com.akhutornoy.shoppinglist.base.view.BaseView;

public interface ProductNameContract {
    interface View extends BaseView {}

    abstract class Presenter extends BasePresenter<View> {
    }
}
