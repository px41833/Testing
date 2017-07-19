package com.xiuxiuing.testing.service;


import android.annotation.SuppressLint;
import android.os.Build;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.support.annotation.RequiresApi;

import com.socks.library.KLog;

/**
 * Created by wang on 17/6/30.
 */

@SuppressLint("OverrideAbstract")
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
public class NotifiListenerService extends NotificationListenerService {


    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);
        KLog.d("onNotificationPosted");
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        super.onNotificationRemoved(sbn);
        KLog.d("onNotificationRemoved");
    }
}
