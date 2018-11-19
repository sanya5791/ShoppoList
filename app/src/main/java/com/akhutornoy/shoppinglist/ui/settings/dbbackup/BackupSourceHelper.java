package com.akhutornoy.shoppinglist.ui.settings.dbbackup;

import android.content.Context;

import java.io.File;

import javax.annotation.Nullable;

import timber.log.Timber;

public class BackupSourceHelper {
    private static final String PRIVATE_APP_STORAGE_FOLDER_DB_NAME = "databases";
    private static final String PRIVATE_APP_STORAGE_TEMP_FOLDER_DB_NAME = "temp";

    private final Context context;

    public BackupSourceHelper(Context context) {
        this.context = context;
    }

    @Nullable
    public File getDbSourceFolder() {

        File dataDir = context.getDataDir();
        File dataBaseFolder = new File(dataDir, PRIVATE_APP_STORAGE_FOLDER_DB_NAME);
        if (!dataBaseFolder.exists()) {
            Timber.e ("Can't Create Backup since Private DB folder is NOT found");
            return null;
        }
        if (!dataBaseFolder.isDirectory()) {
            Timber.e ("Can't Create Backup since Private DB folder is NOT folder");
            return null;
        }

        return dataBaseFolder;
    }

    public File getDbTempFolder() {
        File dbSourceFolder = getDbSourceFolder();
        File tempFolder = new File(dbSourceFolder, PRIVATE_APP_STORAGE_TEMP_FOLDER_DB_NAME);

        if (!tempFolder.exists()) {
            return tempFolder.mkdir()
                    ? tempFolder
                    : null;
        }

        if (!tempFolder.isDirectory()) {
            return null;
        }

        return tempFolder;
    }
}
