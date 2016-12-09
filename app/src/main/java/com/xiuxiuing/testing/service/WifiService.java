package com.xiuxiuing.testing.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.socks.library.KLog;

/**
 * Created by wang on 16/9/14.
 */
public class WifiService extends Service {
    private static long startTime;
    private static long endTime;
    WifiManager wm;
    WifiReceiver wifiReceiver;

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("service create");
        wm = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        wifiReceiver = new WifiReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.SUPPLICANT_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);

        filter.addAction(Intent.ACTION_BOOT_COMPLETED);
        filter.addAction(Intent.ACTION_SHUTDOWN);

        registerReceiver(wifiReceiver, filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        KLog.d("service onDestroy");
        getEndTime();
        unregisterReceiver(wifiReceiver);

    }

    private void getStartTime() {
        startTime = System.currentTimeMillis();
    }

    private void getEndTime() {
        endTime = System.currentTimeMillis();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    class WifiReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)) {
                System.out.println("size:" + wm.getScanResults().size());


            } else if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                try {
                    ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo.State wifiState = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
                    NetworkInfo.State mobileState = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();

                    if (wifiState != null && mobileState != null && NetworkInfo.State.CONNECTED != wifiState
                            && NetworkInfo.State.CONNECTED == mobileState) {
                        KLog.d("手机网络连接成功！");
                        getEndTime();
                    } else if (wifiState != null && mobileState != null && NetworkInfo.State.CONNECTED == wifiState
                            && NetworkInfo.State.CONNECTED != mobileState) {
                        KLog.d("无线网络连接成功！isCheckNetWork:");
                        getStartTime();

                    } else if (wifiState != null && mobileState != null && NetworkInfo.State.CONNECTED != wifiState
                            && NetworkInfo.State.CONNECTED != mobileState) {
                        KLog.d("手机没有任何网络...");
                        getEndTime();
                    }
                } catch (Throwable e) {
                    KLog.e(e);
                }
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
                    KLog.v(info.getSSID() + str);


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
                Log.d("WifiService", str);

            } else if (action.equals(Intent.ACTION_SHUTDOWN)) {
                KLog.d("接收关机广播");
                getEndTime();
            }
        }
    }
}
