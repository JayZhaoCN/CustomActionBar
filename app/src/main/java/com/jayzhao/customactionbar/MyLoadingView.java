package com.jayzhao.customactionbar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
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

    private String mText = null;
    private int mColor;
    private Context mContext;
    private int mAngle;
    private RectF mRectF;    

    private Paint mPaint;
    private Paint mDonePaint;

    private Animator mAnimator;
    private Animator mSuccessAnimator = null;
    private Animator mDoneAnimator = null;

    private int mStrokeWidth;
    private TextPaint mTextPaint;

    private int mWidth;
    private int mHeight;

    private float mDoneProgress = 0;

    private boolean mSuccess = false;
    private int mSuccessAngle = 0;
    private float points[] = new float[8];

    private static final String TAG = "MyLoadingView";

    private OnLoadingEndListener mListener = null;

    public interface OnLoadingEndListener {
        public void onLoadingEnd();
    }

    public void setOnLoadingEndListener(OnLoadingEndListener l) {
        mListener = l;
    }


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

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        float width = Math.max(w, h);
        points[0] = 101 / 378f * width;
        points[1] = 0.5f * width;

        points[2] = 163 / 378f * width;
        points[3] = 251 / 378f * width;

        points[4] = 149 / 378f * width;
        points[5] = 250 / 378f * width;

        points[6] = 278 / 378f * width;
        points[7] = 122 / 378f * width;
    }

    public void obtainStyledAttr(AttributeSet attrs, int defStyleAttr)   {
        TypedArray ta = mContext.obtainStyledAttributes(attrs, R.styleable.MyLoadingView, 0, defStyleAttr);
        mText = ta.getString(R.styleable.MyLoadingView_text);
        mColor = ta.getColor(R.styleable.MyLoadingView_colorz, Color.WHITE);
        mStrokeWidth = ta.getDimensionPixelOffset(R.styleable.MyLoadingView_stroke, 10);
        ta.recycle();
    }

    public void setSuccess() {
        mSuccess = true;
        mSuccessAngle = mAngle;

        mSuccessAnimator = successAnimLoading();
        mSuccessAnimator.start();

        mDoneAnimator = doneAnimaLoading();
        mDoneAnimator.start();

    }

    public void initView() {
        mRectF = new RectF();

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mStrokeWidth - 7);
        mPaint.setColor(mColor);

        mDonePaint = new Paint();
        mDonePaint.setAntiAlias(true);
        mDonePaint.setStyle(Paint.Style.STROKE);
        mDonePaint.setStrokeWidth(mStrokeWidth - 7);
        mDonePaint.setColor(mColor);
        //mDonePaint.set
        //mDonePaint.setStrokeCap(Paint.Cap.ROUND);
        mTextPaint = new TextPaint();
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
        if(!mSuccess) {
            canvas.drawArc(mRectF, 90 + mAngle, 270, false, mPaint);
        } else {
            canvas.drawArc(mRectF, 90 + mAngle, 270 + mSuccessAngle, false, mPaint);
        }

        if (mDoneProgress > 0) {
            if (mDoneProgress < 1 / 3f) {
                float x = points[0] + (points[2] - points[0]) * mDoneProgress*3;
                float y = points[1] + (points[3] - points[1]) * mDoneProgress*3;
                canvas.drawLine(points[0], points[1], x, y, mDonePaint);
            } else {
                float x = points[4] + (points[6] - points[4]) * mDoneProgress;
                float y = points[5] + (points[7] - points[5]) * mDoneProgress;
                canvas.drawLine(points[0], points[1], points[2], points[3], mDonePaint);
                canvas.drawLine(points[4], points[5], x, y, mDonePaint);
            }
        }
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
            mAnimator.cancel();
            mAnimator = null;
        }
        if(mSuccessAnimator != null && mSuccessAnimator.isRunning()) {
            mAnimator.cancel();
            mAnimator = null;
        }
        if(mDoneAnimator != null && mDoneAnimator.isRunning()) {
            mDoneAnimator.cancel();
            mAnimator = null;
        }
    }

    private Animator successAnimLoading() {
        ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float success = (float) animation.getAnimatedValue();
                mSuccessAngle = (int) (success * 90);
                postInvalidateOnAnimation();
            }
        });
        animator.setDuration(1000);
        return animator;
    }

    private Animator doneAnimaLoading() {
        ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mDoneProgress = (float) animation.getAnimatedValue();
                Log.e(TAG, mDoneProgress + "");
                postInvalidateOnAnimation();
            }
        });

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mListener.onLoadingEnd();
            }
        });
        animator.setDuration(1300);
        return animator;
    }


    private Animator animLoading() {
        ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float rotate = (float) animation.getAnimatedValue();
                mAngle = (int) (rotate*360);
                Log.e(TAG, mAngle + "");
                postInvalidateOnAnimation();  //Cause an invalidate to happen on the next animation time step
            }
        });
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setDuration(1500);
        animator.setInterpolator(new LinearInterpolator());
        return animator;
    }
}
