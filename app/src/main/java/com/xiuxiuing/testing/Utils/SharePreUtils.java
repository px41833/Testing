package com.xiuxiuing.testing.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by wang on 16/6/8.
 */
public class SharePreUtils {
    private static SharePreUtils sharePreUtils;
    private SharedPreferences preferences;

    private SharePreUtils(Context context) {
        preferences = context.getSharedPreferences("testing", Activity.MODE_PRIVATE);
    }

    public static SharePreUtils getInstance(Context context) {
        if (sharePreUtils == null) {
            sharePreUtils = new SharePreUtils(context);
        }
        return sharePreUtils;
    }

    public boolean editStringValue(String key, String value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    public String getStringValue(String key) {
        return preferences.getString(key, "");
    }
}
