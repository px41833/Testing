package com.xiuxiuing.testing.activity;

import java.lang.reflect.Method;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.xiuxiuing.testing.R;
import com.xiuxiuing.testing.service.WifiService;
import com.xiuxiuing.testing.utils.PasswordGetter;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by wang on 16/6/7.
 */
public class WiFiSdkActivity extends BaseActivity {

    private static final String token = "";
    private static final String uid = "";
    private static final String mobile = "";
    WifiManager wm;
    WifiReceiver wifiReceiver;
    PasswordGetter passwordGetter;


    ListView listView;
    MyAdapter adapter;

    List<ScanResult> listResults;
    String mSsid;
    String password;
    boolean cracking;

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifisdk);
        listView = (ListView) findViewById(R.id.lv_wifisdk);
        requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        wm = (WifiManager) getSystemService(WIFI_SERVICE);
        wm.startScan();
        System.out.println("init ");

        Intent intent = new Intent(this, WifiService.class);
        startService(intent);
        //
        // wifiReceiver = new WifiReceiver();
        //
        // ContentResolver resolver = getContentResolver();
//        try {
//
//            int value = Settings.System.getInt(resolver, Settings.System.WIFI_SLEEP_POLICY, Settings.System.WIFI_SLEEP_POLICY_DEFAULT);
//            int i = Settings.System.getInt(resolver, Settings.System.WIFI_WATCHDOG_BACKGROUND_CHECK_ENABLED, 0);
//            int q = Settings.System.getInt(resolver, Settings.System.WIFI_NETWORKS_AVAILABLE_NOTIFICATION_ON, 0);
//            int w = Settings.System.getInt(resolver, "wifi_scan_always_enabled", 0);
//
//
//            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
//            Settings.Global.putInt(resolver, "wifi_scan_always_enabled", 1);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        // IntentFilter filter = new IntentFilter();
        // filter.addAction(WifiManager.SUPPLICANT_STATE_CHANGED_ACTION);
        // filter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        // registerReceiver(wifiReceiver, filter);



        // int uid = PackageInfoUtils.getUid(this, this.getPackageName());
        // long reByteCount = TrafficStats.getUidRxBytes(uid);
        // long seByteCount = TrafficStats.getUidTxBytes(uid);
        // Log.d("", "接收包:" + reByteCount);
        // Log.d("", "发送包:" + seByteCount);
        // // if (!wm.isWifiEnabled()) {
        // // wm.setWifiEnabled(true);
        // // }
        //
//        listResults = wm.getScanResults();
//        adapter = new MyAdapter(this, listResults);
//        listView.setAdapter(adapter);
        //
        // try {
        // passwordGetter = new PasswordGetter(this.getAssets().open("password.txt"));
        // } catch (Exception e) {
        // e.printStackTrace();
        // System.out.println("password.txt文件异常");
        // }
        //
        // connectNetwork(wm, null, null);
        //
        // listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        // @Override
        // public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // mSsid = listResults.get(position).SSID;
        // showMessageDialog("wifi热点信息", mSsid, "破解", true, new DialogInterface.OnClickListener() {
        // @Override
        // public void onClick(DialogInterface dialog, int which) {
        // password = replaceBlank(passwordGetter.getPassword());
        // System.out.println("开始验证密码:" + password);
        // cracking = true;
        // connectNetwork(wm, mSsid, password);
        // }
        // });
        // }
        // });
    }

    @Override
    protected void onDestroy() {
        // unregisterReceiver(wifiReceiver);
        super.onDestroy();
    }

    private void deleteSavedConfigs(WifiManager wm) {
        System.out.println("deleteSavedConfigs");
        WifiConfiguration config;
        List<WifiConfiguration> configs = wm.getConfiguredNetworks();
        for (int i = 0; i < configs.size(); i++) {
            config = configs.get(i);
            config.priority = i + 2; // 将优先级排后
            wm.removeNetwork(config.networkId);
        }
        wm.saveConfiguration();
    }

    private void connectNetwork(WifiManager wm, String ssid, String password) {
        System.out.println("connectNetwork");
        WifiConfiguration wc = new WifiConfiguration();
        // wc.SSID = "\"CYWiFi-B22A\"";
        // wc.preSharedKey = "\"1234567890\"";

        ssid = "\"" + ssid + "\"";
        wc.SSID = ssid;
        System.out.println("ssid:" + ssid);
        wc.preSharedKey = password;
        wc.hiddenSSID = true;

        wc.status = WifiConfiguration.Status.ENABLED;

        wc.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);

        wc.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);

        wc.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);

        wc.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);

        wc.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);

        wc.allowedProtocols.set(WifiConfiguration.Protocol.RSN);

        int res = wm.addNetwork(wc);
        Method connMethod = connectWifiByReflectMethod(res);
        if (connMethod != null) {
            boolean b = wm.enableNetwork(res, false);
            wm.saveConfiguration();
            wm.reconnect(); // 连接AP
        }

        // boolean b = wm.enableNetwork(res, false);
        // wm.saveConfiguration();
        // wm.reconnect(); // 连接AP


    }


    private void setWifiNeverSleep() {
        int wifiSleepPolicy = 0;
        wifiSleepPolicy = Settings.System.getInt(getContentResolver(), android.provider.Settings.System.WIFI_SLEEP_POLICY,
                Settings.System.WIFI_SLEEP_POLICY_DEFAULT);
        System.out.println("---> 修改前的Wifi休眠策略值 WIFI_SLEEP_POLICY=" + wifiSleepPolicy);


        Settings.System.putInt(getContentResolver(), android.provider.Settings.System.WIFI_SLEEP_POLICY, Settings.System.WIFI_SLEEP_POLICY_NEVER);


        wifiSleepPolicy = Settings.System.getInt(getContentResolver(), android.provider.Settings.System.WIFI_SLEEP_POLICY,
                Settings.System.WIFI_SLEEP_POLICY_DEFAULT);
        System.out.println("---> 修改后的Wifi休眠策略值 WIFI_SLEEP_POLICY=" + wifiSleepPolicy);
    }

    public String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return "\"" + dest + "\"";
    }

    private void showMessageDialog(String title, String message, String positiveButtonText, boolean bShowCancel,
            DialogInterface.OnClickListener positiveButtonlistener) {
        AlertDialog.Builder builder = new Builder(WiFiSdkActivity.this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(positiveButtonText, positiveButtonlistener);
        if (bShowCancel) {
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {}
            });
        }
        builder.create().show();
    }

    // 通过反射出不同版本的connect方法来连接Wifi
    private Method connectWifiByReflectMethod(int netId) {
        Method connectMethod = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {

            Log.i("connectWifiByReflectMethod", "connectWifiByReflectMethod road 1");
            // 反射方法： connect(int, listener) , 4.2 <= phone's android version
            for (Method methodSub : wm.getClass().getDeclaredMethods()) {
                if ("connect".equalsIgnoreCase(methodSub.getName())) {
                    Class<?>[] types = methodSub.getParameterTypes();
                    if (types != null && types.length > 0) {
                        if ("int".equalsIgnoreCase(types[0].getName())) {
                            connectMethod = methodSub;
                            break;
                        }
                    }
                }
            }

            if (connectMethod != null) {
                try {
                    connectMethod.invoke(wm, netId, null);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i("", "connectWifiByReflectMethod Android " + Build.VERSION.SDK_INT + " error!");
                    return null;
                }
            }
        } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.JELLY_BEAN) {
            // 反射方法: connect(Channel c, int networkId, ActionListener listener)
            // mChannel = mWifiManager.initialize(getActivity(), getActivity().getMainLooper(),
            // null);
            // 暂时不处理4.1的情况 , 4.1 == phone's android version
            Log.i("", "connectWifiByReflectMethod road 2");
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH && Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            Log.i("", "connectWifiByReflectMethod road 3");
            // 反射方法：connectNetwork(int networkId) ,
            // 4.0 <= phone's android version < 4.1
            for (Method methodSub : wm.getClass().getDeclaredMethods()) {
                if ("connectNetwork".equalsIgnoreCase(methodSub.getName())) {
                    Class<?>[] types = methodSub.getParameterTypes();
                    if (types != null && types.length > 0) {
                        if ("int".equalsIgnoreCase(types[0].getName())) {
                            connectMethod = methodSub;
                        }
                    }
                }
            }

            if (connectMethod != null) {
                try {
                    connectMethod.invoke(wm, netId);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i("", "connectWifiByReflectMethod Android " + Build.VERSION.SDK_INT + " error!");
                    return null;
                }
            }
        } else {
            // < android 4.0
            return null;
        }
        return connectMethod;
    }

    class WifiReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)) {
                System.out.println("size:" + wm.getScanResults().size());

                listResults = wm.getScanResults();
                adapter = new MyAdapter(WiFiSdkActivity.this, listResults);
                listView.setAdapter(adapter);

            } else if (WifiManager.SUPPLICANT_STATE_CHANGED_ACTION.equals(action)) {
                WifiInfo info = wm.getConnectionInfo();
                SupplicantState state = info.getSupplicantState();
                String str = null;
                if (state == SupplicantState.ASSOCIATED) {
                    str = "关联AP完成";
                    System.out.println(str);
                } else if (state.toString()
                        .equals("AUTHENTICATING")/* SupplicantState.AUTHENTICATING */) {
                    str = "正在验证";
                    System.out.println(str);
                } else if (state == SupplicantState.ASSOCIATING) {
                    str = "正在关联AP...";
                    System.out.println(str);
                } else if (state == SupplicantState.COMPLETED) {
                    str = "已连接";
                    System.out.println(str + " " + password);
                    if (cracking) {
                        cracking = false;
                        showMessageDialog("恭喜您,密码跑出来了", "密码为:" + password, "确定", false, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {}
                        });
                    }
                    return;

                } else if (state == SupplicantState.DISCONNECTED) {
                    str = "已断开";
                    System.out.println(str);
                } else if (state == SupplicantState.DORMANT) {
                    str = "暂停活动";
                    System.out.println(str);
                } else if (state == SupplicantState.FOUR_WAY_HANDSHAKE) {
                    str = "四路握手中...";
                    System.out.println(str);
                } else if (state == SupplicantState.GROUP_HANDSHAKE) {
                    str = "GROUP_HANDSHAKE";
                    System.out.println(str);
                } else if (state == SupplicantState.INACTIVE) {
                    str = "休眠中...";
                    System.out.println(str);
                } else if (state == SupplicantState.INVALID) {
                    str = "无效";
                    System.out.println(str);
                } else if (state == SupplicantState.SCANNING) {
                    str = "扫描中...";
                    System.out.println(str);
                } else if (state == SupplicantState.UNINITIALIZED) {
                    str = "未初始化";
                    System.out.println(str);
                }
                if (cracking) {
                    int errorCode = intent.getIntExtra(WifiManager.EXTRA_SUPPLICANT_ERROR, -1);
                    if (errorCode == WifiManager.ERROR_AUTHENTICATING) {
                        System.out.println("wifi验证失败");

                        password = replaceBlank(passwordGetter.getPassword());
                        System.out.println("开始验证密码:" + password);
                        connectNetwork(wm, mSsid, password);
                    }


                }
            }
        }
    }

    static class ViewHolder {
        TextView title;
        TextView desc;
    }

    class MyAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        private List<ScanResult> mList;

        public MyAdapter(Context context, List<ScanResult> list) {
            this.mInflater = LayoutInflater.from(context);
            this.mList = list;

        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.recycle_main_item, null);
                holder.title = (TextView) convertView.findViewById(R.id.tv_main_title);
                holder.desc = (TextView) convertView.findViewById(R.id.tv_main_desc);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            ScanResult result = mList.get(position);
            holder.title.setText(result.SSID);
            holder.desc.setText(result.BSSID);

            return convertView;
        }
    }
}
