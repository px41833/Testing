package com.xiuxiuing.testing;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by wang on 16/5/12.
 */
public class ScreenBroadcastActivity extends Activity {
    ScreenBroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_USER_PRESENT);

        broadcastReceiver = new ScreenBroadcastReceiver();
        registerReceiver(broadcastReceiver, filter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

    private class ScreenBroadcastReceiver extends BroadcastReceiver {
        private String action = null;

        @Override
        public void onReceive(Context context, Intent intent) {
            action = intent.getAction();
            if (Intent.ACTION_SCREEN_ON.equals(action)) { // 开屏
                Log.d("ScreenBroadcastReceiver", "ACTION_SCREEN_ON");
            } else if (Intent.ACTION_SCREEN_OFF.equals(action)) { // 锁屏
                Log.d("ScreenBroadcastReceiver", "ACTION_SCREEN_OFF");
            } else if (Intent.ACTION_USER_PRESENT.equals(action)) { // 解锁
                Log.d("ScreenBroadcastReceiver", "ACTION_USER_PRESENT");
            }
        }
    }
}
