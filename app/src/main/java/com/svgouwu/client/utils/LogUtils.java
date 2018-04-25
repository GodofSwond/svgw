package com.svgouwu.client.utils;

import android.util.Log;

import com.svgouwu.client.Constant;

/**
 * @author Melon
 */
public class LogUtils {
    public static final String TAG = "Log:";
    public static void v(String tag, String msg) {
        if (Constant.DEBUG) {
            Log.v(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (Constant.DEBUG) {
            Log.d(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (Constant.DEBUG) {
            Log.i(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (Constant.DEBUG) {
            Log.w(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (Constant.DEBUG) {
            Log.e(tag, msg);
        }
    }

    public static void v(String msg) {
        v(TAG, msg);
    }

    public static void d(String msg) {
        d(TAG, msg);
    }

    public static void i(String msg) {
        i(TAG, msg);
    }

    public static void w(String msg) {
        w(TAG, msg);
    }

    public static void e(String msg) {
        if (Constant.DEBUG) {
            e(TAG, msg);
        }
    }

}
