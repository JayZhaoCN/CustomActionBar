package com.jayzhao.customactionbar;

import android.os.Bundle;

/**
 * Created by Administrator on 2016/4/7.
 */
public class JayActivity extends MyBaseTitleActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE.FULL_SCREEN);

        this.setContentView(R.layout.jay_layout);

    }
}
