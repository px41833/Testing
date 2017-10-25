package com.xiuxiuing.testing.activity;

import com.socks.library.KLog;
import com.xiuxiuing.testing.R;
import com.xiuxiuing.testing.utils.ToastUtils;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

import dalvik.system.DexFile;

/**
 * Created by wang on 16/5/6.
 */
public class IntentOpenApp extends BaseActivity implements View.OnClickListener {
    Context mContext;
    EditText etUri;
    EditText etPkg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;
        setTitle(R.string.title_android_openappstore);
        setContentView(R.layout.activity_intentapp);
        etUri = (EditText) findViewById(R.id.et_uri);
        etPkg = (EditText) findViewById(R.id.et_pkg);
        etUri.setText("");
        findViewById(R.id.btn_start).setOnClickListener(this);


        String mPhone = ((TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE)).getLine1Number();
        System.out.println("mPhone:" + mPhone);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                startIntent();
                break;
           
            default:
                break;
        }

    }

    public void startIntent(String uriString, String pkgName, String className) {
        try {
            Uri uri = Uri.parse(uriString);
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            intent.setClassName(pkgName, className);
            intent.setData(uri);
            System.out.println(intent.toURI().toString());
            KLog.d("intent:" + intent.toURI().toString());
            mContext.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startIntent(String uriString) {
        try {
            Uri uri = Uri.parse(uriString);
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            intent.setData(uri);
            System.out.println("intent:" + intent.toURI().toString());
            KLog.d("intent:" + intent.toURI().toString());
            mContext.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }





































    public void startIntent() {
        try {

            Uri uri = Uri.parse(etUri.getText().toString());
            KLog.e("START u0:" + uri.getSchemeSpecificPart().substring(2));
            KLog.e("START u0 getPath:" + uri.getPath());
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            if (!TextUtils.isEmpty(etPkg.getText().toString())) {
                intent.setPackage(etPkg.getText().toString());
            }

            int a = Intent.FLAG_ACTIVITY_NEW_TASK & 0x100000;
            //
            // HashMap bundle = new HashMap();
            // bundle.put("enterMod", "hehe");
            // bundle.put("enterMod2", "hehe");
            // bundle.put("enterId", "4");
            //
            // intent.putExtra("extra.key.jump.data", bundle);

            // intent.setType("oppo/launch");
            //// intent.setAction("com.oppo.main.ACTION_LAUNCH");
            // intent.addCategory("android.intent.category.DEFAULT");
            // intent.setPackage("com.nearme.gamecenter");
            // intent.setClassName("com.nearme.gamecenter","com.oppo.cdo.ui.activity.WebBridgeActivity");


            intent.setData(uri);



            KLog.e("START u0:" + intent.toUri(Intent.FLAG_ACTIVITY_NEW_TASK).toString());
            KLog.e("START u0 get data:" + intent.getDataString());

            KLog.e("START u0 get Query:" + intent.toString());


            mContext.startActivity(intent);



        } catch (Exception e) {
            e.printStackTrace();
            ToastUtils.showToastLong(mContext, "检查应用是否安装");
        }

    }

}
