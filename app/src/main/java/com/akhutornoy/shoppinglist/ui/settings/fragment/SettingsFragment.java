package com.akhutornoy.shoppinglist.ui.settings.fragment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.akhutornoy.shoppinglist.Injections;
import com.akhutornoy.shoppinglist.ui.MainActivity;
import com.akhutornoy.shoppinglist.R;
import com.akhutornoy.shoppinglist.ui.base.fragment.BaseFragment;
import com.akhutornoy.shoppinglist.ui.base.presenter.BasePresenter;
import com.akhutornoy.shoppinglist.ui.settings.contract.SettingsContract;
import com.akhutornoy.shoppinglist.ui.settings.dbbackup.BackupDestinationHelper;
import com.akhutornoy.shoppinglist.ui.util.ui.AlertDialogUtils;

import java.io.InputStream;
import java.io.OutputStream;

import javax.annotation.Nullable;

import timber.log.Timber;

public class SettingsFragment extends BaseFragment implements SettingsContract.View {

    private SettingsContract.Presenter mPresenter;

    private BackupDestinationHelper mBackupDestinationHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        mPresenter = Injections.provideSettingsPresenter(getActivity());
        mBackupDestinationHelper = new BackupDestinationHelper();
        initBackupButtons(view);

        return view;
    }

    @Override
    @LayoutRes
    protected int getFragmentLayoutId() {
        return R.layout.fragment_settings;
    }

    @Override
    protected BasePresenter getBasePresenter() {
        return mPresenter;
    }

    private void initBackupButtons(View view) {
        view.findViewById(R.id.bt_backup_create).setOnClickListener(v ->
                onBackupCreateClicked());

        view.findViewById(R.id.bt_backup_restore).setOnClickListener(v ->
                onBackupRestoreClicked());
    }

    private void onBackupCreateClicked() {
        mBackupDestinationHelper.startCreateBackupFileOperation(this);
    }

    private void onBackupRestoreClicked() {
        mBackupDestinationHelper.startRestoreBackupZipFile(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Timber.e("onActivityResult()");

        if (mBackupDestinationHelper.isRequestCodeCreateBackup(requestCode)) {
            OutputStream os = mBackupDestinationHelper.getCreateBackupFileOutputStream(getActivity(), resultCode, data);
            createBackup(os);
        } else if (mBackupDestinationHelper.isRequestCodeRestoreBackup(requestCode)) {
            InputStream is = mBackupDestinationHelper.getRestoreBackupInputStream(getActivity(), resultCode, data);
            restoreBackup(is);
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void createBackup(@Nullable OutputStream os) {
        if (os != null) {
            mPresenter.createBackup(os);
        } else {
            onError(getString(R.string.error_backup_create));
        }
    }

    private void restoreBackup(@Nullable InputStream is) {
        if (is != null) {
            mPresenter.restoreBackup(is);
        } else {
            onError(getString(R.string.error_backup_restore_file));
        }
    }

    @Override
    public void onError(String errorMsg) {
        Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void reInitDb() {
        AlertDialogUtils.getOkCancelDialogBuilder(getActivity(), this::restartApp)
                .setMessage(R.string.message_restore_db_data_will_be_available_after_restart)
                .show();
    }

    private void restartApp() {
        Intent startApp = new Intent(getActivity(), MainActivity.class);

        int pendingIntentId = 123456;
        PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), pendingIntentId, startApp,
                PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager mgr = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, pendingIntent);
        getActivity().finishAffinity();
    }
}
