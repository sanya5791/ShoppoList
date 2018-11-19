package com.akhutornoy.shoppinglist.ui.settings.dbbackup;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.annotation.Nullable;

public class BackupDestinationHelper {
    static final int BACKUP_READ_REQUEST_CODE = 42;
    static final int BACKUP_WRITE_REQUEST_CODE = 43;
    private static final String ZIP_MIME_TYPE = "application/zip";

    private static final String BACKUP_FILE_NAME_START = "ShoppoList-Backup-";
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    public boolean isRequestCodeCreateBackup(int onActivityResultRequestCode) {
        return BACKUP_WRITE_REQUEST_CODE == onActivityResultRequestCode;
    }

    public boolean isRequestCodeRestoreBackup(int onActivityResultRequestCode) {
        return BACKUP_READ_REQUEST_CODE == onActivityResultRequestCode;
    }

    /**
     * Starts Android 'Storage Access Framework' to define destination Folder and
     * create backup destination 'zip' file there.
     *
     * tip: override FragmentActivity#onActivityResult and pass result into {@link #getCreateBackupFileOutputStream}
     */
    public void startCreateBackupFileOperation(Fragment fragment) {
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        intent.setType(ZIP_MIME_TYPE);
        intent.putExtra(Intent.EXTRA_TITLE, getBackupFileName());
        fragment.startActivityForResult(intent, BACKUP_WRITE_REQUEST_CODE);
    }

    private String getBackupFileName() {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
        return BACKUP_FILE_NAME_START + sdf.format(new Date());
    }

    /**
     * Starts Android 'Storage Access Framework' to pick a 'zip' with backup there
     *
     * tip: override FragmentActivity#onActivityResult and pass result into {@link #getRestoreBackupInputStream}
     */
    public void startRestoreBackupZipFile(Fragment fragment) {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType(ZIP_MIME_TYPE);

        fragment.startActivityForResult(intent, BACKUP_READ_REQUEST_CODE);
    }

    @Nullable
    public OutputStream getCreateBackupFileOutputStream(Context context, int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK && data != null) {
            Uri uri = data.getData();
            ContentResolver cr = context.getContentResolver();
            if (uri != null && cr != null) {
                try {
                    OutputStream outputStream = cr.openOutputStream(uri);
                    return outputStream;
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public InputStream getRestoreBackupInputStream(Context context, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && data != null) {
            Uri uri = data.getData();
            ContentResolver cr = context.getContentResolver();
            if (uri != null && cr != null) {
                try {
                    InputStream inputStream = cr.openInputStream(uri);
                    return inputStream;
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

}
