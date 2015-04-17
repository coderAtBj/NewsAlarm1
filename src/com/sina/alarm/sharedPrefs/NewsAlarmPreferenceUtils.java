package com.sina.alarm.sharedPrefs;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * NewsAlarmPreferenceUtils: 提供通用的shared prefs相关的读写操作。
 * 
 * @author dianhui
 * */
public final class NewsAlarmPreferenceUtils {
    public static SharedPreferences getSharedPref(Context ctx, String prefName) {
        return ctx.getSharedPreferences(prefName, Context.MODE_PRIVATE);
    }

    public static void putString(Context ctx, String prefName, String key, String value) {
        SharedPreferences prefs = getSharedPref(ctx, prefName);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getString(Context ctx, String prefName, String key, String defaultValue) {
        SharedPreferences prefs = getSharedPref(ctx, prefName);
        return prefs.getString(key, defaultValue);
    }

    public static void putBoolean(Context ctx, String prefName, String key, boolean value) {
        SharedPreferences prefs = getSharedPref(ctx, prefName);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static boolean getBoolean(Context ctx, String prefName, String key, boolean defaultValue) {
        SharedPreferences prefs = getSharedPref(ctx, prefName);
        return prefs.getBoolean(key, defaultValue);
    }

    public static void putInt(Context ctx, String prefName, String key, int value) {
        SharedPreferences prefs = getSharedPref(ctx, prefName);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static int getInt(Context ctx, String prefName, String key, int defaultValue) {
        SharedPreferences prefs = getSharedPref(ctx, prefName);
        return prefs.getInt(key, defaultValue);
    }

    public static void putLong(Context ctx, String prefName, String key, long value) {
        SharedPreferences prefs = getSharedPref(ctx, prefName);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    public static long getLong(Context ctx, String prefName, String key, long defaultValue) {
        SharedPreferences prefs = getSharedPref(ctx, prefName);
        return prefs.getLong(key, defaultValue);
    }
}
