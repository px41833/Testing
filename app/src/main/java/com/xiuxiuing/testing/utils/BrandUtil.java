package com.xiuxiuing.testing.utils;

/**
 * Created by wang on 2017/11/7.
 */



import android.annotation.SuppressLint;
import android.os.Build;

@SuppressLint({"NewApi"})
/* compiled from: BrandUtil */
public class BrandUtil {

    /* compiled from: BrandUtil */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] ints = new int[a.values().length];

        static {
            try {
                ints[a.HUAWEI.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                ints[a.SAMSUNG.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    /* compiled from: BrandUtil */
    public enum a {
        UNKNOWN, SAMSUNG, HUAWEI
    }

    public static a a() {

        String str = Build.BRAND;
        if (str.equalsIgnoreCase("samsung")) {
            return a.SAMSUNG;
        }
        if (str.equalsIgnoreCase("Huawei")) {
            return a.HUAWEI;
        }
        return a.UNKNOWN;
    }

    public static int b() {
        return a(a());
    }

    public static int a(a aVar) {
        switch (AnonymousClass1.ints[aVar.ordinal()]) {
            case 1:
                return 0;
            case 2:
                return 1;
            default:
                return -1;
        }
    }
}
