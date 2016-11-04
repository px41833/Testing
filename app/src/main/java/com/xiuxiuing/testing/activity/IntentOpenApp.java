package com.xiuxiuing.testing.activity;

import com.xiuxiuing.testing.R;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.TelephonyManager;

/**
 * Created by wang on 16/5/6.
 */
public class IntentOpenApp extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startWeiboGameDetailApp(this);

        String mPhone = ((TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE)).getLine1Number();
        System.out.println("mPhone:" + mPhone);

        // goToSamsungappsMarket(this, "com.UCMobile");
        // goToMarket(this, "com.UCMobile");
        // goToLeTVStore(this, "com.UCMobile");
        // goToLeTVStoreDetail(this, "com.sina.weibo");
        // goToMarket.setClassName("com.tencent.android.qqdownloader",
        // "com.tencent.pangu.link.LinkProxyActivity");
    }


    public static void goToMarket(Context context, String packageName) {
        Uri uri = Uri.parse("market://details?id=" + packageName);
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            goToMarket.setClassName("com.tencent.android.qqdownloader", "com.tencent.pangu.link.LinkProxyActivity");
            context.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void goToSamsungappsMarket(Context context, String packageName) {
        Uri uri = Uri.parse("http://www.samsungapps.com/appquery/appDetail.as?appId=" + packageName);
        Intent goToMarket = new Intent();
        goToMarket.setClassName("com.sec.android.app.samsungapps", "com.sec.android.app.samsungapps.Main");
        goToMarket.setData(uri);
        try {
            context.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    void goToLeTVStore(Context context, String packageName) {
        Uri uri = Uri.parse("http://base.mapi.letvstore.com/mapi/app/share?packagename=" + packageName);
        Intent intent = new Intent();
        intent.setClassName("com.letv.app.appstore", "com.letv.app.appstore.appmodule.search.SearchActivity");
        intent.setAction("com.letv.mobile.appstore.search");
        intent.setData(uri);
        context.startActivity(intent);
    }

    void startWeiboGameDetailApp(Context context) {
        Uri uri = Uri.parse("sinaweibo://snggamedetail/1631951503?aid=503999&acid=0&auto=0");
        Intent intent = new Intent();
        intent.setClassName("com.sina.weibo", "com.sina.weibo.appmarket.sng.activity.SngGameDetailActivity");
        intent.setData(uri);
        System.out.println(intent.toURI().toString());
        context.startActivity(intent);
    }

    void goToLeTVStoreDetail(Context context, String packageName) {
        // Uri uri = Uri.parse("http://base.mapi.letvstore.com/mapi/app/share?packagename=" +
        // packageName);
        // Uri uri = Uri.parse("http://base.mapi.letvstore.com/mapi/app/detail?packagename=" +
        // packageName);
        Intent intent = new Intent();
        intent.setClassName("com.letv.app.appstore", "com.letv.app.appstore.appmodule.details.DetailsActivity");
        intent.setAction("com.letv.app.appstore.appdetailactivity");
        intent.putExtra("packageName", packageName);
        System.out.println(intent.toURI().toString());
        // intent.setData(uri);
        context.startActivity(intent);
    }

    protected void openBrower(String url) {

        Uri uri = Uri.parse(url);

        Intent intent = new Intent(Intent.ACTION_VIEW, uri);

        startActivity(intent);

    }
}
