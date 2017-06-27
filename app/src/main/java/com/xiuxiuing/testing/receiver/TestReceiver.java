package com.xiuxiuing.testing.receiver;

import com.socks.library.KLog;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by wang on 17/5/10.
 */

public class TestReceiver extends BroadcastReceiver {
    private static final String TAG = "TestReceiver";

    @Override
    public void onReceive(final Context context, Intent intent) {
        KLog.d(TAG);
        // new Handler().postDelayed(new Runnable() {
        // @Override
        // public void run() {
        //// gotoDouyu(context);
        // gotoJingDong(context);
        //// goToYingYongBaoMarket(context);
        //// startTmallShop(context);
        // }
        // }, 5000);

    }

    public static void gotoDouyu(Context context) {
        try {
            Uri uri = Uri.parse("tbopen://air.tv.douyu/index.html?room_id=228866&isVertical=0");
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // intent.setClassName("air.tv.douyu.android", "tv.douyu.view.activity.MainActivity");
            intent.setData(uri);
            System.out.println(intent.toURI().toString());
            context.startActivity(intent);
        } catch (Exception e) {

        }

    }

    void gotoJingDong(Context context) {
        try {
            Uri uri = Uri.parse(
                    "openApp.jdMobile://virtual?params={\"category\":\"jump\",\"des\":\"m\",\"sourceValue\":\"sourceValue_gtDQ\",\"sourceType\":\"sourceType_gtDQ\",\"url\":\"https://item.m.jd.com/product/3772942.html\"}");

            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            System.out.println(intent.toURI().toString());


            context.startActivity(intent);
        } catch (Exception e) {

        }

    }

    public void startTmallShop(Context context) {

        try {
            // Uri uri = Uri.parse("baidumap://map/cost_share?url=http://www.baidu.com");
            Uri uri = Uri.parse("tmall://page.tm/webview?url=https://equity-vip.tmall.com/agent/mobile.htm?agentId=35927&_bind=true");
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // intent.setClassName("com.baidu.appsearch", "com.baidu.appsearch.UrlHandlerActivity");
            intent.setData(uri);
            KLog.e("START u0:" + intent.toURI().toString());
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void goToYingYongBaoMarket(Context context) {

        try {
            Intent uri = Intent.parseUri(
                    "tpmast://appdetails?pname=com.huajiao&versioncode=0&oplist=0&request_type=0#Intent;launchFlags=0x10000000;component=com.tencent.android.qqdownloader/com.tencent.pangu.link.LinkProxyActivity;end",
                    0);

            context.startActivity(uri);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
