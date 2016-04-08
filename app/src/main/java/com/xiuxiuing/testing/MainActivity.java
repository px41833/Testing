package com.xiuxiuing.testing;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.lang.reflect.Method;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        TextView hello = (TextView) findViewById(R.id.hello);
        int a = getWifiLevel(this);
        String b = getWifiBssid(this);

        String c = getWifiSsid(this);
        // hello.setText(getWifiLevel(this) + " " + getWifiBssid(this) + " " + getWifiSsid(this));
        // hello.setText(getWifiList(this));
        // if (isWifiApEnabled(this)) {
        // WifiConfiguration wifiApConfiguration = getWifiApConfiguration(this);
        //
        // hello.setText(wifiApConfiguration.SSID + "" + wifiApConfiguration.BSSID + " " +
        // getLocalMac());
        //
        // }

        hello.setText(getWifiBssid(this));
        // Log.e("", getWifiBssid(this));
        // hello.setText(getWifiBssid(this));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public static WifiConfiguration getWifiApConfiguration(Context context) {
        WifiManager mWifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        try {
            Method method = mWifiManager.getClass().getMethod("getWifiApConfiguration");
            return (WifiConfiguration) method.invoke(mWifiManager);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean isWifiApEnabled(Context context) {
        WifiManager mWifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        try {
            Method method = mWifiManager.getClass().getMethod("isWifiApEnabled");
            return (Boolean) method.invoke(mWifiManager);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }



    public static int getWifiLevel(Context context) {
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();

        return wifiInfo.getRssi();
    }

    public static String getWifiSsid(Context context) {
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        String ssid = wifiInfo.getSSID();
        if (TextUtils.isEmpty(wifiInfo.getSSID())) {
            return "";
        } else {
            return wifiInfo.getSSID();
        }
    }

    public String getLocalMac() {
        String mac = "";
        // 获取wifi管理器
        WifiManager wifiMng = (WifiManager) getSystemService(Context.WIFI_SERVICE);

        WifiInfo wifiInfor = wifiMng.getConnectionInfo();
        wifiInfor.getSSID();
        mac = "本机的mac地址是：" + wifiInfor.getMacAddress();
        return mac;
    }


    public static String getWifiBssid(Context context) {
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        // if (!wifiManager.isWifiEnabled()) {
        // wifiManager.setWifiEnabled(true);
        // }

        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        String bssid = wifiInfo.getBSSID();
        if (TextUtils.isEmpty(bssid)) {
            return "";
        } else {
            return bssid.replace(":", "").toLowerCase();
        }
    }

    public String getWifiList(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (wifiManager.isWifiEnabled()) {
            List<ScanResult> list = wifiManager.getScanResults();
            if (list != null) {
                StringBuilder sb = new StringBuilder();
                for (ScanResult result : list) {
                    sb.append(result.SSID + " " + result.BSSID + " " + result.level + "\n");
                }
                return sb.toString();
            }
        }
        return "";
    }
}
