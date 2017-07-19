package com.xiuxiuing.testing.activity;

import java.util.Set;

import com.xiuxiuing.testing.R;
import com.xiuxiuing.testing.utils.ToastUtils;

import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import android.support.v4.app.NotificationManagerCompat;
import android.view.View;

/**
 * Created by wang on 17/1/23.
 */
public class SystemSwitchActivity extends BaseActivity implements View.OnClickListener {
    private Context mContext;
    private static Camera camera;
    private PowerManager mPowerManager;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_systemswitch);
        findViewById(R.id.system_location).setOnClickListener(this);
        findViewById(R.id.system_setting).setOnClickListener(this);
        findViewById(R.id.system_app).setOnClickListener(this);
        findViewById(R.id.system_display).setOnClickListener(this);
        findViewById(R.id.system_sound).setOnClickListener(this);
        findViewById(R.id.system_date).setOnClickListener(this);
        findViewById(R.id.system_sync).setOnClickListener(this);
        findViewById(R.id.system_input).setOnClickListener(this);
        findViewById(R.id.system_flash).setOnClickListener(this);
        findViewById(R.id.system_screen).setOnClickListener(this);
        findViewById(R.id.system_reboot).setOnClickListener(this);
        findViewById(R.id.system_down).setOnClickListener(this);
        findViewById(R.id.system_notifi).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.system_location:
                systemLocationUtils();
                break;
            case R.id.system_setting:
                systemSetUtils();
                break;
            case R.id.system_app:
                systemAppsUtils();
                break;
            case R.id.system_display:
                systemDisplayUtils();
                break;
            case R.id.system_sound:
                systemSoundUtils();
                break;
            case R.id.system_date:
                systemDateUtils();
                break;
            case R.id.system_sync:
                systemSyncUtils();
                break;
            case R.id.system_input:
                systemInputUtils();
                break;
            case R.id.system_flash:
                flashUtils();
                break;
            case R.id.system_screen:
                lockScreenSwitchUtils();
                break;
            case R.id.system_reboot:
                rebootUtils();
                break;
            case R.id.system_down:
                shutDownSwitchUtils();
                break;
            case R.id.system_notifi:
                isNotificationListenerServiceEnabled(mContext);
                openNotificationAccess();
                break;
            default:
                break;
        }
    }

    /**
     * 跳转到系统设置
     */
    public void systemSetUtils() {
        Intent intent = new Intent(Settings.ACTION_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);

    }

    /**
     * 跳转到系统位置设置
     */
    public void systemLocationUtils() {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);

    }

    /**
     * 跳转到系统app管理
     */
    public void systemAppsUtils() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);

    }

    /**
     * 跳转到系统显示设置
     */
    public void systemDisplayUtils() {
        Intent intent = new Intent(Settings.ACTION_DISPLAY_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);

    }

    /**
     * 跳转到系统声音设置
     */
    public void systemSoundUtils() {
        Intent intent = new Intent(Settings.ACTION_SOUND_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);

    }

    /**
     * 跳转到系统日期设置
     */
    public void systemDateUtils() {
        Intent intent = new Intent(Settings.ACTION_DATE_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);

    }

    /**
     * 跳转到系统同步设置
     */
    public void systemSyncUtils() {
        Intent intent = new Intent(Settings.ACTION_SYNC_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);

    }

    /**
     * 跳转到系统输入设置
     */
    public void systemInputUtils() {
        Intent intent = new Intent(Settings.ACTION_INPUT_METHOD_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);

    }

    /**
     * 是否开启了闪光灯
     * 
     * @return
     */
    public boolean isFlashlightOn() {
        if (camera == null) {
            camera = Camera.open();
        }

        Camera.Parameters parameters = camera.getParameters();
        String flashMode = parameters.getFlashMode();

        if (flashMode.equals(Camera.Parameters.FLASH_MODE_TORCH)) {

            return true;
        } else {
            return false;
        }
    }

    /**
     * 闪光灯开关
     */
    public void flashlightUtils() {
        if (camera == null) {
            camera = Camera.open();
        }

        Camera.Parameters parameters = camera.getParameters();
        // String flashMode = parameters.getFlashMode();

        if (isFlashlightOn()) {

            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);// 关闭
            camera.setParameters(parameters);
            camera.release();
            camera = null;
            ToastUtils.showToastShort(mContext, "关闭手电筒");
        } else {
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);// 开启
            camera.setParameters(parameters);
            ToastUtils.showToastShort(mContext, "开启手电筒");

        }

    }

    /**
     * 闪光灯开关2
     */
    public void flashUtils() {
        Camera camera = Camera.open();
        Camera.Parameters parameters = camera.getParameters();
        String flashMode = parameters.getFlashMode();
        if (flashMode.equals(Camera.Parameters.FLASH_MODE_TORCH)) {
            camera.stopPreview();
            camera.release();
            camera = null;

        } else {

            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            camera.setParameters(parameters);
            camera.autoFocus(new Camera.AutoFocusCallback() {
                public void onAutoFocus(boolean success, Camera camera) {}
            });
            camera.startPreview();
        }
    }

    /**
     * 锁屏
     */
    public void lockScreenSwitchUtils() {
        if (mPowerManager == null) {
            mPowerManager = (PowerManager) mContext.getSystemService(Context.POWER_SERVICE);
        }
        // mPowerManager.goToSleep(SystemClock.uptimeMillis());
    }

    /**
     * 重启
     */
    public void rebootUtils() {
        if (mPowerManager == null) {
            mPowerManager = (PowerManager) mContext.getSystemService(Context.POWER_SERVICE);
        }
        mPowerManager.reboot(null);

    }

    /**
     * 关机
     */
    public void shutDownSwitchUtils() {
        Intent intent = new Intent("android.intent.action.ACTION_REQUEST_SHUTDOWN");
        intent.putExtra("android.intent.extra.KEY_CONFIRM", false);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // 弹出系统内置的对话框，选择确定关机或取消关机
        mContext.startActivity(intent);


    }

    public void openNotificationAccess() {
        startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
    }

    public boolean isNotificationListenerServiceEnabled(Context context) {
        Set<String> pkgs = NotificationManagerCompat.getEnabledListenerPackages(context);
        if (pkgs.contains(context.getPackageName())) {
            return true;
        }
        return false;
    }
}
