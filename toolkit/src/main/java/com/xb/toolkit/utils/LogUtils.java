package com.xb.toolkit.utils;


import android.text.TextUtils;
import android.util.Log;


/**
 * 日志控件
 */
public class LogUtils {

    public static boolean isDebug = true;
    public static String TAG = "LogUtils";



    public static void i(String tag, String msg) {
        if (isDebug && !TextUtils.isEmpty(msg)) {
            Log.i(tag, msg);
        }
    }


    public static void d(String tag, String msg) {
        if (isDebug && !TextUtils.isEmpty(msg)) {
            Log.d(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (isDebug && !TextUtils.isEmpty(msg)) {
            Log.w(tag, msg);
        }
    }


    public static void e(String tag, String msg) {
        if (isDebug && !TextUtils.isEmpty(msg)) {
            Log.e(tag, msg);
        }
    }


    public static void i(String msg) {
        i(TAG, msg);
    }

    public static void w(String msg) {
        w(TAG, msg);
    }

    public static void d(String msg) {
        d(TAG, msg);
    }

    public static void e(String msg) {
        e(TAG, msg);
    }


}
