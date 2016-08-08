package com.jayzhao.customactionbar;

import android.graphics.Color;
import android.os.Bundle;

import com.jayzhao.customactionbar.Widget.SportHeartRateView;

/**
 * Created by Jay on 16-7-29.
 */
public class TestViewActivity extends MyBaseTitleActivity {
    private SportHeartRateView mSportHeartRateView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE.SINGLE_BACK);
        setTitle("HeartRateView");
        setContentView(R.layout.activity_heart_rate);
        initViews();
    }

    private void initViews() {
        mSportHeartRateView = (SportHeartRateView) findViewById(R.id.shrv);
        SportHeartRateView.Builder builder = new SportHeartRateView.Builder(mSportHeartRateView);
        builder.scaleTextSize(13f)
                .bezierLineColor(Color.parseColor("#E25D4D"))
                .heartBmp(R.mipmap.icon_heart)
                .canTouch(true)
                .maxXScale(300)
                .minXScale(100)
                .textXScaleColor(Color.parseColor("#66000000"))
                .lableTextSize(9.3f)
                .scaleTextSize(9.3f)
                .textLableColor(Color.parseColor("#66000000"))
                .build();
        mSportHeartRateView.setBuilder(builder);
        mSportHeartRateView.setHeartRateValue(176);
    }
}
