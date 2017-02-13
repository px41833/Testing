package com.xiuxiuing.testing.service;

import android.accessibilityservice.AccessibilityService;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.os.Parcelable;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.socks.library.KLog;
import com.xiuxiuing.testing.utils.ProcessesUtils;

import java.util.List;

/**
 * Created by wang on 16/12/13.
 */
public class RedMoneyService extends AccessibilityService {
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        int eventType = event.getEventType();
        KLog.d("eventtype:" + eventType + ": " + event.getClassName().toString());
        KLog.d("foreApp:" + ProcessesUtils.getForegroundApp());
        switch (eventType) {
            // 第一步：监听通知栏消息
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:
                if (event.getParcelableData() != null && event.getParcelableData() instanceof Notification) {
                    // Notification notification = (Notification) event.getParcelableData();
                    // // 打开通知栏的intent，即打开对应的聊天界面
                    // PendingIntent pendingIntent = notification.contentIntent;
                    // IntentSender sender = pendingIntent.getIntentSender();
                    // sender.getTargetPackage();
                    //
                    // KLog.d("intent:" + pendingIntent.toString());
                }

                break;
            // 第二步：监听是否进入微信红包消息界面
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:

                break;
            case AccessibilityEvent.TYPE_VIEW_CLICKED:
                Parcelable data = event.getParcelableData();
                KLog.d("data");
                break;
        }
    }

    /**
     * 查找到
     */
    @SuppressLint("NewApi")
    private void openPacket() {
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        if (nodeInfo != null) {
            List<AccessibilityNodeInfo> list = nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/bdh");
            for (AccessibilityNodeInfo n : list) {
                n.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            }
        }

    }

    @SuppressLint("NewApi")
    private void getPacket() {
        AccessibilityNodeInfo rootNode = getRootInActiveWindow();
        recycle(rootNode);
    }

    /**
     * 打印一个节点的结构
     * 
     * @param info
     */
    @SuppressLint("NewApi")
    public void recycle(AccessibilityNodeInfo info) {
        if (info.getChildCount() == 0) {
            if (info.getText() != null) {
                if ("领取红包".equals(info.getText().toString())) {
                    // 这里有一个问题需要注意，就是需要找到一个可以点击的View
                    Log.i("demo", "Click" + ",isClick:" + info.isClickable());
                    info.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    AccessibilityNodeInfo parent = info.getParent();
                    while (parent != null) {
                        Log.i("demo", "parent isClick:" + parent.isClickable());
                        if (parent.isClickable()) {
                            parent.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                            break;
                        }
                        parent = parent.getParent();
                    }

                }
            }

        } else {
            for (int i = 0; i < info.getChildCount(); i++) {
                if (info.getChild(i) != null) {
                    recycle(info.getChild(i));
                }
            }
        }
    }

    @Override
    public void onInterrupt() {

    }
}
