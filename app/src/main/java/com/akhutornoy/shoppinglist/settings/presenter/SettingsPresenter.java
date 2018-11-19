package com.akhutornoy.shoppinglist.settings.presenter;

import android.content.Context;

import com.akhutornoy.shoppinglist.settings.contract.SettingsContract;
import com.akhutornoy.shoppinglist.settings.dbbackup.BackupFilesException;
import com.akhutornoy.shoppinglist.settings.dbbackup.BackupSourceHelper;
import com.akhutornoy.shoppinglist.settings.dbbackup.Zipper;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

import javax.annotation.Nonnull;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SettingsPresenter extends SettingsContract.Presenter {
    private final BackupSourceHelper mBackupSourceHelper;
    private final TempDbHandler mTempDb;
    private final Zipper mZipper;

    public SettingsPresenter(Context context) {
        mBackupSourceHelper = new BackupSourceHelper(context);
        mTempDb = new TempDbHandler(mBackupSourceHelper);
        mZipper = new Zipper();
    }

    @Override
    public void createBackup(@Nonnull OutputStream outputStream) {
        getCompositeDisposable().add(
                Completable.fromAction(() -> createDbZipBackup(outputStream))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> {},
                                this::onError)
        );
    }

    private void createDbZipBackup(OutputStream outputStream) {
        File sourceDbFolder = mBackupSourceHelper.getDbSourceFolder();
        if (sourceDbFolder == null) {
            throw new BackupFilesException("Can't Create Backup since External Storage is NOT Available");
        }
        mZipper.zipAll(sourceDbFolder, outputStream);
    }

    @Override
    public void restoreBackup(@Nonnull InputStream inputStream) {
        getCompositeDisposable().add(
                Completable.fromAction (() -> restoreDbZipBackupAndCloseStream(inputStream))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> getView().reInitDb(),
                                this::onError)
        );
    }

    private void restoreDbZipBackupAndCloseStream(InputStream inputStream) {
        File destinationDbFolder = mBackupSourceHelper.getDbSourceFolder();
        if (destinationDbFolder == null) {
            throw new BackupFilesException("Can't Create Backup since External Storage is NOT Available");
        }

        mTempDb.createTempDb(destinationDbFolder);

        try {
            mZipper.unzipAll(destinationDbFolder, inputStream);
        } catch (Exception e) {
            mTempDb.restoreTempDb(destinationDbFolder);
            getView().onError("Db restore Error.");
        }
        mTempDb.deleteTempDb();
    }
}
