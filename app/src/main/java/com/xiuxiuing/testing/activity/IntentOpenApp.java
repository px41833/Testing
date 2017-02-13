package com.xiuxiuing.testing.activity;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
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
        findViewById(R.id.btn_yingyongbao).setOnClickListener(this);
        findViewById(R.id.btn_360zhushou).setOnClickListener(this);
        findViewById(R.id.btn_sinagame).setOnClickListener(this);
        findViewById(R.id.btn_getui).setOnClickListener(this);
        findViewById(R.id.btn_jingdong).setOnClickListener(this);
        findViewById(R.id.btn_ifeng).setOnClickListener(this);
        findViewById(R.id.btn_damai).setOnClickListener(this);
        findViewById(R.id.btn_qqlive).setOnClickListener(this);
        findViewById(R.id.btn_youku).setOnClickListener(this);
        findViewById(R.id.btn_dianping).setOnClickListener(this);
        findViewById(R.id.btn_tudou).setOnClickListener(this);
        findViewById(R.id.btn_souhu).setOnClickListener(this);
        findViewById(R.id.btn_aiqiyi).setOnClickListener(this);
        findViewById(R.id.btn_tuniu).setOnClickListener(this);
        findViewById(R.id.btn_baidu).setOnClickListener(this);
        findViewById(R.id.btn_leshi).setOnClickListener(this);
        findViewById(R.id.btn_qunar).setOnClickListener(this);
        findViewById(R.id.btn_xiecheng).setOnClickListener(this);
        findViewById(R.id.btn_chunqiu).setOnClickListener(this);
        findViewById(R.id.btn_momo).setOnClickListener(this);
        findViewById(R.id.btn_huajiao).setOnClickListener(this);

        String mPhone = ((TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE)).getLine1Number();
        System.out.println("mPhone:" + mPhone);

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
            case R.id.btn_yingyongbao:
                goToYingYongBaoMarket(mContext, "com.UCMobile");
                break;
            case R.id.btn_360zhushou:
                goTo360ZhuShouMarket(mContext, "com.UCMobile");
                break;
            case R.id.btn_sinagame:
                startWeiboGameDetailApp(mContext);
                break;
            case R.id.btn_getui:
                startPushService(mContext);
                break;
            case R.id.btn_jingdong:
                gotoJingDong();
                break;
            case R.id.btn_ifeng:
                gotoIFeng();
                break;
            case R.id.btn_damai:
                gotoDamai();
                break;
            case R.id.btn_qqlive:
                gotoQQLive();
                break;
            case R.id.btn_youku:
                gotoYouku();
                break;
            case R.id.btn_dianping:
                gotoDianping();
                break;
            case R.id.btn_tudou:
                gotoTuDou();
                break;
            case R.id.btn_souhu:
                gotoSouhu();
                break;
            case R.id.btn_aiqiyi:
                gotoIqiyi();
                break;
            case R.id.btn_tuniu:
                gotoTuniu();
                break;
            case R.id.btn_baidu:
                gotoBaidu();
                break;
            case R.id.btn_leshi:
                gotoLeshi();
                break;
            case R.id.btn_qunar:
                gotoQuna();
                break;
            case R.id.btn_xiecheng:
                gotoXieCheng();
                break;
            case R.id.btn_chunqiu:
                gotoChunqiu();
                break;
            case R.id.btn_momo:
                gotoMomo();
                break;
            case R.id.btn_huajiao:
                gotoHuajiao();
                break;
            default:
                break;
        }

    }

    public void startIntent(String uriString, String pkgName, String className) {
        Uri uri = Uri.parse(uriString);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setClassName(pkgName, className);
        intent.setData(uri);
        System.out.println(intent.toURI().toString());
        mContext.startActivity(intent);
    }

    public void startIntent(String uriString) {
        Uri uri = Uri.parse(uriString);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(uri);
        System.out.println("intent:" + intent.toURI().toString());
        mContext.startActivity(intent);
    }

    public void gotoHuajiao() {
        startIntent("huajiao://huajiao.com/goto/live?liveid=79619083");
    }

    public void gotoMomo() {
        startIntent("momochat://immomo.com?goto=[点击观看|goto_plive_profile|1457967628432|share_page]");
    }

    public void gotoChunqiu() {
        // cq://m.springtour.com/vacation/82694
        startIntent("cq://m.springtour.com/tour/22087");
    }

    public void gotoXieCheng() {
        // ctrip://wireless/InlandHotel?checkInDate=20170210&checkOutDate=20170211&hotelId=435383&hotelDataType=1
        startIntent("ctrip://wireless/hotel_wise_detail?c1=20170213&c2=20170214&c3=435383&c4=1", "ctrip.android.view",
                "ctrip.android.view.view.CtripBootActivity");
    }

    public void gotoQuna() {
        startIntent("qunaraphone://hy?mixedmode=0&type=navibar-none&url=https://touch.dujia.qunar.com/detail.qunar?id=499613064");
    }

    public void gotoLeshi() {
        startIntent("letvclient://msiteAction?actionType=9&vid=27209469&isfullscene=false");
    }

    public void gotoBaidu() {
        startIntent(
                "bdvideo://invoke?intent=#Intent;action=com.baidu.search.video;launchFlags=0x8000;component=com.baidu.video/.ui.ThirdInvokeActivtiy;S.vid=23833;S.vtype=tvplay;S.episode=1;end");
    }


    public void gotoTuniu() {
        // tuniuapp://travel/product_detail?product_id=210155472&product_type=102
        startIntent("tuniuandroid://productdetail/102/210155472?pValue=16213", "com.tuniu.app.ui", "com.tuniu.app.ui.homepage.LaunchActivity");
    }

    public void gotoDamai() {
        Uri uri = Uri.parse("dmextension://page1/code?code=113233");
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setClassName("cn.damai", "cn.damai.activity.ProjectDetailActivity");
        intent.setData(uri);
        System.out.println(intent.toURI().toString());
        mContext.startActivity(intent);
    }

    public void gotoIqiyi() {
        Uri uri = Uri.parse("iqiyi://mobile/player?aid=204166701&vid=5d2157eabcea7a5a82c0ca8975063d43");
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        // intent.setClassName("com.qiyi.video", "org.iqiyi.video.activity.PlayerActivity");
        intent.setData(uri);
        System.out.println(intent.toURI().toString());
        mContext.startActivity(intent);
    }

    public void gotoSouhu() {
        Uri uri = Uri.parse("sohuvideo://?action=1.1&vid=3357313");
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        // intent.setClassName("com.sohu.sohuvideo", "com.sohu.sohuvideo.ui.SohuEntryActivity");
        intent.setData(uri);
        System.out.println(intent.toURI().toString());
        mContext.startActivity(intent);

        // try {
        // Intent intent = Intent.parseUri(
        // "intent://?action=1.1&vid=3357313&backpage=0#Intent;scheme=sohuvideo;package=com.sohu.sohuvideo;end",
        // 0);
        // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // mContext.startActivity(intent);
        // } catch (URISyntaxException e) {
        // e.printStackTrace();
        // }
    }

    public void gotoTuDou() {
        Uri uri = Uri.parse("tudou://itemcode=9l2kqzTlFR4");
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        // intent.setClassName("com.tudou.android", "com.tudou.ui.activity.DetailActivity");
        intent.setData(uri);
        System.out.println(intent.toURI().toString());
        mContext.startActivity(intent);
    }

    public void gotoDianping() {
        // dptuan://tuandeal?id=22576529
        Uri uri = Uri.parse("dianping://shopinfo?id=18149081");
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        // intent.setClassName("com.dianping.v1",
        // "com.dianping.baseshop.activity.ShopInfoActivity");
        intent.setData(uri);
        System.out.println(intent.toURI().toString());
        mContext.startActivity(intent);
    }

    public void gotoYouku() {
        Uri uri = Uri.parse("youku://play?action=play&vid=XMjUwMTQ4Nzc2OA&source=ascheme-limitedplaybutton");
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        // intent.setClassName("com.youku.phone", "com.youku.ui.activity.DetailActivity");
        intent.setData(uri);
        System.out.println(intent.toURI().toString());
        mContext.startActivity(intent);
    }

    public void gotoQQLive() {
        Uri uri = Uri.parse("tenvideo2://?action=1&video_id=x00229vvcah");
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        // intent.setClassName("com.tencent.qqlive", "com.tencent.qqlive.open.QQLiveOpenActivity");
        intent.setData(uri);
        System.out.println(intent.toURI().toString());
        mContext.startActivity(intent);
    }


    public static void startPushService(Context context) {
        Intent serviceIntent = new Intent();
        serviceIntent.setAction("com.igexin.sdk.action.service.message");
        serviceIntent.setComponent(new ComponentName("com.wifi.test", "com.igexin.sdk.PushService"));
        context.startService(serviceIntent);

        // Intent convoyIntent = new Intent();
        // convoyIntent.setClassName("com.wifi.test", "com.igexin.sdk.GActivity");
        // convoyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // System.out.println("uri:" + convoyIntent.toString());
        // context.startActivity(convoyIntent);
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

    public static void goToYingYongBaoMarket(Context context, String packageName) {
        Uri uri = Uri.parse("market://details?id=" + packageName);
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            goToMarket.setClassName("com.tencent.android.qqdownloader", "com.tencent.pangu.link.LinkProxyActivity");
            context.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void goTo360ZhuShouMarket(Context context, String packageName) {
        Uri uri = Uri.parse("market://details?id=" + packageName);
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            goToMarket.setClassName("com.qihoo.appstore", "com.qihoo.appstore.distribute.SearchDistributionActivity");
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
                "openApp.jdMobile://virtual?params={\"category\":\"jump\",\"des\":\"m\",\"sourceValue\":\"sourceValue_gtDQ\",\"sourceType\":\"sourceType_gtDQ\",\"url\":\"http://h5.m.jd.com/active/3C3kFqSMepFJNpTRCfUgnsCdoTnh/index.html\"}");

        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        System.out.println(intent.toURI().toString());


        startActivity(intent);

    }

    void gotoIFeng() {
        Intent uri = null;
        try {
            uri = Intent.parseUri(
                    "#Intent;package=com.ifeng.news2;component=com.ifeng.news2/.activity.DetailActivity;S.extra.com.ifeng.news2.id=030010050602229;end",
                    0);
            System.out.println(uri.toURI().toString());
            startActivity(uri);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        //

    }


}
