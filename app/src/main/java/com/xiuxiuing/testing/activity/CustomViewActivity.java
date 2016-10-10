package com.xiuxiuing.testing.activity;

import com.xiuxiuing.testing.R;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * Created by wang on 16/9/18.
 */
public class CustomViewActivity extends BaseActivity {
    View myTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);
        myTextView = (View) findViewById(R.id.view);

    }
}
