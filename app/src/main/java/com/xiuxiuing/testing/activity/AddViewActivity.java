package com.xiuxiuing.testing.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.xiuxiuing.testing.R;

/**
 * Created by wang on 16/3/24.
 */
public class AddViewActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // LinearLayout layout = (LinearLayout) findViewById(R.id.layout_main);
        //
        //
        // LayoutInflater inflater = (LayoutInflater)
        // getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // View view = inflater.inflate(R.layout.add_item, null);
        // View view1 = inflater.inflate(R.layout.add_item, null);
        // LinearLayout.LayoutParams lp = new
        // LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
        // LinearLayout.LayoutParams.WRAP_CONTENT);
        // lp.leftMargin = 10;
        // layout.addView(view, lp);
        // layout.addView(view1, lp);
    }
}
