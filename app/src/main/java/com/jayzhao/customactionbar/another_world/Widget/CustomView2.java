package com.jayzhao.customactionbar.another_world.Widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.jayzhao.customactionbar.R;

/**
 * Created by Jay on 16-9-5.
 */
public class CustomView2 extends View {
    private static final String TAG = "CustomView2";
    private Paint mPaint = null;
    private Path mPath = null;

    private int mWaveHeight = 40;
    private int mWaveLocationY = 400;
    private int mWaveNumber = 3;
    private int mWaveWidth = 0;
    private int dx = 0;

    private ValueAnimator mAnimator = null;

    public CustomView2(Context context) {
        this(context, null);
    }

    public CustomView2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(2);
        mPaint.setColor(getResources().getColor(R.color.blue_light));

        mPath = new Path();

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.i(TAG, "onSizeChanged");


        mAnimator = ValueAnimator.ofInt(-w / mWaveNumber, 0);
        mAnimator.setInterpolator(new LinearInterpolator());
        mAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mAnimator.setDuration(700);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                dx = (int) animation.getAnimatedValue();
                Log.i(TAG, dx + "");
                postInvalidate();
            }
        });

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();
        int singleWave = getWidth() / (mWaveNumber * 4);
        mWaveWidth = getWidth() / mWaveNumber;
        mPath.moveTo(-mWaveWidth + dx, mWaveLocationY);
        for(int i=-1; i<mWaveNumber + 1; i++) {
            mPath.rQuadTo(singleWave, - mWaveHeight, singleWave * 2, 0);
            mPath.rQuadTo(singleWave, mWaveHeight, singleWave * 2, 0);
        }
        mPath.lineTo(getWidth() + mWaveWidth, getHeight());
        mPath.lineTo(-mWaveWidth, getHeight());
        mPath.close();


        canvas.drawPath(mPath, mPaint);
        if(!mAnimator.isStarted()) {
           mAnimator.start();
        }
    }
}
