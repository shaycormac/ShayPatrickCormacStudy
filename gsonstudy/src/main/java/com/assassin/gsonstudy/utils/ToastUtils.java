package com.assassin.gsonstudy.utils;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

public class ToastUtils {

    private static Toast mToast;

    public static void ToastBottomMsg(Context context, String message) {
        if (context == null || TextUtils.isEmpty(message)) {
            return;
        }
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void ToastTopMsg(Context context, String message) {
        if (context == null || TextUtils.isEmpty(message)) {
            return;
        }
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 200);
        toast.show();
    }

    public static void ToastLongMessage(Context context, String message) {
        if (context == null || TextUtils.isEmpty(message)) {
            return;
        }
       Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void ToastFailureMessage(Context context) {
        if (context == null) {
            return;
        }
        Toast.makeText(context, "提交失败，请重试！", Toast.LENGTH_LONG).show();
    }
    
}
