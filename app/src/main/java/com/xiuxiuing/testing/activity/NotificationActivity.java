package com.xiuxiuing.testing.activity;

import com.xiuxiuing.testing.R;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RemoteViews;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wang on 17/6/27.
 */

public class NotificationActivity extends BaseActivity {

    @BindView(R.id.btn_noti1)
    Button button;

    @BindView(R.id.cb_box)
    CheckBox cbBox;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.title_android_notify);
        setContentView(R.layout.activity_notify);
        ButterKnife.bind(this);

        // button.setOnClickListener(new View.OnClickListener() {
        // @TargetApi(26)
        // @Override
        // public void onClick(View v) {
        //
        // NotificationManager notificationManager = (NotificationManager)
        // getSystemService(NOTIFICATION_SERVICE);
        // if (android.os.Build.VERSION.SDK_INT >= 20) {
        //
        // // Notification.Builder mBuilder = new Notification.Builder(this);
        // // mBuilder.setContentTitle("dfasfdas");
        // // mBuilder.setContentText("wetrwtwe");
        // // mBuilder.setTicker("dsfdasf");
        // // mBuilder.setWhen(System.currentTimeMillis());
        // // mBuilder.setSmallIcon(R.mipmap.icon);
        // // Notification notification = mBuilder.getNotification();
        // // if (notification.contentView == null){
        // // KLog.d("notification.contentView == null");
        // // }
        // //
        // // notificationManager.notify(123, notification);
        //
        // RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.notify);
        // //
        // int nid = (int) System.currentTimeMillis() % 1000;
        // //
        // // final Notification group = new
        // //
        // NotificationCompat.Builder(NotificationActivity.this).setSmallIcon(R.mipmap.ic_launcher)
        // // .setContentTitle("hehe").setAutoCancel(false).setContentText("group").build();
        // //
        // // final Notification group2 = new
        // //
        // NotificationCompat.Builder(NotificationActivity.this).setSmallIcon(R.mipmap.ic_launcher)
        // //
        // .setContentTitle("000000").setAutoCancel(true).setGroupSummary(false).setContentText("group").build();
        // // final Notification group3 = new
        // //
        // NotificationCompat.Builder(NotificationActivity.this).setSmallIcon(R.mipmap.ic_launcher)
        // //
        // .setContentTitle("000000").setAutoCancel(true).setGroupSummary(false).setContentText("group").build();
        // // final Notification group4 = new
        // //
        // NotificationCompat.Builder(NotificationActivity.this).setSmallIcon(R.mipmap.ic_launcher)
        // //
        // .setContentTitle("000000").setAutoCancel(true).setGroupSummary(false).setContentText("group").build();
        // //
        // // NotificationCompat.Builder builder = new
        // //
        // NotificationCompat.Builder(NotificationActivity.this).setSmallIcon(R.mipmap.ic_launcher)
        // //
        // .setContentTitle("group子通知栏标题").setGroup("分组信息").setAutoCancel(true).setContentText("group子通知栏内容");
        // // builder.setContent(remoteViews);
        // //
        // //
        // // Notification newMessageNotification = builder.build();
        //
        //
        // // NotificationChannel mChannel = null;
        // //
        // // mChannel = new NotificationChannel("channel", "渠道测试",
        // // NotificationManager.IMPORTANCE_DEFAULT);
        // // mChannel.enableLights(true);
        // // mChannel.setLightColor(Color.RED);
        // // mChannel.enableVibration(true);
        // // mChannel.setVibrationPattern(new long[] {100, 200, 300, 400, 500, 400, 300,
        // // 200, 400});
        // // notificationManager.createNotificationChannel(mChannel);
        // //
        // // Notification notification = new
        // // NotificationCompat.Builder(NotificationActivity.this).setContentTitle("New
        // // Message")
        // // .setContentText("You've received new
        // // messages.").setSmallIcon(R.mipmap.icon).setChannel("channel").build();
        // // notificationManager.notify(1100 , notification);
        //
        //
        //
        // // notificationManager.notify(1007, group);
        // // notificationManager.notify(1001, newMessageNotification);
        // // notificationManager.notify(1002, newMessageNotification);
        // // notificationManager.notify(1004, newMessageNotification);
        // // notificationManager.notify(1008, newMessageNotification);
        // // notificationManager.notify(1010, newMessageNotification);
        // // notificationManager.notify(1006 + id, newMessageNotification);
        // //
        // //
        // // notificationManager.notify(1005 + id, group);
        // // notificationManager.notify(1003 + id, group2);
        // // notificationManager.notify(1000 + id, group3);
        // // notificationManager.notify(1009 + id, group4);
        // // notificationManager.notify(1011, group);
        //
        //
        //
        // // RemoteInput remoteInput = new
        // // RemoteInput.Builder("KEY_TEXT_REPLY").setLabel("回复点什么呢？").build();
        // //
        // // Intent intent = new Intent();
        // // intent.putExtra("id", 100);
        // // intent.setClass(this, this.getClass());
        // // PendingIntent replyPendingIntent = PendingIntent.getActivity(this, 0, intent,
        // // PendingIntent.FLAG_ONE_SHOT);
        // // Notification.Action action =
        // // new Notification.Action.Builder(R.mipmap.ic_launcher, "点击这里回复东西",
        // // replyPendingIntent).addRemoteInput(remoteInput).build();
        // //
        // // // Build the notification and add the action.
        // // final Notification newNotification =
        // // new
        // //
        // Notification.Builder(this).setSmallIcon(R.mipmap.ic_launcher).setContentTitle("通知栏标题").setContentText("通知栏内容")
        // //
        // .setPriority(Notification.PRIORITY_HIGH).setDefaults(Notification.DEFAULT_VIBRATE).addAction(action).build();
        // // // Issue the notification.
        // // NotificationManager notificatiManager = (NotificationManager)
        // // getSystemService(NOTIFICATION_SERVICE);
        // // notificatiManager.notify(101, newNotification);
        // //
        // // try {
        // // Bundle remoteInput1 = RemoteInput.getResultsFromIntent(getIntent());
        // // String res = null;
        // // if (remoteInput != null) {
        // // res = (String) remoteInput1.getCharSequence("KEY_TEXT_REPLY");
        // // }
        // // Notification repliedNotification =
        // // new
        // // Notification.Builder(this).setSmallIcon(R.mipmap.ic_launcher).setContentText(res
        // // + "回复成功").build();
        // // // Issue the new notification.使用这个相同的ID冲掉以前的notifacation
        // // NotificationManager notificationManager1 = (NotificationManager)
        // // getSystemService(NOTIFICATION_SERVICE);
        // // int id = intent.getIntExtra("id", 1);
        // // notificationManager1.notify(id, repliedNotification);
        // // } catch (Exception e) {
        // // e.printStackTrace();
        // // }
        // }
        // }
        // });



    }

    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    @OnClick(R.id.btn_noti1)
    public void btnClick(View v) {
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        int importance = NotificationManager.IMPORTANCE_LOW;

        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.notify);
        if (android.os.Build.VERSION.SDK_INT >= 26) {

            String CHANNEL_ID = "my_channel_01";
            CharSequence name = "channel_name";
            NotificationChannel mChannel = null;
            mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[] {100, 200, 300, 400, 500, 400, 300, 200, 400});
            mNotificationManager.createNotificationChannel(mChannel);

            Notification notification = new Notification.Builder(NotificationActivity.this, CHANNEL_ID).setContentTitle("New Message")
                    .setStyle(new Notification.InboxStyle().setSummaryText("hehehehh")).setContentText("You've received new messages.")
                    .setSmallIcon(R.mipmap.icon).setGroup("分组").setGroupSummary(true).build();
            notification.contentIntent =
                    PendingIntent.getActivity(this, 1367, new Intent(this, WebviewActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

            Notification notification1 = new Notification.Builder(NotificationActivity.this, CHANNEL_ID).setContentTitle("New Message1")
                    .setContentText("You've received new messages1.").setSmallIcon(R.mipmap.icon).setGroup("分组").setGroupSummary(cbBox.isChecked())
                    .build();

            Notification notification2 = new Notification.Builder(NotificationActivity.this, CHANNEL_ID).setContentTitle("New Message2")
                    .setContentText("You've received new messages2.").setSmallIcon(R.mipmap.icon).setGroup("分组").setGroupSummary(cbBox.isChecked())
                    .setCustomContentView(remoteViews).build();
            // Issue the notification.
            int nid = (int) System.currentTimeMillis() % 1000;
            mNotificationManager.notify(nid + 10001, notification);
            mNotificationManager.notify(nid + 10002, notification1);
            // mNotificationManager.notify(nid + 10003, notification2);
        } else {
            Notification notification = new NotificationCompat.Builder(NotificationActivity.this).setContentTitle("New Message")
                    .setContentText("You've received new messages.").setSmallIcon(R.mipmap.icon).setGroup("分组").setGroupSummary(cbBox.isChecked())
                    .build();
            notification.contentIntent =
                    PendingIntent.getActivity(this, 1367, new Intent(this, WebviewActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

            Notification notification1 = new NotificationCompat.Builder(NotificationActivity.this).setContentTitle("New Message1")
                    .setContentText("You've received new messages1.").setSmallIcon(R.mipmap.icon).setGroup("分组").setGroupSummary(cbBox.isChecked())
                    .build();

            Notification notification2 = new NotificationCompat.Builder(NotificationActivity.this).setContentTitle("New Message2")
                    .setContentText("You've received new messages2.").setSmallIcon(R.mipmap.icon).setGroup("分组").setGroupSummary(cbBox.isChecked())
                    .setCustomContentView(remoteViews).build();
            // Issue the notification.
            int nid = (int) System.currentTimeMillis() % 1000;
            mNotificationManager.notify(nid + 10004, notification);
            mNotificationManager.notify(nid + 10005, notification1);
            mNotificationManager.notify(nid + 10006, notification2);
        }



    }
}
