package com.xiuxiuing.testing.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.jaredrummler.android.processes.AndroidProcesses;
import com.jaredrummler.android.processes.models.AndroidAppProcess;
import com.jaredrummler.android.processes.models.AndroidProcess;
import com.socks.library.KLog;
import com.xiuxiuing.testing.utils.ProcessesUtils;

import java.util.List;

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

            List<AndroidAppProcess> lists = AndroidProcesses.getRunningForegroundApps(getApplicationContext());
            List<AndroidProcess> lists1 = AndroidProcesses.getRunningProcesses();
            System.out.println();
            handler.postDelayed(this, 1 * 1000);
        }
    };
}
