package com.lawer.mrlawer.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by yifan on 15-11-8.
 */
public class SysUtil {

    private static final String PREF_NAME = "pref_com_lawer_mrlawer";

    /**
     * 存储int到sp中。
     *
     * @param context 上下文，最好是applicationcontext。
     * @param key     需要存储的preferences key
     * @param value   待存储的值
     */
    public static void putInt(Context context, String key, int value) {
        SharedPreferences.Editor editor = context
                .getSharedPreferences(PREF_NAME, 0).edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static void putString(Context context, String key, String value) {
        SharedPreferences.Editor editor = context
                .getSharedPreferences(PREF_NAME, 0).edit();
        editor.putString(key, value);
        editor.apply();
    }

    /**
     * 获取sp里存储的int。
     *
     * @param context      上下文，最好是applicationcontext。
     * @param key          需要查找的preferences key。
     * @param defaultValue key不存在时返回的默认值.
     * @return key对应的int。
     */
    public static int getInt(Context context, String key, int defaultValue) {
        SharedPreferences sp = context.getSharedPreferences(PREF_NAME, 0);
        return sp.getInt(key, defaultValue);
    }

    public static String getString(Context context, String key, String defaultValue) {
        SharedPreferences sp = context.getSharedPreferences(PREF_NAME, 0);
        return sp.getString(key, defaultValue);
    }
}
