package com.xiuxiuing.testing.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.socks.library.KLog;
import com.xiuxiuing.testing.R;

import java.lang.reflect.Method;

/**
 * Created by wang on 2017/9/26.
 */

public class PermissionManagerActivity extends BaseActivity {

    public static final int OP_COARSE_LOCATION = 0;
    public static final int OP_FINE_LOCATION = 1;
    public static final int OP_GPS = 2;
    public static final int OP_WIFI_SCAN = 10;

    public static final int OP_READ_SMS = 14;
    public static final int OP_WRITE_SMS = 15;
    public static final int OP_RECEIVE_SMS = 16;
    public static final int OP_SEND_SMS = 20;
    public static final int OP_WRITE_SETTINGS = 23;
    public static final int OP_SYSTEM_ALERT_WINDOW = 24;

    Context mContext;
    private LinearLayout llParent;
    private TextView tvLog;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common);
        setTitle(R.string.title_android_permiss);
        mContext = this.getApplicationContext();
        llParent = (LinearLayout) findViewById(R.id.ll_parent);
        tvLog = (TextView) findViewById(R.id.tv_log);

        addButton("权限检测", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkOp(mContext, OP_COARSE_LOCATION);

                checkOp(mContext, OP_WRITE_SETTINGS);
                checkOp(mContext, OP_READ_SMS);
                checkOp(mContext, OP_WRITE_SMS);
                checkOp(mContext, OP_RECEIVE_SMS);
                checkOp(mContext, OP_SYSTEM_ALERT_WINDOW);

                // setMode(mContext, OP_COARSE_LOCATION, 0);
            }
        });

        addButton("请求权限", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(PermissionManagerActivity.this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        });

        addButton("魅族权限设置", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Meizu(mContext);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    public void addButton(String text, View.OnClickListener listener) {
        Button btn = new Button(this);
        btn.setText(text);
        btn.setOnClickListener(listener);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        llParent.addView(btn, lp);
    }

    public boolean checkOp(Context context, int op) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            if (appOpsManager != null) {
                try {
                    Method checkOpMethod = AppOpsManager.class.getDeclaredMethod("checkOp", Integer.TYPE, Integer.TYPE, String.class);
                    int checkop = (Integer) checkOpMethod.invoke(appOpsManager, op, Binder.getCallingUid(), context.getPackageName());
                    KLog.d("checkop:" + checkop);
                    tvLog.setText(tvLog.getText() + "\n" + "checkop:" + checkop);
                    return (checkop == AppOpsManager.MODE_ALLOWED);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public void setMode(Context context, int op, int mode) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {


            try {
                Context mContext = context.createPackageContext("cn.nubia.security2", Context.CONTEXT_INCLUDE_CODE | Context.CONTEXT_IGNORE_SECURITY);
                AppOpsManager appOpsManager = (AppOpsManager) mContext.getSystemService(Context.APP_OPS_SERVICE);
                if (appOpsManager != null) {

                    Method checkOpMethod = AppOpsManager.class.getDeclaredMethod("setMode", Integer.TYPE, Integer.TYPE, String.class, Integer.TYPE);
                    checkOpMethod.invoke(appOpsManager, op, 8736, context.getPackageName(), mode);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static void Meizu(Context activity) {
        Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.putExtra("packageName", activity.getPackageName());
        activity.startActivity(intent);
    }
}
