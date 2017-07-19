package com.xiuxiuing.testing.activity;

import com.socks.library.KLog;
import com.xiuxiuing.testing.BuildConfig;

import android.app.Application;


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
