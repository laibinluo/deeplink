package com.llb.deeplink.deeplink.util;

import android.util.Log;


/**
 * Created by szy on 2015/4/30.
 */
public class LogUtils {
    public static boolean write = false;
    public static boolean log = true;
//    public static boolean log = true;

    public static void d(String TAG, String msg) {
        if (log) {
            Log.d(TAG, msg);
        }
    }

    public static void e(String TAG, String msg) {
        if (log) {
            Log.e(TAG, msg);
        }
    }

    public static void i(String TAG, String msg) {
        if (log) {
            Log.i(TAG, msg);
        }
    }

    public static void w(String TAG, String msg) {
        if (log) {
            Log.w(TAG, msg);
        }
    }

    public static void v(String TAG, String msg) {
        if (log) {
            Log.v(TAG, msg);
        }
    }
}
