package com.xiuxiuing.testing.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

/**
 * Created by wang on 16/6/8.
 */
public class PackageInfoUtils {

    public static int getUid(Context context, String pkg) {
        PackageManager pm = context.getPackageManager();
        try {
            ApplicationInfo ai = pm.getApplicationInfo(pkg, PackageManager.GET_ACTIVITIES);
            return ai.uid;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
