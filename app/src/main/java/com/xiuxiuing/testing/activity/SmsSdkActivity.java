package com.xiuxiuing.testing.activity;


import com.socks.library.KLog;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.annotation.Nullable;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;


/**
 * Created by wang on 17/3/28.
 */

public class SmsSdkActivity extends BaseActivity {
    private static final String TAG = "SmsSdkActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // SMSSDK.initSDK(this, "1c84c4a11ef9c", "d7ae28c4e5aa4fa9a7f325a8d8c7fc9e");
        //
        // // 打开注册页面
        // RegisterPage registerPage = new RegisterPage();
        // registerPage.setRegisterCallback(new EventHandler() {
        // public void afterEvent(int event, int result, Object data) {
        // // 解析注册结果
        // if (result == SMSSDK.RESULT_COMPLETE) {
        // @SuppressWarnings("unchecked")
        // HashMap<String, Object> phoneMap = (HashMap<String, Object>) data;
        // String country = (String) phoneMap.get("country");
        // String phone = (String) phoneMap.get("phone");
        //
        // // 提交用户信息（此方法可以不调用）
        // // registerUser(country, phone);
        // }
        // }
        // });
        // registerPage.show(this);
        IntentFilter filter = new IntentFilter();
        // filter.addAction(Telephony.Sms.Intents.SMS_RECEIVED_ACTION);
        registerReceiver(rece, filter);

        SmsManager smsManager = SmsManager.getDefault();
        if (Build.VERSION.SDK_INT >= 26) {
            String token = smsManager.createAppSpecificSmsToken(buildPendingIntent());
            KLog.d("token:" + token);
        }


    }

    private PendingIntent buildPendingIntent() {
        return (PendingIntent.getBroadcast(this, 1367, new Intent(this, rece.getClass()), PendingIntent.FLAG_UPDATE_CURRENT));
    }

    BroadcastReceiver rece = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    for (SmsMessage pdu : Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                        KLog.d(pdu.getDisplayMessageBody() + "  " + pdu.getDisplayOriginatingAddress());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
}
