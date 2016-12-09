package com.xiuxiuing.testing.listener;

import android.util.Log;

import net.youmi.android.normal.spot.SpotListener;

/**
 * Created by wang on 16/11/29.
 */
public class ClsaaSpotListener implements SpotListener {
    private static final String TAG = "ClsaaSpotListener";
    @Override
    public void onShowSuccess() {
        Log.d(TAG, "展示成功");
    }

    @Override
    public void onShowFailed(int i) {
        Log.d(TAG, "onShowFailed: 展示失败");
    }

    @Override
    public void onSpotClosed() {
        Log.d(TAG, "onSpotClosed: 插播被关闭");
    }

    @Override
    public void onSpotClicked(boolean b) {
        Log.d(TAG, "onSpotClicked: 插播被点击");
    }
}
