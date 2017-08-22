package com.xiuxiuing.testing.activity;

import com.socks.library.KLog;
import com.xiuxiuing.testing.R;
import com.xiuxiuing.testing.utils.ToastUtils;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import java.util.HashMap;

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


        findViewById(R.id.btn_start).setOnClickListener(this);
        findViewById(R.id.btn_jingdong).setOnClickListener(this);
        findViewById(R.id.btn_yingyongbao).setOnClickListener(this);
        findViewById(R.id.btn_douyu).setOnClickListener(this);
        findViewById(R.id.btn_panda).setOnClickListener(this);
        findViewById(R.id.btn_huya).setOnClickListener(this);
        findViewById(R.id.btn_longzhu).setOnClickListener(this);
        findViewById(R.id.btn_quanmin).setOnClickListener(this);

        findViewById(R.id.btn_360zhushou).setOnClickListener(this);
        findViewById(R.id.btn_sinagame).setOnClickListener(this);
        findViewById(R.id.btn_getui).setOnClickListener(this);

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
        findViewById(R.id.btn_360service).setOnClickListener(this);
        findViewById(R.id.btn_360activity).setOnClickListener(this);

        String mPhone = ((TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE)).getLine1Number();
        System.out.println("mPhone:" + mPhone);

        // startIntent("taobao://a.m.taobao.com/i524638533701.htm", "com.taobao.taobao",
        // "com.taobao.tao.detail.activity.DetailActivity");
        // startTmallShop();

        // try {
        // Intent intent = new Intent();
        // intent.setClassName("com.getui.library.startapp",
        // "com.getui.library.startapp.MainService");
        // mContext.startService(intent);
        // } catch (Exception e) {
        // e.printStackTrace();
        // }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                startIntent();
                break;
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
                goToYingYongBaoMarket(mContext, "com.huajiao");
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
            case R.id.btn_360service:
                start360Service();
                break;
            case R.id.btn_360activity:
                start360Activity();
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
        startIntent("ctrip://wireless/vacation_group_detail?c1=17&c2=13757975&c3=17", "ctrip.android.view",
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
        Uri uri = Uri.parse("dmextension://page1/code?utm_source=damai&utm_medium=msite&utm_campaign=translink_120156&code=120156");
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);

        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setClassName("cn.damai", "cn.damai.newtradeorder.ui.projectdetail.ui.activity.SchemeActivity");
        intent.setData(uri);
        System.out.println(intent.toURI().toString());
        mContext.startActivity(intent);

        // try {
        // Intent intent1 =
        // Intent.parseUri("dmextension://page1/code?utm_source=damai&utm_medium=msite&utm_campaign=translink_120156&code=120156",
        // 0);
        // mContext.startActivity(intent1);
        // } catch (Exception e) {
        // e.printStackTrace();
        // }
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
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // intent.setClassName("air.tv.douyu.android", "tv.douyu.view.activity.MainActivity");
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
        // dat=yykiwi://channel?sid=77259038&subSid=2535706816
        Intent intent = null;
        try {
            intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            Uri uri = Uri.parse("#Intent;component=com.duowan.kiwi/com.duowan.kiwi.channelpage.ChannelPage;l.subSid=2535706816;l.sid=77259038;end");
            intent.setData(uri);
            // intent = Intent.parseUri(
            // "pp://www.25pp.com/down?packagename=com.woqutz.didi&appid=6702146&ch=#Intent;launchFlags=0x10000000;component=com.pp.assistant/.activity.PPExternalDialogActivity;end",
            // 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(intent.toURI().toString());
        context.startActivity(intent);

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


        Uri uri = Uri.parse("plulongzhulive://room/openwith?roomId=348652");
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

    public void goToYingYongBaoMarket(Context context, String packageName) {
        // 自动下载
        // startIntent("tpmast://appdetails?pname=" + packageName +
        // "&versioncode=0&oplist=1&via=ANDROIDQQ.YYB.SHARESOURCE&request_type=1",
        // "com.tencent.android.qqdownloader", "com.tencent.pangu.link.LinkProxyActivity");

        try {

            Intent intent = new Intent("android.intent.action.VIEW");

            // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // intent.addCategory(Intent.CATEGORY_BROWSABLE);

            // if (!TextUtils.isEmpty(etPkg.getText().toString())) {
            // intent.setPackage(etPkg.getText().toString());
            // }
            // intent.setPackage("com.tencent.android.qqdownloader");
            // intent.setClassName("com.tencent.android.qqdownloader",
            // "com.tencent.pangu.link.LinkProxyActivity");
            intent.setData(Uri.parse("tmast://webview?mode=0&url=http://www.baidu.com"));

            if (intent.resolveActivityInfo(getPackageManager(), 0) == null) {
                KLog.d("START u0 null null ");
            }

            mContext.startActivity(intent);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void goTo360ZhuShouMarket(Context context, String packageName) {
        Intent localIntent = new Intent("com.huawei.appmarket.ext.public");
        localIntent.putExtra("thirdId", "4026640");
        localIntent.putExtra("openStr", getOpenStr(
                "http://hispaceclt.hicloud.com:8080/hwmarket/h5/app?h5Name=%E4%BC%A0%E5%A5%87%E4%B8%96%E7%95%8C&iv=gm1krjImlbV%2BqLq18jYrEA%3D%3D&sign=d9001011e611115320000000@BB07DE4C277AC2DCE360C2246A0358FC&clientDeviceType=0&serviceType=5&token=7l5zyDBdj1fr7%2BrATE46SUyH4TcZ0eEUdc%2BZCK4qNyO2lXsC2d8nAVr4paAEWJOQVrA%2BNSE7caUgaN1N8wedRA%2B1%2FbtD3dza9VMTQ36rar7n1Io3v2Gd%2Bh3sP2X2%2F8%2FX&userId=D312581B51E53AA72BFAC711AD8C18B9&appid=H10561643&hcrId=C3E1A057D82F4E75A0C334C902DC51B6&terminalType=ONEPLUS%20A3000&devicetype=0&clientPackage=com.huawei.gamebox&direction=1&trace=b47544e098bd43fba2a89afc7e19ba9f;__entrance7;__e3882109fb794cde9f167737a986b0aa;9f3233cb0774420cb92a8ad1e8f6a6d1|1499668121853;app|H10561643__autoList____50__1__b9d782611da1383faa07626aed64b4c0%3B&thirdId=null&showServiceType=5&clientVersionCode=70203301"));
        localIntent.setPackage("com.huawei.gamebox");
        KLog.e("START u0:" + localIntent.toURI().toString());
        mContext.startActivity(localIntent);
    }

    private static String getOpenStr(String paramString) {
        return "{\"openId\":\"38\",\"params\":[{\"name\":\"url\",\"type\":\"String\",\"value\":\"" + paramString + "\"}]}";
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
        Uri uri = Uri.parse("sinaweibo://snggamedetail/2649115076?aid=503972&acid=0&auto=0");
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
                "openApp.jdMobile://virtual?params={\"category\":\"jump\",\"des\":\"m\",\"sourceValue\":\"sourceValue_gtDQ\",\"sourceType\":\"sourceType_gtDQ\",\"url\":\"https://item.m.jd.com/product/3772942.html\"}");

        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        System.out.println(intent.toURI().toString());


        startActivity(intent);

    }

    void gotoIFeng() {
        Intent uri = null;
        try {
            uri = Intent.parseUri(
                    "pp://www.25pp.com/down?packagename=&appid=46527&ch=#Intent;launchFlags=0x10000000;component=com.pp.assistant/.activity.PPExternalDialogActivity;end",
                    0);
            uri.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            System.out.println(uri.toURI().toString());
            startActivity(uri);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        //

    }

    public void start360Service() {
        // Intent intent = new Intent();
        // intent.setAction("com.getui.gtc.sdk.service.action");
        // intent.setClassName("com.gysdk.demo", "com.getui.gtc.GtcService");
        // System.out.println(intent.toURI().toString());
        // startService(intent);

        Intent intent = new Intent();
        intent.setAction("cn.ninegame.gamemanager.action.PULLUP");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setPackage("cn.ninegame.gamemanager");
        // 指定Package，防止拦截/*** Url 参数规则:* 1. 必须符合统跳协议*
        // 2. target参数必须是url的最后一个参数* 3. 参数中，
        // 如有Url特殊字符，必须使用 UrlEncoder encode 两次* 4.
        // 参数中，如有敏感字段需要加密，建议使用AES#encryptToBase64Str和AES#decryptBase64Str
        // 加解密(目前客户端和SDK的密钥是一致的)，加密后的字符串有可能会有出现Url特殊字符，所以需要执行第三点*/
        String url = "http://web.9game.cn/share?pageType=browser&target=https%3a%2f%2frender-ant.9game.cn%2fp%2fq%2f3fa23c610%2fportal.html";
//        String url = "http://web.9game.cn/share?pageType=browser&target=http://u.9game.cn/2bqiraqq";
        intent.putExtra("request", "url_jump_intent");
        intent.putExtra("url_jump_url", url);
//        intent.putExtra("skip_splash", true);
        // // 是否跳过闪屏， 4.7.6.0 以上支持

//        intent.putExtra("skip_home", false);
        // 是否跳过首页, 4.7.6.0 以上支持
        startActivity(intent);

    }

    public void start360Activity() {
        // Uri uri = Uri.parse("baidumap://map/cost_share?url=http://www.baidu.com");
        Uri uri = Uri.parse("http://prom.m.gome.com.cn/html/prodhtml/topics/201704/26/sale9G0jY9WS99Y.html");
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setClassName("com.gome.eshopnew", "com.gome.ecmall.wap.sales.WapSalesActivity");
        intent.setData(uri);
        KLog.e("START u0:" + intent.toURI().toString());
        mContext.startActivity(intent);
    }

    public void startTmallShop() {

        try {
            // Uri uri = Uri.parse("baidumap://map/cost_share?url=http://www.baidu.com");
            Uri uri = Uri.parse("tmall://page.tm/webview?url=https://equity-vip.tmall.com/agent/mobile.htm?agentId=35927&_bind=true");
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // intent.setClassName("com.baidu.appsearch", "com.baidu.appsearch.UrlHandlerActivity");
            intent.setData(uri);
            KLog.e("START u0:" + intent.toURI().toString());
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
            //
            // HashMap bundle = new HashMap();
            // bundle.put("enterMod", "hehe");
            // bundle.put("enterMod2", "hehe");
            // bundle.put("enterId", "4");
            //
            // intent.putExtra("extra.key.jump.data", bundle);

            // intent.setPackage("com.tencent.android.qqdownloader");
            // intent.setClassName("com.tencent.android.qqdownloader",
            // "com.tencent.pangu.link.LinkProxyActivity");

            intent.setData(uri);



            KLog.e("START u0:" + intent.toUri(Intent.FLAG_ACTIVITY_NEW_TASK).toString());
            KLog.e("START u0 get data:" + intent.getDataString());

            KLog.e("START u0 get Query:" + intent.toString());



            // String s = intent.getData().getQueryParameter("params");
            // JSONArray array = new JSONObject(s).getJSONArray("params");
            // JSONObject object = array.getJSONObject(0);
            // String tag = object.getString("iv");
            // object.put("null", JSONObject.NULL);
            // KLog.d("START u0 " + object.toString());
            // HashMap hashMap = (HashMap)intent.getSerializableExtra("extra.key.jump.data");

            mContext.startActivity(intent);



        } catch (Exception e) {
            e.printStackTrace();
            ToastUtils.showToastLong(mContext, "检查应用是否安装");
        }

    }

}
