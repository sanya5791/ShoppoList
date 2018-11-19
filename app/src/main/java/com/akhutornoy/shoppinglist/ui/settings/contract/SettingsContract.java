package com.akhutornoy.shoppinglist.ui.settings.contract;

import com.akhutornoy.shoppinglist.ui.base.presenter.BasePresenter;
import com.akhutornoy.shoppinglist.ui.base.view.BaseView;

import java.io.InputStream;
import java.io.OutputStream;

import javax.annotation.Nonnull;

public interface SettingsContract {
    interface View extends BaseView {
        void reInitDb();
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void createBackup(@Nonnull OutputStream outputStream);
        public abstract void restoreBackup(@Nonnull InputStream inputStream);
    }
}
