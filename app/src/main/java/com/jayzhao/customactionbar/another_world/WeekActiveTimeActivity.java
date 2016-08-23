package com.jayzhao.customactionbar.another_world;

import android.os.Bundle;

import com.jayzhao.customactionbar.MyBaseTitleActivity;
import com.jayzhao.customactionbar.R;
import com.jayzhao.customactionbar.Widget.WeekActiveCircleView;
import com.jayzhao.customactionbar.Widget.WeekActiveTimeView;

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

        WeekActiveCircleView circleView = (WeekActiveCircleView) findViewById(R.id.circle_view);
        WeekActiveTimeView activeTimeView = (WeekActiveTimeView) findViewById(R.id.active_time_view);

        circleView.startLoading();
        activeTimeView.startLoading();
    }
}
