package com.xiuxiuing.testing.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.xiuxiuing.testing.R;
import com.xiuxiuing.testing.listener.ClsaaSpotListener;

import net.youmi.android.normal.spot.SpotManager;

/**
 * Created by wang on 16/11/29.
 */
public class AdYiMiActivity extends BaseActivity implements View.OnClickListener {
    Button spotBtn;
    Button slideBtn;
    Button bannerBtn;
    Button nativespotBtn;
    Button videoadBtn;
    Button navideoadBtn;

    Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_youmi);
        spotBtn = (Button) findViewById(R.id.btn_spot);
        slideBtn = (Button) findViewById(R.id.btn_slide);
        bannerBtn = (Button) findViewById(R.id.btn_banner);
        nativespotBtn = (Button) findViewById(R.id.btn_nativespot);
        videoadBtn = (Button) findViewById(R.id.btn_videoad);
        navideoadBtn = (Button) findViewById(R.id.btn_nativevideoad);

        spotBtn.setOnClickListener(this);

        setupSpotAd();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_spot:
                SpotManager.getInstance(mContext).showSpot(mContext, new ClsaaSpotListener());
                break;
            case R.id.btn_slide:
                break;
            case R.id.btn_banner:
                break;
            case R.id.btn_nativespot:
                break;
            case R.id.btn_videoad:
                break;
            case R.id.btn_nativevideoad:
                break;
            default:
                break;

        }
    }

    /**
     * 设置插屏广告
     */
    private void setupSpotAd() {
        // 设置插屏图片类型，默认竖图
        // // 横图
        // SpotManager.getInstance(mContext).setImageType(SpotManager
        // .IMAGE_TYPE_HORIZONTAL);
        // 竖图
        SpotManager.getInstance(mContext).setImageType(SpotManager.IMAGE_TYPE_VERTICAL);

        // 设置动画类型，默认高级动画
        // // 无动画
        // SpotManager.getInstance(mContext).setAnimationType(SpotManager
        // .ANIMATION_TYPE_NONE);
        // // 简单动画
        // SpotManager.getInstance(mContext).setAnimationType(SpotManager
        // .ANIMATION_TYPE_SIMPLE);
        // 高级动画
        SpotManager.getInstance(mContext).setAnimationType(SpotManager.ANIMATION_TYPE_NONE);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SpotManager.getInstance(mContext).onDestroy();
    }
}
