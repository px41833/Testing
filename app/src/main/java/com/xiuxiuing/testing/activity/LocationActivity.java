package com.xiuxiuing.testing.activity;

import com.socks.library.KLog;
import com.xiuxiuing.testing.R;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

/**
 * Created by wang on 16/6/16.
 */
public class LocationActivity extends BaseActivity {
    String TAG = "LocationActivity";
    LocationManager lm;
    Location location;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);
        setTitle(R.string.title_android_location);
        lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }

        String sAll = Settings.System.getString(getContentResolver(), Settings.System.LOCATION_PROVIDERS_ALLOWED);
        String mode = Settings.System.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        KLog.d("sAll: " + sAll);
        KLog.d("mode: " + mode);

        // gps network gps,network

        // 判断GPS是否正常启动
        // if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
        // Toast.makeText(this, "请开启GPS导航...", Toast.LENGTH_SHORT).show();
        // // 返回开启GPS导航设置界面
        // Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        // startActivityForResult(intent, 0);
        // return;
        // }


        try {
            location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Log.i(TAG, "经度：" + location.getLongitude());
            Log.i(TAG, "纬度：" + location.getLatitude());
            Log.i(TAG, "海拔：" + location.getAltitude());
            Log.i(TAG, "精确度:" + location.getAccuracy());
            Log.i(TAG, "速度:" + location.getSpeed());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //
        // try {
        // location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        // Log.i(TAG, "经度：" + location.getLongitude());
        // Log.i(TAG, "纬度：" + location.getLatitude());
        // Log.i(TAG, "海拔：" + location.getAltitude());
        // Log.i(TAG, "精确度:" + location.getAccuracy());
        // Log.i(TAG, "速度:" + location.getSpeed());
        // } catch (Exception e) {
        // e.printStackTrace();
        // }
        //
        // try {
        // String bestProvider = lm.getBestProvider(getCriteria(), true);
        // location = lm.getLastKnownLocation(bestProvider);
        // Log.i(TAG, "时间：" + location.getTime());
        // Log.i(TAG, "经度：" + location.getLongitude());
        // Log.i(TAG, "纬度：" + location.getLatitude());
        // Log.i(TAG, "海拔：" + location.getAltitude());
        // Log.i(TAG, "精确度:" + location.getAccuracy());
        // Log.i(TAG, "速度:" + location.getSpeed());
        // } catch (Exception e) {
        // e.printStackTrace();
        // }

        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, -1, -1, locationListener);


    }

    private Criteria getCriteria() {
        Criteria criteria = new Criteria();
        // 设置定位精确度 Criteria.ACCURACY_COARSE比较粗略，Criteria.ACCURACY_FINE则比较精细
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        // 设置是否要求速度
        criteria.setSpeedRequired(false);
        // 设置是否允许运营商收费
        criteria.setCostAllowed(false);
        // 设置是否需要方位信息
        criteria.setBearingRequired(false);
        // 设置是否需要海拔信息
        criteria.setAltitudeRequired(false);
        // 设置对电源的需求
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        return criteria;
    }

    private LocationListener locationListener = new LocationListener() {

        /**
         * 位置信息变化时触发
         */
        public void onLocationChanged(Location location) {
            Log.i(TAG, "onLocationChanged时间：" + location.getTime());
            Log.i(TAG, "onLocationChanged经度：" + location.getLongitude());
            Log.i(TAG, "onLocationChanged纬度：" + location.getLatitude());
            Log.i(TAG, "onLocationChanged海拔：" + location.getAltitude());
            if (ActivityCompat.checkSelfPermission(LocationActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(LocationActivity.this,
                            Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                // ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                // public void onRequestPermissionsResult(int requestCode, String[] permissions,
                // int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            lm.removeUpdates(locationListener);
        }

        /**
         * GPS状态变化时触发
         */
        public void onStatusChanged(String provider, int status, Bundle extras) {
            switch (status) {
                // GPS状态为可见时
                case LocationProvider.AVAILABLE:
                    Log.i(TAG, "当前GPS状态为可见状态");
                    break;
                // GPS状态为服务区外时
                case LocationProvider.OUT_OF_SERVICE:
                    Log.i(TAG, "当前GPS状态为服务区外状态");
                    break;
                // GPS状态为暂停服务时
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    Log.i(TAG, "当前GPS状态为暂停服务状态");
                    break;
            }
        }

        /**
         * GPS开启时触发
         */
        public void onProviderEnabled(String provider) {
            if (ActivityCompat.checkSelfPermission(LocationActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(LocationActivity.this,
                            Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            Location location = lm.getLastKnownLocation(provider);
        }

        /**
         * GPS禁用时触发
         */
        public void onProviderDisabled(String provider) {}

    };
}
