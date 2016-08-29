package com.jayzhao.customactionbar.another_world.Widget;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.jayzhao.customactionbar.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jay on 16-8-26.
 */
public class CircleLoadingView extends View {
    private static final String TAG = "CircleLoadingView";

    private int mHeight = 0;
    private int mWidth = 0;
    private int mRadius = 0;
    private int mPointRadius = 5;

    private Paint mPaint = null;
    private Resources mResources = null;
    private int mStartColor = 0;
    private int mEndColor = 0;
    private List<Point> mPointList = null;

    private SweepGradient mSweepGradient = null;
    private int mSweepAngle = 0;
    private Matrix mMatrix = null;

    private ValueAnimator mAnimator = null;

    private OnLoadingDoneListener mListener = null;

    public CircleLoadingView(Context context) {
        this(context, null);
    }

    public CircleLoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mResources = context.getResources();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mStartColor = mResources.getColor(R.color.blue_light);
        mEndColor = mResources.getColor(R.color.transparency);
        mPaint.setColor(mStartColor);
        mPaint.setStyle(Paint.Style.FILL);
        mPointList = new ArrayList<>();

        mAnimator = ValueAnimator.ofInt(0, 360);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mSweepAngle = (Integer) animation.getAnimatedValue();
                postInvalidateOnAnimation();
            }
        });

        /**
         * onAnimationEnd表示动画完成一定会调用
         * onAnimationCancel在调用了animation.cancel()方法后会调用，之后也会调用onAnimationEnd()
         */
        mAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                Log.i(TAG, "onAnimationStart");
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.i(TAG, "onAnimationEnd");
                mListener.onLoadingDone(CircleLoadingView.this);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                Log.i(TAG, "onAnimationCancel");
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                Log.i(TAG, "onAnimationRepeat");
            }
        });

        mAnimator.setInterpolator(new LinearInterpolator());
        mAnimator.setRepeatCount(5);
        mAnimator.setDuration(500);
        mAnimator.start();
    }

    public void setListener(OnLoadingDoneListener listener) {
        mListener = listener;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHeight = h;
        mWidth = w;
        mRadius = Math.min(mHeight, mWidth) / 2 - 5;

        mPointList.add(new Point(mRadius, 0));
        mPointList.add(new Point((int) (mRadius * (1 + Math.sin(Math.PI / 4))), (int) (mRadius * (1 - Math.sin(Math.PI / 4)))));
        mPointList.add(new Point(mRadius * 2, mRadius));
        mPointList.add(new Point((int) (mRadius * (1 + Math.sin(Math.PI / 4))), (int) (mRadius * (1 + Math.sin(Math.PI / 4)))));
        mPointList.add(new Point(mRadius, mRadius * 2));
        mPointList.add(new Point((int) (mRadius * (1 - Math.sin(Math.PI / 4))), (int) (mRadius * (1 + Math.sin(Math.PI / 4)))));
        mPointList.add(new Point(0, mRadius));
        mPointList.add(new Point((int) (mRadius * (1 - Math.sin(Math.PI / 4))), (int) (mRadius * (1 - Math.sin(Math.PI / 4)))));

        for(Point point : mPointList) {
            point.x += 5;
            point.y += 5;
        }
    }

    public void initGradient() {
        if(mSweepGradient == null) {
            mSweepGradient = new SweepGradient(mRadius + 5, mRadius + 5, mStartColor, mEndColor);
            mPaint.setShader(mSweepGradient);
        }
        if(mMatrix == null) {
            mMatrix = new Matrix();
        }
        mMatrix.setRotate(mSweepAngle, mRadius + 5, mRadius + 5);
        mSweepGradient.setLocalMatrix(mMatrix);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initGradient();
        for(Point point : mPointList) {
            canvas.drawCircle(point.x, point.y, mPointRadius, mPaint);
        }
    }
}
