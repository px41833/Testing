package com.xiuxiuing.testing.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.socks.library.KLog;
import com.xiuxiuing.testing.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wang on 16/8/9.
 */
public class PhoneInfoActivity extends BaseActivity {
    TextView tvPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phoneinfo);
        tvPhone = (TextView) findViewById(R.id.phoneifno);
        tvPhone.setText(getBuild() + getDeviceInfo());

        if (Build.VERSION.SDK_INT >= 21) {
            final ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkRequest.Builder builder = new NetworkRequest.Builder();

            // 设置指定的网络传输类型(蜂窝传输) 等于手机网络
            builder.addTransportType(NetworkCapabilities.TRANSPORT_WIFI);

            // 设置感兴趣的网络功能
            // builder.addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);

            // 设置感兴趣的网络：计费网络
            // builder.addCapability(NetworkCapabilities.NET_CAPABILITY_NOT_METERED);

            NetworkRequest request = builder.build();
            ConnectivityManager.NetworkCallback callback = new ConnectivityManager.NetworkCallback() {
                /**
                 * Called when the framework connects and has declared a new network ready for use.
                 * This callback may be called more than once if the {@link Network} that is
                 * satisfying the request changes.
                 */
                @TargetApi(Build.VERSION_CODES.M)
                @Override
                public void onAvailable(Network network) {
                    super.onAvailable(network);
                    Log.i("test", "已根据功能和传输类型找到合适的网络" + network.toString());

                    NetworkInfo networkInfo = connectivityManager.getNetworkInfo(network);
                    if (networkInfo.getState() != null && NetworkInfo.State.CONNECTED == networkInfo.getState()) {
                        KLog.d("手机网络连接成功！");
                    }
                    // 只要一找到符合条件的网络就注销本callback
                    // 你也可以自己进行定义注销的条件
                    connectivityManager.unregisterNetworkCallback(this);

                    // 通过network.openConnection 来获取URLConnection
                    try {
                        HttpURLConnection urlConnection = (HttpURLConnection) network.openConnection(new URL("http://www.baidu.com/s?wd=123"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    // 或者 raw Socket
                    // network.bindSocket(...);
                }
            };
            connectivityManager.registerNetworkCallback(request, callback);
            connectivityManager.requestNetwork(request, callback);
        }

    }

    private String getBuild() {
        StringBuilder sb = new StringBuilder();
        // sb.append("Model:" + Build.MODEL + "\n");
        // sb.append("Brand:" + Build.BRAND + "\n");
        sb.append("\u3000\u3000RomVersion:  " + getRomVersion() + "\n");

        return sb.toString();
    }

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
}
