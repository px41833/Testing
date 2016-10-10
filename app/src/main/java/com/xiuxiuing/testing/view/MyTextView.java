package com.xiuxiuing.testing.view;

import com.xiuxiuing.testing.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by wang on 16/9/18.
 */
public class MyTextView extends View {

    String TAG = MyTextView.class.getSimpleName();



    private static final int[] mAttr = {R.attr.testAttr};
    private static final int ATTR_TESTATTR = 0;

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);



        TypedArray ta = context.obtainStyledAttributes(attrs, mAttr);
        int testAttr = ta.getInteger(ATTR_TESTATTR, -1);
        Log.e(TAG, " , textAttr = " + testAttr);
        ta.recycle();
    }

    public void setmAttr(int attr) {
        Log.e(TAG, " , textAttr = " + attr);
    }
}
