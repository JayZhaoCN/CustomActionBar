package com.jayzhao.customactionbar.another_world;

import android.os.Bundle;

import com.jayzhao.customactionbar.MyBaseTitleActivity;
import com.jayzhao.customactionbar.R;

/**
 * Created by Jay on 16-9-5.
 */
public class CustomViewActivity extends MyBaseTitleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        setStyle(STYLE.BACK_AND_MORE);
        setTitle("自定义View");
    }
}
