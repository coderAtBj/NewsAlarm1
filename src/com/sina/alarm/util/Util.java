package com.sina.alarm.util;

import android.util.Log;

public class Util {
    public static final String LOG_TAG = "NewsAlarm";

    public static void logi(String msg) {
        Log.i(LOG_TAG, msg);
    }

    public static void logd(String msg) {
        Log.d(LOG_TAG, msg);
    }

    public static void logw(String msg) {
        Log.w(LOG_TAG, msg);
    }

    public static void loge(String msg) {
        Log.e(LOG_TAG, msg);
    }

    public static void logi(String msg, Throwable t) {
        Log.i(LOG_TAG, msg, t);
    }

    public static void logd(String msg, Throwable t) {
        Log.d(LOG_TAG, msg, t);
    }

    public static void logw(String msg, Throwable t) {
        Log.w(LOG_TAG, msg, t);
    }

    public static void loge(String msg, Throwable t) {
        Log.e(LOG_TAG, msg, t);
    }
}
