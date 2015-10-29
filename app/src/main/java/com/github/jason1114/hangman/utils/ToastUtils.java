package com.github.jason1114.hangman.utils;

import android.os.Looper;
import android.support.annotation.StringRes;
import android.widget.Toast;


/**
 * Common Toast Utils
 * Created by Jason on 2015/6/1.
 */
public class ToastUtils {
    public static void show(@StringRes final int resId, final int length) {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                Toast.makeText(App.get(), resId, length).show();
            } else {
                App.get().getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(App.get(), resId, length).show();
                    }
                });
            }

        } catch (Exception e) {
            // ignore
        }
    }

    public static void show(final String msg, final int length) {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                Toast.makeText(App.get(), msg, length).show();
            } else {
                App.get().getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(App.get(), msg, length).show();
                    }
                });
            }
        } catch (Exception e) {
            // ignore
        }
    }

    /**
     * show toast short
     */
    public static void s(String msg) {
        show(msg, Toast.LENGTH_SHORT);
    }

    public static void s(@StringRes int resId) {
        show(resId, Toast.LENGTH_SHORT);
    }

    public static void l(String msg) {
        show(msg, Toast.LENGTH_LONG);
    }

    public static void l(@StringRes int resId) {
        show(resId, Toast.LENGTH_LONG);
    }
}
