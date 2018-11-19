package com.akhutornoy.shoppinglist.ui.util;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class KeyboardUtils {

    public static void showKeyboard(View focusThief) {
        InputMethodManager imm = (InputMethodManager) focusThief.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    public static void hideKeyboard(View focusThief) {
        focusThief.requestFocus();
        InputMethodManager imm = (InputMethodManager) focusThief.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(focusThief.getWindowToken(), 0);
    }
}
