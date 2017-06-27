package com.xiuxiuing.testing.activity;

import java.util.HashMap;

import android.os.Bundle;
import android.support.annotation.Nullable;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;

/**
 * Created by wang on 17/3/28.
 */

public class SmsSdkActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SMSSDK.initSDK(this, "1c84c4a11ef9c", "d7ae28c4e5aa4fa9a7f325a8d8c7fc9e");

        // 打开注册页面
        RegisterPage registerPage = new RegisterPage();
        registerPage.setRegisterCallback(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                // 解析注册结果
                if (result == SMSSDK.RESULT_COMPLETE) {
                    @SuppressWarnings("unchecked")
                    HashMap<String, Object> phoneMap = (HashMap<String, Object>) data;
                    String country = (String) phoneMap.get("country");
                    String phone = (String) phoneMap.get("phone");

                    // 提交用户信息（此方法可以不调用）
                    // registerUser(country, phone);
                }
            }
        });
        registerPage.show(this);
    }
}
