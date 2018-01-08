package com.akhutornoy.shoppinglist.createroduct.contract;

import com.akhutornoy.shoppinglist.base.presenter.BasePresenter;
import com.akhutornoy.shoppinglist.base.view.BaseView;

public interface ProductDefaultQuantityContract {
    interface View extends BaseView {}

    abstract class Presenter extends BasePresenter<View> {
    }
}
