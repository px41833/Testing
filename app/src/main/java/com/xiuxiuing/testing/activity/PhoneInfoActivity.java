package com.xiuxiuing.testing.activity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.socks.library.KLog;
import com.xiuxiuing.testing.R;
import com.xiuxiuing.testing.utils.TelephonyUtils;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by wang on 16/8/9.
 */
public class PhoneInfoActivity extends BaseActivity {
    TextView tvPhone;

    public static String PREFERRED_APN_URI = "content://telephony/carriers/preferapn";

    @TargetApi(Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phoneinfo);
        tvPhone = (TextView) findViewById(R.id.phoneifno);
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        TelephonyUtils telephonyUtils = new TelephonyUtils(this);

        SubscriptionManager subManager = SubscriptionManager.from(this.getApplicationContext());
        List list = subManager.getActiveSubscriptionInfoList();

        String imei1 = tm.getImei(0);
        String imei2 = tm.getImei(1);
        // String meid1 = tm.getMeid(0);
        // String meid2 = tm.getMeid(1);

        int simState1 = tm.getSimState(0);
        int simState2 = tm.getSimState(1);
        NetworkInfo networkInfo = ((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        String type = networkInfo.getSubtypeName();
        int nettype = networkInfo.getSubtype();
        KLog.d("type:" + type + " nettype:" + nettype);

        StringBuilder sb = new StringBuilder();
        StringBuilder sbString = new StringBuilder();

        sb.append("subid1:").append(telephonyUtils.getSubId1()).append("\n");
        sb.append("subid2:").append(telephonyUtils.getSubId2()).append("\n\n");

        sb.append("imei:  ").append(tm.getDeviceId()).append("\n");
        sb.append("imei1: ").append(telephonyUtils.getDeviced1()).append("\n");
        sb.append("imei2: ").append(telephonyUtils.getDeviced2()).append("\n\n");

        sbString.append(tm.getDeviceId()).append(" ").append(telephonyUtils.getDeviced1()).append(" ").append(telephonyUtils.getDeviced2())
                .append(" ");


        sb.append("imsi:  ").append(tm.getSubscriberId()).append("\n");
        sb.append("imsi1: ").append(telephonyUtils.getSubscriberId(telephonyUtils.getSubId1())).append("\n");
        sb.append("imsi2: ").append(telephonyUtils.getSubscriberId(telephonyUtils.getSubId2())).append("\n\n");

        sbString.append(tm.getSubscriberId()).append(" ").append(telephonyUtils.getSubscriberId(telephonyUtils.getSubId1())).append(" ")
                .append(telephonyUtils.getSubscriberId(telephonyUtils.getSubId2())).append(" ");

        sb.append("iccid: ").append(tm.getSimSerialNumber()).append("\n");
        sb.append("iccid1:").append(telephonyUtils.getSimSerialNumber(telephonyUtils.getSubId1())).append("\n");
        sb.append("iccid2:").append(telephonyUtils.getSimSerialNumber(telephonyUtils.getSubId2())).append("\n\n");

        sbString.append(tm.getSimSerialNumber()).append(" ").append(telephonyUtils.getSimSerialNumber(telephonyUtils.getSubId1())).append(" ")
                .append(telephonyUtils.getSimSerialNumber(telephonyUtils.getSubId2())).append("\n\n");

        sb.append("num:   ").append(tm.getLine1Number()).append("\n");
        sb.append("num1:  ").append(telephonyUtils.getLine1NumberForSubscriber(telephonyUtils.getSubId1())).append("\n");
        sb.append("num2:  ").append(telephonyUtils.getLine1NumberForSubscriber(telephonyUtils.getSubId2())).append("\n");

        sb.append(new String(Character.toChars(0x1F602)));

        tvPhone.setText(sb.toString());
        KLog.d(sb.toString());
        Log.d("tag", sb.toString());
        Log.d("tag", sbString.toString());

        KLog.d("MAC:" + getMacAddress());

        KLog.d("Build:" + getDeviceInfo());

    }

    private String getBuild() {
        StringBuilder sb = new StringBuilder();
        // sb.append("Model:" + Build.MODEL + "\n");
        // sb.append("Brand:" + Build.BRAND + "\n");
        sb.append("\u3000\u3000RomVersion:  " + getRomVersion() + "\n");

        return sb.toString();
    }

    public String getCurrentApnInUse(Context context) {
        Cursor cursor = context.getContentResolver().query(Uri.parse(PREFERRED_APN_URI), null, null, null, null);
        cursor.moveToFirst();
        String apn = "";
        if (cursor.isAfterLast()) {
            apn = cursor.getString(3);
            if (apn == null) {
                apn = "";
            }
        }
        return apn;
    }

    // 获取Mobile网络下的cmwap、cmnet
    // private int getCurrentApnInUse(Context context) {
    // int type = NONET;
    // Cursor cursor = context.getContentResolver().query(Uri.parse(PREFERRED_APN_URI), new String[]
    // {"_id", "apn", "type"}, null, null, null);
    // cursor.moveToFirst();
    // int counts = cursor.getCount();
    // if (counts != 0) {// 适配平板外挂3G模块情况
    // if (!cursor.isAfterLast()) {
    // String apn = cursor.getString(1);
    // // #777、ctnet 都是中国电信定制机接入点名称,中国电信的接入点：Net、Wap都采用Net即非代理方式联网即可
    // // internet 是模拟器上模拟接入点名称
    // if (apn.equalsIgnoreCase("cmnet") || apn.equalsIgnoreCase("3gnet") ||
    // apn.equalsIgnoreCase("uninet") || apn.equalsIgnoreCase("#777")
    // || apn.equalsIgnoreCase("ctnet") || apn.equalsIgnoreCase("internet")) {
    // type = WIFIAndCMNET;
    // } else if (apn.equalsIgnoreCase("cmwap") || apn.equalsIgnoreCase("3gwap") ||
    // apn.equalsIgnoreCase("uniwap")) {
    // type = CMWAP;
    // }
    // } else {
    // // 适配中国电信定制机,如海信EG968,上面方式获取的cursor为空，所以换种方式
    // Cursor c = context.getContentResolver().query(PREFERRED_APN_URI, null, null, null, null);
    // c.moveToFirst();
    // String user = c.getString(c.getColumnIndex("user"));
    // if (user.equalsIgnoreCase("ctnet")) {
    // type = WIFIAndCMNET;
    // }
    // c.close();
    // }
    // } else {
    // type = WIFIAndCMNET;// 平板外挂3G,采用非代理方式上网
    // }
    // cursor.close();
    // return type;
    // }

    /**
     * 获取指定字段信息
     *
     * @return
     */
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private String getDeviceInfo() {
        StringBuffer sb = new StringBuffer();
        sb.append("\u3000\u3000主板BOARD:  " + Build.BOARD);
        sb.append("\n\u3000\u3000系统启动程序版本号BOOTLOADER:  " + Build.BOOTLOADER);
        sb.append("\n\u3000\u3000系统定制商BRAND:  " + Build.BRAND);
        sb.append("\n\u3000\u3000cpu指令集CPU_ABI:  " + Build.CPU_ABI);
        sb.append("\n\u3000\u3000cpu指令集2CPU_ABI2:  " + Build.CPU_ABI2);
        sb.append("\n\u3000\u3000设置参数DEVICE:  " + Build.DEVICE);
        sb.append("\n\u3000\u3000显示屏参数DISPLAY:  " + Build.DISPLAY);
        sb.append("\n\u3000\u3000无线电固件版本getRadioVersion:  " + Build.getRadioVersion());
        sb.append("\n\u3000\u3000硬件识别码FINGERPRINT:  " + Build.FINGERPRINT);
        sb.append("\n\u3000\u3000硬件名称HARDWARE:  " + Build.HARDWARE);
        sb.append("\n\u3000\u3000HOST:  " + Build.HOST);
        sb.append("\n\u3000\u3000修订版本列表ID:  " + Build.ID);
        sb.append("\n\u3000\u3000硬件制造商MANUFACTURER:  " + Build.MANUFACTURER);
        sb.append("\n\u3000\u3000版本MODEL:  " + Build.MODEL);
        sb.append("\n\u3000\u3000硬件序列号SERIAL:  " + Build.SERIAL);
        sb.append("\n\u3000\u3000手机制造商PRODUCT:  " + Build.PRODUCT);
        sb.append("\n\u3000\u3000描述Build的标签TAGS:  " + Build.TAGS);
        sb.append("\n\u3000\u3000TIME:  " + Build.TIME);
        sb.append("\n\u3000\u3000builder类型TYPE:  " + Build.TYPE);
        sb.append("\n\u3000\u3000USER:  " + Build.USER);
        return sb.toString();
    }

    /**
     * 通过反射获取所有的字段信息
     *
     * @return
     */
    public String getDeviceInfo2() {
        StringBuilder sbBuilder = new StringBuilder();
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                sbBuilder.append("\n" + field.getName() + ":" + field.get(null).toString());
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return sbBuilder.toString();
    }

    public static String getRomVersion() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("huawei", "ro.build.version.emui");
        map.put("xiaomi", "ro.build.version.incremental");
        map.put("samsang", "ro.build.version.incremental");
        map.put("vivo", "ro.vivo.os.version");
        map.put("oppo", "ro.build.version.opporom");
        map.put("meizu", "ro.build.display.id");
        map.put("lenovo", "ro.build.version.incremental");
        map.put("smartisan", "ro.modversion");
        map.put("htc", "ro.build.sense.version");
        map.put("oneplus", "ro.rom.version");


        String fileName = "/system/build.prop";

        File file = new File(fileName);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {

                if (map.get(Build.BRAND.toLowerCase()) != null) {
                    if (tempString.contains(map.get(Build.BRAND.toLowerCase()))) {

                        System.out.println("getBuild:" + tempString.split("=")[1]);
                        System.out.println(tempString);
                        return tempString.split("=")[1];
                    }
                }

            }
            reader.close();
        } catch (Throwable e) {
            // #debug
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Throwable e) {
                    // #debug
                    e.printStackTrace();
                }
            }
        }
        return "";
    }

    public String getAllImei(Context context) {
        try {
            int simId_1 = 0;
            int simId_2 = 1;
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            Class<?> ct = Class.forName("android.telephony.TelephonyManager");
            Method md = ct.getMethod("getDeviceId", int.class);
            String imei_1 = (String) md.invoke(tm, simId_1);
            String imei_2 = (String) md.invoke(tm, simId_2);
            return imei_1 + "," + imei_2;
        } catch (Exception e) {

        }
        return "";
    }

    public void initQualcommDoubleSim() {
        try {
            TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            Class<?> cx = Class.forName("android.telephony.MSimTelephonyManager");

            Class<?> ct = Class.forName("android.telephony.TelephonyManager");

            Object obj = getSystemService("phone_msim");
            Integer simId_1 = 0;
            Integer simId_2 = 1;

            Method mx = cx.getMethod("getDataState");
            // int stateimei_1 = (Integer) mx.invoke(cx.newInstance());
            int stateimei_2 = tm.getDataState();
            Method mde = cx.getMethod("getDefault");
            Method md = ct.getMethod("getDeviceId", int.class);
            Method ms = cx.getMethod("getSubscriberId", int.class);
            Method mp = cx.getMethod("getPhoneType");

            // Object obj = mde.invoke(cx);

            String imei_1 = (String) md.invoke(tm, simId_1);
            KLog.d("imei_1:" + imei_1);
            String imei_2 = (String) md.invoke(tm, simId_2);
            KLog.d("imei_2:" + imei_2);

            String imsi_1 = (String) ms.invoke(obj, simId_1);
            String imsi_2 = (String) ms.invoke(obj, simId_2);

            int statephoneType_1 = tm.getDataState();
            int statephoneType_2 = (Integer) mx.invoke(obj);
            Log.e("tag", statephoneType_1 + "---" + statephoneType_2);

            // Class<?> msc = Class.forName("android.telephony.MSimSmsManager");
            // for (Method m : msc.getMethods()) {
            // if (m.getName().equals("sendTextMessage")) {
            // m.getParameterTypes();
            // }
            // Log.e("tag", m.getName());
            // }

        } catch (Exception e) {
            return;
        }
    }

    private void printAllMethods(Class cls, TelephonyUtils telephonyUtils) {
        Method[] method = cls.getDeclaredMethods();
        System.out.println("getDeclaredMethods():获取所有的权限修饰符修饰的Method");
        for (Method m : method) {
            System.out.println("Method Name = " + m.getName());
            Class<?>[] parameterTypes = m.getParameterTypes();
            if (parameterTypes.length >= 1) {
                try {
                    KLog.d("Method Name = " + m.getName());
                    KLog.d(m.invoke(telephonyUtils, telephonyUtils.getSubId1()) + "," + m.invoke(telephonyUtils, telephonyUtils.getSubId2()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            System.out.println("*****************************");
        }
        System.out.println();
    }


    void printAllFileds(Class cls) {
        Field[] field = cls.getDeclaredFields();
        System.out.println("getFields():获取所有权限修饰符修饰的字段");
        for (Field f : field) {
            System.out.println("Field Name = " + f.getName());
            System.out.println("Class Name = " + f.getType().getName());
        }
        System.out.println();
    }


    private void initMtkDoubleSim() {
        try {
            TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            Class<?> c = Class.forName("com.android.internal.telephony.Phone");
            // printAllFileds(c);
            // printAllMethods(c);
            //
            // printAllMethods(TelephonyManager.class);

            // Field fields1 = c.getField("GEMINI_SIM_1");
            // fields1.setAccessible(true);
            // Integer simId_1 = (Integer) fields1.get(null);
            // Field fields2 = c.getField("GEMINI_SIM_2");
            // fields2.setAccessible(true);
            // Integer simId_2 = (Integer) fields2.get(null);
            int simId_1 = 0;
            int simId_2 = 1;

            Method m1 = TelephonyManager.class.getDeclaredMethod("getDeviceId", int.class);
            String imei_1 = (String) m1.invoke(tm, simId_1);
            KLog.d("imei_1:" + imei_1);
            String imei_2 = (String) m1.invoke(tm, simId_2);
            KLog.d("imei_2:" + imei_2);


            Method m = TelephonyManager.class.getDeclaredMethod("getSubscriberId", int.class);
            String imsi_1 = (String) m.invoke(tm, simId_1);
            KLog.d("imsi_1:" + imsi_1);
            String imsi_2 = (String) m.invoke(tm, simId_2);
            KLog.d("imsi_2:" + imsi_2);


            Method mx = TelephonyManager.class.getDeclaredMethod("getPhoneTypeGemini", Integer.class);
            Integer phoneType_1 = (Integer) mx.invoke(tm, simId_1);
            Integer phoneType_2 = (Integer) mx.invoke(tm, simId_2);

            if (TextUtils.isEmpty(imsi_1) && (!TextUtils.isEmpty(imsi_2))) {
            }
            if (TextUtils.isEmpty(imsi_2) && (!TextUtils.isEmpty(imsi_1))) {
            }
        } catch (Exception e) {
            return;
        }
    }


    private void initMtkSecondDoubleSim() {
        try {
            Integer simId_1;
            Integer simId_2;
            TelephonyManager tm = null;
            try {
                tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                Class<?> c = Class.forName("com.android.internal.telephony.Phone");
                Field fields1 = c.getField("GEMINI_SIM_1");
                fields1.setAccessible(true);
                simId_1 = (Integer) fields1.get(null);
                Field fields2 = c.getField("GEMINI_SIM_2");
                fields2.setAccessible(true);
                simId_2 = (Integer) fields2.get(null);
            } catch (Exception e) {
                simId_1 = 0;
                simId_2 = 1;
            }

            Method mx = TelephonyManager.class.getMethod("getDefault", int.class);
            TelephonyManager tm1 = (TelephonyManager) mx.invoke(tm, simId_1);
            TelephonyManager tm2 = (TelephonyManager) mx.invoke(tm, simId_2);


            String imsi_1 = tm1.getSubscriberId();
            String imsi_2 = tm2.getSubscriberId();

            String imei_1 = tm1.getDeviceId();
            String imei_2 = tm2.getDeviceId();

            // phoneType_1 = tm1.getPhoneType();
            // phoneType_2 = tm2.getPhoneType();

            if (TextUtils.isEmpty(imsi_1) && (!TextUtils.isEmpty(imsi_2))) {
            }
            if (TextUtils.isEmpty(imsi_2) && (!TextUtils.isEmpty(imsi_1))) {
            }

        } catch (Exception e) {
            return;
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    public String getSimIccId(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) { // 大于等于Android 5.1.0 L版本
            SubscriptionManager sub = (SubscriptionManager) context.getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE);
            List<SubscriptionInfo> info = sub.getActiveSubscriptionInfoList();
            int count = sub.getActiveSubscriptionInfoCount();
            if (count > 0) {
                if (count > 1) {
                    String icc1 = info.get(0).getIccId();
                    String icc2 = info.get(1).getIccId();
                    return icc1 + "," + icc2;
                } else {
                    for (SubscriptionInfo list : info) {
                        String icc1 = list.getIccId();
                        KLog.d("list.getSubscriptionId():" + list.getSubscriptionId());
                        return icc1;
                    }
                }
            } else {
                return "";
            }
        } else {
            // 小于5.1.0 以下的版本
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            return tm.getSimSerialNumber();
        }
        return "";
    }

    public static Class[] getMethodParamTypes(Class cls, String methodName) throws ClassNotFoundException {
        Class[] params = null;
        try {
            Method[] methods = cls.getDeclaredMethods();
            for (int i = 0; i < methods.length; i++) {
                if (methodName.equals(methods[i].getName())) {// 和传入方法名匹配
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

    public static String getPhoneImsiNum(Context context) {
        int subId1 = -1;
        int subId2 = -1;
        String imsi1 = null;
        String imsi2 = null;
        try {
            TelephonyManager tManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Method getSubscriberId = null;
                Method getSimOperator = null;
                try {
                    getSubscriberId = tManager.getClass().getMethod("getSubscriberId", getMethodParamTypes(tManager.getClass(), "getSubscriberId"));
                } catch (Exception e) {

                }

                try {
                    getSimOperator = tManager.getClass().getMethod("getSimOperator", getMethodParamTypes(tManager.getClass(), "getSimOperator"));
                } catch (Exception e) {

                }

                subId1 = getSubId(0, context);
                subId2 = getSubId(1, context);

                if (subId1 > 0) {
                    imsi1 = (String) getSubscriberId.invoke(tManager, subId1);
                }
                if (subId2 > 0) {
                    imsi2 = (String) getSubscriberId.invoke(tManager, subId2);
                }

                if (!TextUtils.isEmpty(imsi1) && !TextUtils.isEmpty(imsi2)) {
                    return imsi1 + "," + imsi2;
                } else {
                    if (!TextUtils.isEmpty(imsi1)) {
                        return imsi1;
                    } else {
                        return imsi2;
                    }
                }
            } else {
                // Android 5.0以下的api获取ismi方法 sdk < 21
                return tManager.getSubscriberId();
            }
        } catch (Exception e) {
        }
        return "";
    }

    public static int getSubId(int simid, Context context) {
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

    public static String getMacAddress() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) {
                    continue;
                }

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:", b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
            KLog.d(ex.getLocalizedMessage());
        }
        return "02:00:00:00:00:00";
    }
}
