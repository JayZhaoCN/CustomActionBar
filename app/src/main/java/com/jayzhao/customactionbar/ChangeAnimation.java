package com.jayzhao.customactionbar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.FragmentTransaction;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Toast;

/**
 * Created by hm on 16-4-18.
 */
public class ChangeAnimation extends MyBaseTitleActivity {
    private Fragment1 mFragment1 = null;
    private Fragment2 mFragment2 = null;

    private ValueAnimator mAnimator12 = null;
    private ValueAnimator mAnimator21 = null;

    private boolean mFlag = true;

    private Point mScreenSize = new Point();

    private double mMaxRadius = 0;

    private AnimatorUpdateListener mListener = null;
    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setStyle(STYLE.SINGLE_BACK);
        setContentView(R.layout.change_animation);
        setTitle("MyChangeAnimation");

        Display display = getWindowManager().getDefaultDisplay();
        display.getSize(mScreenSize);

        mMaxRadius = Math.sqrt(mScreenSize.x * mScreenSize.x + mScreenSize.y * mScreenSize.y);

        findViewById(R.id.change).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index ++;
                if(index == 4) {
                    index = 0;
                }
                if (mFlag) {
                    mAnimator12.start();
                    mFlag = false;
                } else {
                    mAnimator21.start();
                    mFlag = true;
                }
            }
        });


        mListener = new AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                double index = value/100d;
                int currentRadius = (int) (index * mMaxRadius);
                mFragment1.getRoot().setOvalMask(mScreenSize.x, 0, currentRadius);
            }
        };

        mAnimator12 = ValueAnimator.ofInt(0, 100);
        mAnimator21 = ValueAnimator.ofInt(100, 0);
        mAnimator21.setDuration(1500);
        mAnimator12.setDuration(1500);
        mAnimator12.addUpdateListener(mListener);
        mAnimator21.addUpdateListener(mListener);
        initFragment02();
        initFragment01();
    }

    private void initFragment01() {
        if(mFragment1 == null) {
            mFragment1 = new Fragment1();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.add(R.id.fragment_container, mFragment1);
            ft.commit();
        }
    }

    private void initFragment02() {
        if(mFragment2 == null) {
            mFragment2 = new Fragment2();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_container, mFragment2);
            ft.commit();
        }
    }




}
