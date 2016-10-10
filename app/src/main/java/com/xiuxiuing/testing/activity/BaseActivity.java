package com.xiuxiuing.testing.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.xiuxiuing.testing.R;

/**
 * Created by wang on 16/5/31.
 */
public class BaseActivity extends AppCompatActivity {
    ActionBar bar;
    FrameLayout frameLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_base);

        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        frameLayout = (FrameLayout) findViewById(R.id.layout_content);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        bar = getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    // public void setBarTitle(int id) {
    // bar.setTitle(id);
    // }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        frameLayout.removeAllViews();
        View.inflate(this, layoutResID, frameLayout);
        onContentChanged();
    }

    @Override
    public void setContentView(View view) {
        frameLayout.removeAllViews();
        frameLayout.addView(view);
        onContentChanged();
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        frameLayout.removeAllViews();
        frameLayout.addView(view, params);
        onContentChanged();
    }
}
