package com.jayzhao.customactionbar;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by hm on 16-4-1.
 * 下一阶段准备添加加载成功的动画
 */

public class MyLoadingView extends View {

    //just for test branch
    //just for test branch 2
    //just for test branch 3
    //just for test branch 4
    //just for test branch 5dddd    

    private String mText = null;
    private int mColor;
    private Context mContext;
    private int angle;
    private RectF mRectF;    

    private Paint mPaint;

    private Animator mAnimator;

    private int mStrokeWidth;
    private TextPaint mTextPaint;

    private int mWidth;
    private int mHeight;

    private static final String TAG = "MyLoadingView";

    public MyLoadingView(Context context) {
        this(context, null);
    }

    public MyLoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = getContext();
        obtainStyledAttr(attrs, defStyleAttr);
        initView();

    }

    public void obtainStyledAttr(AttributeSet attrs, int defStyleAttr) {
        TypedArray ta = mContext.obtainStyledAttributes(attrs, R.styleable.MyLoadingView, 0, defStyleAttr);
        mText = ta.getString(R.styleable.MyLoadingView_text);
        mColor = ta.getColor(R.styleable.MyLoadingView_colorz, Color.WHITE);
        mStrokeWidth = ta.getDimensionPixelOffset(R.styleable.MyLoadingView_stroke, 10);
        ta.recycle();
    }

    public void initView() {

        mRectF = new RectF();

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setColor(mColor);

        mTextPaint = new TextPaint() ;
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(mColor);
        startLoading();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mWidth = View.resolveSize(300, widthMeasureSpec);
        mHeight = View.resolveSize(300, heightMeasureSpec);
        mRectF.set(mStrokeWidth / 2, mStrokeWidth / 2, mWidth - mStrokeWidth / 2, mHeight - mStrokeWidth / 2);
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawArc(mRectF, 90 + angle, 270, false, mPaint);

    }

    public void startLoading() {
        if (mAnimator == null) {
            mAnimator = animLoading();
        }

        if (!mAnimator.isStarted()) {
            mAnimator.start();
        }
    }

    public void stopLoading() {
        if (mAnimator != null && mAnimator.isRunning()) {
            mAnimator.end();
            mAnimator.cancel();
            mAnimator = null;
        }
    }

    private Animator animLoading() {
        ValueAnimator animator = ValueAnimator.ofFloat(0, 1);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float rotate = (float) animation.getAnimatedValue();
                angle = (int) (rotate*360);
                Log.e(TAG, angle + "");
                postInvalidateOnAnimation();
            }
        });
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setDuration(1500);
        animator.setInterpolator(new LinearInterpolator());
        return animator;
    }
}
