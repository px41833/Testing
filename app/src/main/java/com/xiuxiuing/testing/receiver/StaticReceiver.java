package com.xiuxiuing.testing.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.TrafficStats;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.os.Parcelable;
import android.util.Log;

import com.socks.library.KLog;
import com.xiuxiuing.testing.data.Consts;
import com.xiuxiuing.testing.utils.PackageInfoUtils;
import com.xiuxiuing.testing.utils.SharePreUtils;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by wang on 16/6/14.
 */
public class StaticReceiver extends BroadcastReceiver {
    String TAG = "StaticReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals("android.intent.action.BOOT_COMPLETED")) {
            FileOutputStream fos;
            try {
                String path = Environment.getExternalStorageDirectory() + File.separator + "SysLog.txt";
                Log.d(TAG, "path:" + path);
                fos = new FileOutputStream(path, true);

                fos.write("系统开机\n".getBytes("utf-8"));
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.d(TAG, "开机广播接收");
        }
        if (action.equals("android.intent.action.ACTION_SHUTDOWN")) {

            FileOutputStream fos;
            try {
                String path = Environment.getExternalStorageDirectory() + File.separator + "SysLog.txt";
                Log.d(TAG, "path:" + path);
                fos = new FileOutputStream(path, true);
                fos.write("系统退出\n".getBytes("utf-8"));
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            Log.d(TAG, "关机广播接收");
            String totalRx = SharePreUtils.getInstance(context).getStringValue(Consts.UID_TOTAL_RX);
            String totalTx = SharePreUtils.getInstance(context).getStringValue(Consts.UID_TOTAL_TX);
            long rxByte = TrafficStats.getUidRxBytes(PackageInfoUtils.getUid(context, context.getPackageName()));
            long txByte = TrafficStats.getUidTxBytes(PackageInfoUtils.getUid(context, context.getPackageName()));


        }

        if (WifiManager.NETWORK_STATE_CHANGED_ACTION.equals(intent.getAction())) {
            Parcelable parcelableExtra = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
            if (null != parcelableExtra) {
                NetworkInfo networkInfo = (NetworkInfo) parcelableExtra;
                NetworkInfo.State state = networkInfo.getState();
                boolean isConnected = state == NetworkInfo.State.CONNECTED;// 当然，这边可以更精确的确定状态
                Log.d(this.getClass().getSimpleName(), "isConnected" + isConnected);
                if (isConnected) {
                    Log.d(TAG, "wifi连接");
                } else {
                    Log.d(TAG, "WiFi不可用");
                }
            }
        }

        if (action.equals(WifiManager.WIFI_STATE_CHANGED_ACTION)) {
            int state = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, 0);
            Log.d(TAG, "Wifi状态:" + state);
            switch (state) {
                case WifiManager.WIFI_STATE_ENABLED:
                    Log.d(TAG, "打开WiFi");
                    break;
                case WifiManager.WIFI_STATE_DISABLED:
                    Log.d(TAG, "关闭WiFi");
                    break;
            }
        }

        if (action.equals(Intent.ACTION_SCREEN_OFF)){
            KLog.d("hei ping le");
        }
    }
}
