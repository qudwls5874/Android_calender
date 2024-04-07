package com.example.myapplication.event;

import android.app.Activity;
import android.app.Dialog;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class HideKeyboardHelperDialog {

    public static void setupUI(View view, final Dialog dialog) {
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(dialog);
                    view.requestFocus();
                    return false;
                }
            });
        }

        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView, dialog);
            }
        }
    }

    public static void hideSoftKeyboard(Dialog dialog) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) dialog.getContext().getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        View focusedView = dialog.getWindow().getCurrentFocus();
        if (focusedView != null) {
            inputMethodManager.hideSoftInputFromWindow(
                    focusedView.getWindowToken(), 0);
        }
    }

}