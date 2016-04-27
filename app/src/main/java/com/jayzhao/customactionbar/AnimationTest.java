package com.jayzhao.customactionbar;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

/**
 * Created by hm on 16-4-25.
 */
public class AnimationTest extends MyBaseTitleActivity implements View.OnClickListener {

    private TextView mScaleAnimation = null;
    private TextView mAlphaAnimation = null;
    private TextView mRotateAnimation = null;
    private TextView mTranslateAnimation = null;
    private TextView mAnimationSet = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.animation_layout);
        this.setStyle(STYLE.BACK_AND_MORE);
        this.setTitle("View Animation");

        mScaleAnimation = (TextView) findViewById(R.id.ScaleAnimation);
        mScaleAnimation.setOnClickListener(this);

        mAlphaAnimation = (TextView) findViewById(R.id.AlphaAnimation);
        mAlphaAnimation.setOnClickListener(this);

        mRotateAnimation = (TextView) findViewById(R.id.RotateAnimation);
        mRotateAnimation.setOnClickListener(this);

        mTranslateAnimation = (TextView) findViewById(R.id.TranslateAnimation);
        mTranslateAnimation.setOnClickListener(this);

        mAnimationSet = (TextView) findViewById(R.id.AnimationSet);
        mAnimationSet.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ScaleAnimation:
                //两种得到Animation的方法，从XML文件中读取，和直接在代码中新建

                /*ScaleAnimation scale = new ScaleAnimation(1, 5, 1, 5);
                scale.setDuration(3000);
                scale.setRepeatMode(Animation.REVERSE);
                scale.setRepeatCount(3);
                mScaleAnimation.startAnimation(scale);*/

                Animation scale = AnimationUtils.loadAnimation(AnimationTest.this, R.anim.scale_animation);
                mScaleAnimation.startAnimation(scale);
                break;
            case R.id.AlphaAnimation:
                AlphaAnimation alpha = new AlphaAnimation(1f, 0f);
                alpha.setDuration(3000);
                alpha.setRepeatCount(Animation.REVERSE);
                alpha.setRepeatCount(3);
                mAlphaAnimation.startAnimation(alpha);

                /*Animation alpha = AnimationUtils.loadAnimation(AnimationTest.this, R.anim.alpha_animation);
                mAlphaAnimation.startAnimation(alpha);*/
                break;
            case R.id.RotateAnimation:
                /*RotateAnimation rotate = new RotateAnimation(0, 90);
                rotate.setDuration(3000);
                rotate.setRepeatCount(Animation.RESTART);
                rotate.setRepeatCount(3);
                mRotateAnimation.startAnimation(rotate);*/

                Animation rotate = AnimationUtils.loadAnimation(AnimationTest.this, R.anim.rotate_animation);
                mRotateAnimation.startAnimation(rotate);
                break;
            case R.id.TranslateAnimation:
                TranslateAnimation translate = new TranslateAnimation(0, 400, 0, 400);
                translate.setRepeatCount(3);
                translate.setRepeatMode(Animation.REVERSE);
                translate.setDuration(3000);
                mTranslateAnimation.startAnimation(translate);

                /*Animation translate = AnimationUtils.loadAnimation(AnimationTest.this, R.anim.translate_animation);
                mTranslateAnimation.startAnimation(translate);*/
                break;
            case R.id.AnimationSet:
                /*Animation animationSet = AnimationUtils.loadAnimation(this, R.anim.animationset);
                mAnimationSet.startAnimation(animationSet);*/

                AlphaAnimation alpha1 = new AlphaAnimation(1f, 0f);
                alpha1.setDuration(3000);
                alpha1.setRepeatCount(Animation.REVERSE);
                alpha1.setRepeatCount(3);

                TranslateAnimation translate1 = new TranslateAnimation(0, 400, 0, 400);
                translate1.setRepeatCount(3);
                translate1.setRepeatMode(Animation.REVERSE);
                translate1.setDuration(3000);


                AnimationSet animationSet = new AnimationSet(true);
                animationSet.addAnimation(alpha1);
                animationSet.addAnimation(translate1);
                mAnimationSet.startAnimation(animationSet);
                break;
        }
    }
}
