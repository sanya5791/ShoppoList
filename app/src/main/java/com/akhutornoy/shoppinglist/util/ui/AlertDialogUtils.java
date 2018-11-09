package com.akhutornoy.shoppinglist.util.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.akhutornoy.shoppinglist.R;
import com.akhutornoy.shoppinglist.util.KeyboardUtils;

import java.util.Objects;

public class AlertDialogUtils {
    public interface OnEditTextAlertDialogListener {
        void onOkClicked(String value);
    }

    public static AlertDialog.Builder getOkCancelEditTextWithSuffixDialog(Context context, String suffix,  OnEditTextAlertDialogListener listener) {
        View view = getEditTextWithSuffixView(context, suffix);
        Runnable onOkPressed = getOnOkPressedRunnable(view, listener);
        return getAddItemDialogBuilder(context, view, onOkPressed);
    }

    public static AlertDialog.Builder getNumberWithSuffixDialog(Context context, String suffix, OnEditTextAlertDialogListener listener) {
        View view = getEditTextWithSuffixView(context, suffix);
        setNumberInputType(view);
        Runnable onOkPressed = getOnOkPressedRunnable(view, listener);
        return getAddItemDialogBuilder(context, view, onOkPressed);
    }

    private static void setNumberInputType(View view) {
        EditText editText = view.findViewById(R.id.et_input);
        editText.setGravity(Gravity.END);
        editText.setImeOptions(EditorInfo.IME_ACTION_DONE);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
//        editText.requestFocus();
    }

    private static View getEditTextWithSuffixView(Context context, String suffix) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_input_with_suffix, null);
        TextView tvSuffix = view.findViewById(R.id.tv_suffix);
        tvSuffix.setText(suffix);
        return view;
    }

    private static Runnable getOnOkPressedRunnable(View view, OnEditTextAlertDialogListener listener) {
        EditText editText = view.findViewById(R.id.et_input);
        return () -> {
            String value = editText.getText().toString();
            listener.onOkClicked(value);
        };
    }

    private static AlertDialog.Builder getAddItemDialogBuilder(Context context, View customView, Runnable onOkPressed) {
        return new AlertDialog.Builder(context)
                .setView(customView)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                    onOkPressed.run();
                    dialog.dismiss();
                })
                .setNegativeButton(android.R.string.cancel, (dialog, which) -> onCancelClicked(customView, dialog));
    }

    private static void onCancelClicked(View customView, DialogInterface dialog) {
        KeyboardUtils.showKeyboard(customView);
        dialog.cancel();
    }
}