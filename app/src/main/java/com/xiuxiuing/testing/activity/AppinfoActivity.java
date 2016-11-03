package com.xiuxiuing.testing.activity;

import com.xiuxiuing.testing.R;
import com.xiuxiuing.testing.adapter.AppinfoRecyclerAdapter;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by wang on 16/11/2.
 */
public class AppinfoActivity extends BaseActivity {
    private RecyclerView mRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appinfo);
        setTitle(R.string.title_android_appinfo);
        mRecyclerView = (RecyclerView) findViewById(R.id.appinfo_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
        mRecyclerView.setAdapter(new AppinfoRecyclerAdapter(this));
    }
}
