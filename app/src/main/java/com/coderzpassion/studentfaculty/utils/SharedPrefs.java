package com.coderzpassion.studentfaculty.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by coderzpassion on 26/10/16.
 */
public class SharedPrefs {

    public static String email="email";
    public static String type="type";
    public static String name="user";
    public static String roll="roll";
    public static String subject="subject";
    public static String department="department";
    public static String batch="batch";
    public static String contact = "contact";

    private static SharedPreferences mAppPreferences;
    private static SharedPreferences.Editor mEditor;

    private static void initPrefs(Context context) {
        if (mAppPreferences == null) {
            mAppPreferences = context.getSharedPreferences("prefs",Context.MODE_PRIVATE);
        }
    }

    public static void setStringData(Context context, String tag, String data) {
        initPrefs(context);
        mEditor = mAppPreferences.edit();
        mEditor.putString(tag, data);
        mEditor.commit();
    }

    public static void setIntData(Context context, String tag, int data) {
        initPrefs(context);
        mEditor = mAppPreferences.edit();
        mEditor.putInt(tag, data);
        mEditor.commit();
    }

    public static void setBooleanData(Context context, String tag, boolean data) {
        initPrefs(context);
        mEditor = mAppPreferences.edit();
        mEditor.putBoolean(tag, data);
        mEditor.commit();
    }

    public static void setLongData(Context context, String tag, Long data) {
        initPrefs(context);
        mEditor = mAppPreferences.edit();
        mEditor.putLong(tag, data);
        mEditor.commit();
    }

    public static String getString(Context context, String tag) {
        initPrefs(context);
        return mAppPreferences.getString(tag, null);
    }

    public static int getInt(Context context, String tag) {
        initPrefs(context);
        return mAppPreferences.getInt(tag, 0);
    }

    public static boolean getBoolean(Context context, String tag) {
        initPrefs(context);
        return mAppPreferences.getBoolean(tag, false);
    }

    public static long getLong(Context context, String tag) {
        initPrefs(context);
        return mAppPreferences.getLong(tag, 0L);
    }
    public static void clear(Context context) {
        initPrefs(context);
        mEditor = mAppPreferences.edit();
        mEditor.clear();
        mEditor.commit();
    }
}
