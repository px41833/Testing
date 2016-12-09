package com.xiuxiuing.testing.activity;

import android.app.Application;

import com.socks.library.KLog;
import com.xiuxiuing.testing.BuildConfig;

/**
 * Created by wang on 16/12/8.
 */
public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        KLog.init(BuildConfig.LOG_DEBUG);
    }
}
