package com.xiuxiuing.testing.activity;

import java.io.InputStream;
import java.lang.reflect.Field;

import com.socks.library.KLog;
import com.xiuxiuing.testing.R;
import com.xiuxiuing.testing.utils.ToastUtils;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.telephony.SmsMessage;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import android.widget.ZoomButtonsController;

/**
 * Created by wang on 16/7/14.
 */
public class WebviewActivity extends BaseActivity {
    WebView webView;
    // WebSettings settings;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        webView = (WebView) findViewById(R.id.webview);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDisplayZoomControls(false);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        webView.requestFocus();

        // settings.setSupportZoom(true);
        // settings.setBuiltInZoomControls(true);
        // 不显示webview缩放按钮


        // if (Build.VERSION.SDK_INT >= 19) {
        // settings.setLoadsImagesAutomatically(true);
        // } else {
        // settings.setLoadsImagesAutomatically(false);
        // }

        // webView.loadUrl("file:///android_asset/html/JavaAndJavaScriptCall.html");
        webView.loadUrl(
                "https://qcs.meituan.com/c/fe/activityCoupon?param=0f33385ea3bee4d035aea28abdec662b");
        webView.setWebViewClient(client);
        // webView.setWebChromeClient(chromeClient);
        // webView.setDownloadListener(new MyDownloadListenter());

        // String ua = "";
        // try {
        // if (Build.VERSION.SDK_INT > 16) {
        // ua = WebSettings.getDefaultUserAgent(this);
        // } else {
        // WebView webview;
        // webview = new WebView(this);
        // // webview.layout(0, 0, 0, 0);
        // WebSettings settings = webview.getSettings();
        // settings.setJavaScriptEnabled(true);
        // ua = settings.getUserAgentString();
        // // ua = new
        // // WebView(WKManager.getIns().getContext()).getSettings().getUserAgentString();
        // }
        //
        // } catch (Throwable t) {
        // } finally {
        // ToastUtils.showToastShort(this, "ua:" + ua);
        // }

        // webView.addJavascriptInterface(new JSInterface(), "Android");
        // try {
        // if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        // for (SmsMessage pdu : Telephony.Sms.Intents.getMessagesFromIntent(getIntent())) {
        // KLog.d(pdu.getDisplayMessageBody() + " " + pdu.getDisplayOriginatingAddress());
        // }
        // }
        // } catch (Exception e) {
        // e.printStackTrace();
        // }


    }

    @Override
    protected void onPause() {
        super.onPause();
        // webView.loadUrl("https://s.wcd.im/v/2bdalZ39");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.stopLoading();
        webView.removeAllViews();
        webView.destroy();
        webView = null;
    }

    WebViewClient client = new WebViewClient() {

    };

    class JSInterface {
        @JavascriptInterface
        public void showToast(String arg) {
            Toast.makeText(WebviewActivity.this, arg, Toast.LENGTH_SHORT).show();
        }
    }

    WebChromeClient chromeClient = new WebChromeClient() {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
        }
    };

    class MyDownloadListenter implements DownloadListener {

        @Override
        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }


    public void setZoomControlGone(View view) {
        Class classType;
        Field field;
        try {
            classType = WebView.class;
            field = classType.getDeclaredField("mZoomButtonsController");
            field.setAccessible(true);
            ZoomButtonsController mZoomButtonsController = new ZoomButtonsController(view);
            mZoomButtonsController.getZoomControls().setVisibility(View.GONE);
            try {
                field.set(view, mZoomButtonsController);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
