package com.xiuxiuing.testing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by wang on 16/8/9.
 */
public class PhoneInfoActivity extends BaseActivity {
    TextView tvPhone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phoneinfo);
        tvPhone = (TextView) findViewById(R.id.phoneifno);
        tvPhone.setText(getBuild());
        getRomVersion();

        try {
            Class<?> classType = Class.forName("android.os.SystemProperties");
            Method[] method = classType.getDeclaredMethods();
            Field[] fields = classType.getDeclaredFields();

            for (int i = 0; i < method.length; i++) {
                Log.i("test", method[i].getName());
            }

            for (int i = 0; i < fields.length; i++) {
                Log.i("fields", fields[i].getName());
            }

            // Method getMethod = classType.getDeclaredMethod("get", new Class<?>[]{String.class});
            // String value = (String) getMethod.invoke(classType, new Object[]{"YOUKEY"});
            // Log.i("test", value);


        } catch (Exception e) {
            Log.e("test", e.getMessage(), e);

        }

    }

    private String getBuild() {
        StringBuilder sb = new StringBuilder();
        sb.append("Model:" + Build.MODEL + "\n");
        sb.append("Brand:" + Build.BRAND + "\n");
        sb.append("RomVersion:" + getRomVersion() + "\n");

        return sb.toString();
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
}
