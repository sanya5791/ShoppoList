package com.akhutornoy.shoppinglist.ui.settings.presenter;

import com.akhutornoy.shoppinglist.ui.settings.dbbackup.BackupFilesException;
import com.akhutornoy.shoppinglist.ui.settings.dbbackup.BackupSourceHelper;

import java.io.File;

import timber.log.Timber;

public class TempDbHandler {
    private final BackupSourceHelper backupSourceHelper;

    public TempDbHandler(BackupSourceHelper backupSourceHelper) {
        this.backupSourceHelper = backupSourceHelper;
    }

    public void createTempDb(File destinationDbFolder) {
        File tempFolder = backupSourceHelper.getDbTempFolder();
        if (tempFolder == null) {
            throw new BackupFilesException("Can't restore DB backup since 'temp' folder is NOT created");
        }

        for (File file : destinationDbFolder.listFiles()) {
            //noinspection ResultOfMethodCallIgnored
            file.renameTo(new File(tempFolder, file.getName()));
        }
    }

    public void restoreTempDb(File destinationDbFolder) {
        Timber.e("restoreTempDb(): restoring from 'temp' data");
        File tempFolder = backupSourceHelper.getDbTempFolder();
        if (tempFolder == null) {
            throw new BackupFilesException("Can't restore DB backup since 'temp' folder is NOT created");
        }

        for (File file : tempFolder.listFiles()) {
            //noinspection ResultOfMethodCallIgnored
            file.renameTo(new File(destinationDbFolder, file.getName()));
        }

        Timber.e("restoreTempDb(): 'temp' data is restored!!!");
    }

    public void  deleteTempDb() {
        File tempFolder = backupSourceHelper.getDbTempFolder();
        if (tempFolder == null) {
            throw new BackupFilesException("Can't restore DB backup since 'temp' folder is NOT created");
        }

        deleteRecursively(tempFolder);
    }

    private boolean deleteRecursively(File fileToRemove) {
        if (fileToRemove.isDirectory()) {
            for (File file : fileToRemove.listFiles()) {
                deleteRecursively(file);
            }
        }
        return fileToRemove.delete();
    }
}
