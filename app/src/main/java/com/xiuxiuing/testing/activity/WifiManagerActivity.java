package com.xiuxiuing.testing.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.TextView;

import com.xiuxiuing.testing.R;

import org.json.JSONObject;

import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by wang on 16/5/30.
 */
public class WifiManagerActivity extends BaseActivity {
    TextView tvHello;
    WifiManager wm;
    WifiReceiver wifiReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);

        JSONObject json = new JSONObject();
        try {
            json.put("imei", null); // 可选

        } catch (Throwable t) {
            t.printStackTrace();
        }
        System.out.println(json.toString());

        tvHello = (TextView) findViewById(R.id.hello);
        setTitle(R.string.title_wifi_manager);

        checkApp(this, "com.sina.weibo");
        // WifiPassword password = new WifiPassword();
        // StringBuilder sb = new StringBuilder();
        // try {
        // List<WifiPassword.WifiInfo> lists = password.Read();
        // for (WifiPassword.WifiInfo info : lists) {
        // sb.append(info.Ssid + " " + info.Password + " " + info.Keymgmt + "\n");
        // }
        // } catch (Exception e) {
        // e.printStackTrace();
        // }
        //
        // tvHello.setText(sb);
        getWifiList(this);
        System.out.println("Config: " + getConfiguration(this));

        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface iF = interfaces.nextElement();
                byte[] addr = new byte[0];
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.GINGERBREAD) {

                    addr = iF.getHardwareAddress();
                    if (addr == null || addr.length == 0) {
                        continue;
                    }
                    StringBuilder buf = new StringBuilder();
                    for (byte b : addr) {
                        buf.append(String.format("%02X:", b));
                    }
                    if (buf.length() > 0) {
                        buf.deleteCharAt(buf.length() - 1);
                    }
                    String mac = buf.toString();
                    Log.d("mac", "interfaceName=" + iF.getName() + ", mac=" + mac);

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        tvHello.setText(getLocalMac(this));

        wm = (WifiManager) getSystemService(WIFI_SERVICE);
        wifiReceiver = new WifiReceiver();
        IntentFilter filter = new IntentFilter(WifiManager.SUPPLICANT_STATE_CHANGED_ACTION);
        registerReceiver(wifiReceiver, filter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(wifiReceiver);
    }

    public static String getImei(Context context) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
            return telephonyManager.getDeviceId();
        } catch (Throwable t) {
            // LogPrinter.d(TAG, t.toString());
        }
        return null;
    }

    public String getWifiList(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(WIFI_SERVICE);
        // if (wifiManager.isWifiEnabled()) {
        // wifiManager.setWifiEnabled(true);
        List<ScanResult> list = wifiManager.getScanResults();
        if (list != null) {
            StringBuilder sb = new StringBuilder();
            for (ScanResult result : list) {
                sb.append(result.SSID + " " + result.BSSID + " " + result.level + "\n");
                System.out.println("wifilist " + result.SSID + " " + result.BSSID);
            }
            return sb.toString();
        }
        // }
        return "";
    }

    public String getLocalMac(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(WIFI_SERVICE);
        WifiInfo info = wifiManager.getConnectionInfo();
        return info.getMacAddress();
    }

    public String getConfiguration(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(WIFI_SERVICE);
        // if (wifiManager.isWifiEnabled()) {
        // wifiManager.setWifiEnabled(true);
        List<WifiConfiguration> list = wifiManager.getConfiguredNetworks();
        if (list != null) {
            StringBuilder sb = new StringBuilder();
            for (WifiConfiguration result : list) {
                sb.append(result.SSID + " " + result.BSSID + "\n");

                // System.out.println(result.SSID + " " + result.BSSID);
                System.out.println(result.networkId);
            }
            return sb.toString();
        }
        // }
        return "";
    }

    public boolean checkApp(Context context, String packagename) {
        PackageInfo packageInfo;

        try {
            packageInfo = context.getPackageManager().getPackageInfo(packagename, 0);

        } catch (Exception e) {
            packageInfo = null;
            e.printStackTrace();
        }
        if (packageInfo == null) {
            System.out.println("not installed");
        } else {
            System.out.println("is installed");
        }
        return false;
    }

    private void connectNetwork(WifiManager wm) {
        System.out.println("connectNetwork");
        WifiConfiguration wc = new WifiConfiguration();
        wc.SSID = "\"CYWiFi-B22A\"";
        wc.preSharedKey = "\"1234567890\"";

        wc.hiddenSSID = true;

        wc.status = WifiConfiguration.Status.ENABLED;

        wc.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);

        wc.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);

        wc.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);

        wc.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);

        wc.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);

        wc.allowedProtocols.set(WifiConfiguration.Protocol.RSN);

        int res = wm.addNetwork(wc);
        // Method connMethod = connectWifiByReflectMethod(res);
        // if (connMethod != null) {
        // boolean b = wm.enableNetwork(res, true);
        // wm.saveConfiguration();
        // wm.reconnect(); // 连接AP
        // }

        // boolean b = wm.enableNetwork(res, true);
        // wm.saveConfiguration();
        // wm.reconnect(); // 连接AP


    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    class WifiReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (WifiManager.SUPPLICANT_STATE_CHANGED_ACTION.equals(action)) {
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
                    System.out.println(str);

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

            }
        }
    }

}
