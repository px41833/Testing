package com.xiuxiuing.testing.activity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;

import com.xiuxiuing.testing.R;

import java.net.URISyntaxException;

/**
 * Created by wang on 16/5/6.
 */
public class IntentOpenApp extends BaseActivity implements View.OnClickListener {
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_intentapp);
        findViewById(R.id.btn_douyu).setOnClickListener(this);
        findViewById(R.id.btn_panda).setOnClickListener(this);
        findViewById(R.id.btn_huya).setOnClickListener(this);
        findViewById(R.id.btn_longzhu).setOnClickListener(this);
        findViewById(R.id.btn_quanmin).setOnClickListener(this);
        // startWeiboGameDetailApp(this);

        gotoJingDong();
        String mPhone = ((TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE)).getLine1Number();
        System.out.println("mPhone:" + mPhone);

        // goToSamsungappsMarket(this, "com.UCMobile");
        // goToMarket(this, "com.UCMobile");
        // goToLeTVStore(this, "com.UCMobile");
        // goToLeTVStoreDetail(this, "com.sina.weibo");
        // goToMarket.setClassName("com.tencent.android.qqdownloader",
        // "com.tencent.pangu.link.LinkProxyActivity");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_douyu:
                gotoDouyu(mContext);
                break;
            case R.id.btn_panda:
                gotoPanda(mContext);
                break;
            case R.id.btn_huya:
                gotoHuya(mContext);
                break;
            case R.id.btn_longzhu:
                gotoLongZhu(mContext);
                break;
            case R.id.btn_quanmin:
                gotoQuanMin(mContext);
                break;
            default:
                break;
        }

    }

    public static void gotoDouyu(Context context) {
        Uri uri = Uri.parse("tbopen://air.tv.douyu/index.html?room_id=228866&isVertical=0");
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setClassName("air.tv.douyu.android", "tv.douyu.view.activity.MainActivity");
        intent.setData(uri);
        System.out.println(intent.toURI().toString());
        context.startActivity(intent);
    }

    public static void gotoPanda(Context context) {
        Uri uri = Uri.parse("pandatv://openroom/373265");
        Intent intent = new Intent();
        intent.setClassName("com.panda.videoliveplatform", "com.panda.videoliveplatform.activity.SchemeActivity");
        intent.setData(uri);
        System.out.println(intent.toURI().toString());
        context.startActivity(intent);
    }

    public static void gotoHuya(Context context) {

        Intent intent = null;
        try {
            intent = Intent
                    .parseUri("#Intent;component=com.duowan.kiwi/com.duowan.kiwi.channelpage.ChannelPage;l.subSid=2546866994;l.sid=77259038;end", 0);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        System.out.println(intent.toURI().toString());
        context.startActivity(intent);

        // Uri uri = Uri.parse("kiwi://live?gameId=1&subSid=2535702558");
        // Intent intent = new Intent();
        // intent.setAction(Intent.ACTION_VIEW);
        // intent.setClassName("com.duowan.kiwi", "com.duowan.kiwi.channelpage.ChannelPage");
        // intent.setData(uri);
        // System.out.println(intent.toURI().toString());
        // context.startActivity(intent);
    }

    public static void gotoLongZhu(Context context) {


        // Intent intent = null;
        // try {
        // intent =
        // Intent.parseUri("#Intent;launchFlags=0x10000000;component=com.longzhu.tga/.clean.liveroom.LiveActivity;i.roomId=1246516;end",
        // 0);
        // } catch (URISyntaxException e) {
        // e.printStackTrace();
        // }
        // System.out.println(intent.toURI().toString());
        // context.startActivity(intent);


        Uri uri = Uri.parse("plulongzhulive://room/openwith?roomId=537278");
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setClassName("com.longzhu.tga", "com.longzhu.tga.clean.main.MainActivity");
        intent.setData(uri);
        System.out.println(intent.toURI().toString());
        context.startActivity(intent);

        // 打开视频
        // Intent intent = new Intent();
        // intent.putExtra("MEDIA_ID", "103323");
        // intent.setAction(Intent.ACTION_VIEW);
        // intent.setClassName("com.longzhu.tga", "com.longzhu.tga.activity.VideoActivity");
        // System.out.println(intent.toURI().toString());
        // context.startActivity(intent);

    }

    public static void gotoQuanMin(Context context) {

        Uri uri = Uri.parse("quanmin://mobile.app/liveroom?live=5191292");
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setClassName("com.maimiao.live.tv", "com.maimiao.live.tv.ui.activity.LiveTransitActivity");
        intent.setData(uri);
        System.out.println(intent.toURI().toString());
        context.startActivity(intent);

        // Intent intent = null;
        // try {
        // intent =
        // Intent.parseUri("#Intent;component=com.maimiao.live.tv/com.maimiao.live.tv.ui.live.HorLiveActivity;S.uid=326745;end",
        // 0);
        // } catch (URISyntaxException e) {
        // e.printStackTrace();
        // }
        // System.out.println(intent.toURI().toString());
        // context.startActivity(intent);

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

    void gotoJingDong() {

        Uri uri = Uri.parse(
                "openApp.jdMobile://virtual?params={\"category\":\"jump\",\"des\":\"m\",\"sourceValue\":\"sourceValue_gtDQ\",\"sourceType\":\"sourceType_gtDQ\",\"url\":\"http://h5.m.jd.com/active/3HsWjHqFvqwd9T5icf9ah7LyTSUf/index.html\"}");

        Intent intent = new Intent(Intent.ACTION_VIEW, uri);


        startActivity(intent);

    }


}
