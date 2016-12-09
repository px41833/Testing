package com.xiuxiuing.testing.activity;

import android.Manifest;
import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.xiuxiuing.testing.R;
import com.xiuxiuing.testing.adapter.MainRecyclerAdapter;

import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class MainActivity extends AppCompatActivity {
    TextView hello = null;
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {

            int a = getWifiLevel(MainActivity.this);
            String b = getWifiBssid(MainActivity.this);

            String c = getWifiSsid(MainActivity.this);

            Log.d("wifi", b + " " + c + " " + a);
            Log.d("enable", String.valueOf(isWiFiActive(MainActivity.this)));
            hello.setText(getLocalIpAddress());
            // 要做的事情
            super.handleMessage(msg);
        }
    };

    private DrawerLayout mDrawerLayout;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar bar = getSupportActionBar();
        bar.setHomeAsUpIndicator(R.drawable.ic_menu);
        bar.setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.main_drawer);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
        mRecyclerView.setAdapter(new MainRecyclerAdapter());


        NavigationView navigationView = (NavigationView) findViewById(R.id.main_navigation);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        // hello = (TextView) findViewById(R.id.hello);
        //
        // // new Thread(new MyThread()).start();
        //
        // int a = getWifiLevel(this);
        // String b = getWifiBssid(this);
        //
        // String c = getWifiSsid(this);
        // hello.setText(getWifiLevel(this) + " " + getWifiBssid(this) + " " + getWifiSsid(this));
        // hello.setText(getWifiList(this));
        // if (isWifiApEnabled(this)) {
        // WifiConfiguration wifiApConfiguration = getWifiApConfiguration(this);
        //
        // hello.setText(wifiApConfiguration.SSID + "" + wifiApConfiguration.BSSID + " " +
        // getLocalMac());
        //
        // }

        // hello.setText(getLocalIpAddress());
        // Log.e("", getWifiBssid(this));
        // hello.setText(getWifiBssid(this));

        // try {
        // Class<?> classType = Class.forName("android.os.SystemProperties");
        // Method[] method = classType.getDeclaredMethods();
        //
        // for (int i = 0; i < method.length; i++) {
        // Log.i("test", method[i].getName());
        // }
        // //
        // Method getMethod = classType.getDeclaredMethod("get", new Class<?>[] {String.class});
        // String value = (String) getMethod.invoke(classType, new Object[] {"YOUKEY"});
        // Log.i("test", value);
        //
        //
        // } catch (Exception e) {
        // Log.e("test", e.getMessage(), e);
        //
        // }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_overaction, menu);
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
        switch (id) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
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
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();

        return wifiInfo.getRssi();
    }

    public static String getWifiSsid(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
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
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

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

    public boolean isWiFiActive(Context inContext) {
        try {
            WifiManager mWifiManager = (WifiManager) inContext.getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = mWifiManager.getConnectionInfo();
            int ipAddress = wifiInfo == null ? 0 : wifiInfo.getIpAddress();
            if (mWifiManager.isWifiEnabled() && ipAddress != 0) {
                return true;
            }
        } catch (Exception e) {
            Log.d("isWiFiActive", e.toString());
        }
        return false;
    }



    public class MyThread implements Runnable {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            while (true) {
                try {
                    Thread.sleep(100);// 线程暂停0.1秒，单位毫秒
                    Message message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);// 发送消息
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    public String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        System.out.println(inetAddress.getHostAddress().toString());
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
        }
        return null;
    }
}
