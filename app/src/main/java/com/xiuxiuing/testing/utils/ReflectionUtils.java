package com.xiuxiuing.testing.utils;

import java.lang.reflect.Method;

/**
 * Created by wang on 2017/11/8.
 */

public class ReflectionUtils {

    public static Object invokeMethod(Object obj, String str, Object[] objArr, Class[] clsArr) {
        try {
            Class cls = Class.forName(obj.getClass().getName());
            if (objArr == null || clsArr == null) {
                return cls.getMethod(str, new Class[0]).invoke(obj, new Object[0]);
            }
            return cls.getMethod(str, clsArr).invoke(obj, objArr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
