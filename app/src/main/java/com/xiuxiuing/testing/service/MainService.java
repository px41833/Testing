package com.xiuxiuing.testing.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.socks.library.KLog;
import com.xiuxiuing.testing.utils.ProcessesUtils;

/**
 * Created by wang on 17/2/13.
 */
public class MainService extends Service {
    private Handler handler = new Handler();

    @Override
    public void onCreate() {
        super.onCreate();
        KLog.d("onCreate");
        handler.postDelayed(task, 3 * 1000);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private Runnable task = new Runnable() {
        @Override
        public void run() {
            KLog.d("foreApp:" + ProcessesUtils.getForegroundApp());
            System.out.println("foreApp:" + ProcessesUtils.getForegroundApp());
            handler.postDelayed(this, 1 * 1000);
        }
    };
}
