package com.xiuxiuing.testing.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.xiuxiuing.testing.R;
import com.xiuxiuing.testing.listener.ClsaaSpotListener;
import com.xiuxiuing.testing.utils.PermissionHelper;

import net.youmi.android.normal.spot.SplashViewSettings;
import net.youmi.android.normal.spot.SpotManager;


/**
 * Created by wang on 16/11/28.
 */
public class SplashActivity extends Activity {
    private static final String TAG = "SplashActivity";
    private PermissionHelper mPermissionHelper;
    LinearLayout linearLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        linearLayout = (LinearLayout) findViewById(R.id.splash_ll);

        // 当系统为6.0以上时，需要申请权限
        mPermissionHelper = new PermissionHelper(this);
        mPermissionHelper.setOnApplyPermissionListener(new PermissionHelper.OnApplyPermissionListener() {
            @Override
            public void onAfterApplyAllPermission() {
                runApp();
            }
        });

        if (Build.VERSION.SDK_INT < 23) {
            runApp();
        } else {
            if (mPermissionHelper.isAllRequestedPermissionGranted()) {
                runApp();
            } else {
                mPermissionHelper.applyPermissions();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mPermissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPermissionHelper.onActivityResult(requestCode, resultCode, data);
    }

    private void runApp() {
        // 初始化SDK
        // AdManager.getInstance(this.getApplicationContext()).init("a3cd7b10a504bd7c",
        // "aafa9eabb1fefbfd", true, true);
        // 设置开屏
        SplashViewSettings splashViewSettings = new SplashViewSettings();
        splashViewSettings.setTargetClass(MainActivity.class);
        splashViewSettings.setSplashViewContainer(linearLayout);
        SpotManager.getInstance(this).showSplash(this, splashViewSettings, new ClsaaSpotListener());

    }

}
