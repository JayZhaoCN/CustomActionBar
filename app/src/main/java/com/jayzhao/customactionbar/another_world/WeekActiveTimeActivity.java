package com.jayzhao.customactionbar.another_world;

import android.os.Bundle;

import com.jayzhao.customactionbar.MyBaseTitleActivity;
import com.jayzhao.customactionbar.R;

/**
 * Created by Jay on 16-8-22.
 */
public class WeekActiveTimeActivity extends MyBaseTitleActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_week_active_time);
        this.setStyle(STYLE.SINGLE_BACK, R.color.blue_dark);
        this.setTitle("周活跃时长");
    }
}
