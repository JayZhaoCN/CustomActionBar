package com.jayzhao.customactionbar.another_world.Widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * Created by Jay on 16-8-10.
 */
public class MyListView extends ListView {
    private float mDownX = 0;
    private float mDownY = 0;
    private static final String TAG = "MyListView";

    public MyListView(Context context) {
        super(context);
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



}
