package com.jayzhao.customactionbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jayzhao.customactionbar.Widget.SubItem;

/**
 * Created by hm on 16-4-20.
 */
public class NextActivity extends MyBaseTitleActivity implements View.OnClickListener {

    private SubItem mSubItem = null;
    private Button mStartButton = null;
    private Animation mAnimation = null;
    private View mStartBg = null;
    private TextView mValueAnimation = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStyle(STYLE.BACK_AND_MORE);
        setContentView(R.layout.next_layout);
        setTitle("Hey Young Blood!");

        mSubItem = (SubItem) findViewById(R.id.subItem);
        mSubItem.setOnClickListener(this);

        mStartButton = (Button) findViewById(R.id.start_button);
        mStartBg = findViewById(R.id.start_bg);
        mAnimation = AnimationUtils.loadAnimation(this, R.anim.button_anim);
        mStartBg.startAnimation(mAnimation);
        mValueAnimation = (TextView) findViewById(R.id.valueAnimation);
        mValueAnimation.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.subItem:
                MyUtils.showToast(NextActivity.this, "you have clicked item!");
                break;
            case R.id.valueAnimation:
                startActivity(new Intent(NextActivity.this, AnimationTest.class));
                break;
        }
    }
}
