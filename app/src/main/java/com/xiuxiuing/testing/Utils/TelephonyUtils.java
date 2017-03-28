package com.xiuxiuing.testing.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.telephony.TelephonyManager;

import com.socks.library.KLog;

import java.lang.reflect.Method;

/**
 * Created by wang on 17/3/21.
 */

public class TelephonyUtils {
    Context mContext;
    private int subId1 = -1;
    private int subId2 = -1;

    public TelephonyUtils(Context context) {
        mContext = context.getApplicationContext();
        subId1 = getSubId(0, mContext);
        subId2 = getSubId(1, mContext);

    }

    private Class[] getMethodParamTypes(String methodName) throws ClassNotFoundException {
        Class[] params = null;
        try {
            Method[] methods = TelephonyManager.class.getDeclaredMethods();
            for (int i = 0; i < methods.length; i++) {
                if (methodName.equals(methods[i].getName())) {
                    params = methods[i].getParameterTypes();
                    if (params.length >= 1) {
                        KLog.d("length:" + params.length);
                        break;
                    }
                }
            }
        } catch (Exception e) {

        }
        return params;
    }

    public int getSubId1() {
        return subId1;
    }

    public int getSubId2() {
        return subId2;
    }

    public String getDeviced1() {
        return (String) getPhoneInfo(0, "getDeviceId");
    }

    public String getDeviced2() {
        return (String) getPhoneInfo(1, "getDeviceId");
    }

    public String getSubscriberId(int subId) {
        String imsi = (String) getPhoneInfo(subId, "getSubscriberId");
        return imsi;
    }

    public String getSimOperator(int subId) {
        String operator = (String) getPhoneInfo(subId, "getSimOperator");
        return operator;
    }

    public int getCurrentPhoneType(int subId) {
        int phoneType = (int) getPhoneInfo(subId, "getCurrentPhoneType");
        return phoneType;
    }

    public String getNetworkOperatorName(int subId) {
        String name = (String) getPhoneInfo(subId, "getNetworkOperatorName");
        return name;
    }

    public String getNetworkOperatorForSubscription(int subId) {
        String operator = (String) getPhoneInfo(subId, "getNetworkOperatorForSubscription");
        return operator;
    }

    public boolean isNetworkRoaming(int subId) {
        boolean isRoaming = (boolean) getPhoneInfo(subId, "isNetworkRoaming");
        return isRoaming;
    }

    public int getNetworkType(int subId) {
        int type = (int) getPhoneInfo(subId, "getNetworkType");
        return type;
    }

    public int getDataNetworkType(int subId) {
        int type = (int) getPhoneInfo(subId, "getDataNetworkType");
        return type;
    }

    public String getSimOperatorNumericForSubscription(int subId) {
        String numeric = (String) getPhoneInfo(subId, "getSimOperatorNumericForSubscription");
        return numeric;
    }

    public String getSimOperatorNameForSubscription(int subId) {
        String name = (String) getPhoneInfo(subId, "getSimOperatorNameForSubscription");
        return name;
    }

    public String getLine1NumberForSubscriber(int subId) {
        String name = (String) getPhoneInfo(subId, "getLine1NumberForSubscriber");
        return name;
    }

    public String getSimSerialNumber(int subId) {
        String iccid = (String) getPhoneInfo(subId, "getSimSerialNumber");
        return iccid;
    }

    private Object getPhoneInfo(int subId, String methodName) {
        Object value = null;
        try {
            TelephonyManager tm = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
            if (Build.VERSION.SDK_INT >= 21) {
                Method method = null;
                method = tm.getClass().getMethod(methodName, getMethodParamTypes(methodName));
                if (subId >= 0) {
                    value = method.invoke(tm, subId);
                }
            }

        } catch (Exception e) {

        }
        return value;
    }

    private int getSubId(int simid, Context context) {
        Uri uri = Uri.parse("content://telephony/siminfo");
        Cursor cursor = null;
        ContentResolver contentResolver = context.getContentResolver();
        try {
            cursor = contentResolver.query(uri, new String[] {"_id", "sim_id"}, "sim_id = ?", new String[] {String.valueOf(simid)}, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    return cursor.getInt(cursor.getColumnIndex("_id"));
                }
            }
        } catch (Exception e) {
        } finally {
            if (null != cursor) {
                cursor.close();
            }
        }
        return -1;
    }
}
