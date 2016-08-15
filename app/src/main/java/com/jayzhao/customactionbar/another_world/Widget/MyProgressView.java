package com.jayzhao.customactionbar.another_world.Widget;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.SweepGradient;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.jayzhao.customactionbar.MyUtils;
import com.jayzhao.customactionbar.R;

/**
 * Created by Jay on 16-8-12.
 */
public class MyProgressView extends View {

    private static final String TAG = "MyProgressView";

    private int mStrokeColor = 0;
    private float mStrokeWidth = 0;
    private Context mContext = null;

    private Drawable mCenterDrawable = null;

    private int mWidth = 0;
    private int mHeight = 0;

    private Paint mPaint = null;

    private SweepGradient mSweepGradient = null;

    private int offsetAngle = 0;

    private ValueAnimator mAnimator = null;


    public static final int LOADING = 0;
    public static final int PROGRESS = 1;

    //默认的样式为LOADING
    private int mStyle = LOADING;

    private int mProgressAngle = 90;

    public MyProgressView(Context context) {
        this(context, null);
    }

    public MyProgressView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        obtainAttrs(attrs);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(mStrokeColor);
        mPaint.setStrokeWidth(mStrokeWidth);
        //不太明白这句代码是做什么用的
        //mPaint.setStrokeCap(Paint.Cap.ROUND);

        //不自动Loading
        //startLoading();
    }

    private void obtainAttrs(AttributeSet attrs) {
        TypedArray ta = getResources().obtainAttributes(attrs, R.styleable.ProgressView);
        mStrokeColor = ta.getColor(R.styleable.ProgressView_strokeColor1, getResources().getColor(R.color.blue_light));
        mStrokeWidth = ta.getDimensionPixelOffset(R.styleable.ProgressView_strokeWidth1, 10);
        mCenterDrawable = ta.getDrawable(R.styleable.ProgressView_picture);
        if(mCenterDrawable == null) {
            mCenterDrawable = getResources().getDrawable(R.drawable.icon_scale);
        }
        Log.i(TAG, "" + mStrokeWidth);
        //默认样式为LOADING
        mStyle = ta.getInt(R.styleable.ProgressView_style, LOADING);
        ta.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int desireWidth = getPaddingLeft() + getPaddingRight() + (int)MyUtils.dp2px(mContext, 100f);
        int desireHeight = getPaddingTop() + getPaddingBottom() + (int)MyUtils.dp2px(mContext, 100f);

        mWidth = View.resolveSize(desireWidth, widthMeasureSpec);
        mHeight = View.resolveSize(desireHeight, heightMeasureSpec);
        Log.i(TAG + "_mWidth", "" + mWidth);
        Log.i(TAG + "_mHeight", "" + mHeight);

        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        switch (mStyle) {
            case LOADING:
                initGradient();
                //圆心X，圆心Y，半径，画笔
                canvas.drawCircle(mWidth / 2, mHeight / 2, (mHeight - mStrokeWidth) / 2, mPaint);
                //设置中心图像的位置
                mCenterDrawable.setBounds(mWidth/2 - mCenterDrawable.getIntrinsicWidth()/2,
                        mHeight/2 - mCenterDrawable.getIntrinsicHeight()/2,
                        mWidth/2 + mCenterDrawable.getIntrinsicWidth()/2,
                        mHeight/2 + mCenterDrawable.getIntrinsicHeight()/2);
                mCenterDrawable.draw(canvas);
                //这个方法比较繁琐
                MyUtils.drawBitmapCenter(canvas, mWidth / 2, mHeight / 2, 2.0f, MyUtils.drawable2Bitmap(mCenterDrawable), null);
                break;
            case PROGRESS:
                canvas.drawArc(mStrokeWidth/2, mStrokeWidth/2, mWidth - mStrokeWidth/2, mHeight - mStrokeWidth/2, 0, mProgressAngle, false, mPaint);
                break;
            default:
            break;
        }
    }

    /**
     * 设置当前的Progress
     * @param progress 进度，从0 - 365
     */
    public void setProgress(int progress) {
        if(mStyle == PROGRESS) {
            mProgressAngle = progress;
            Log.i(TAG, "setProgress : " + mProgressAngle);
            invalidate();
        }
    }

    public void startLoading() {
        if(mStyle == LOADING) {
            if(mAnimator == null) {
                mAnimator = ValueAnimator.ofFloat(0, 1);
                mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        offsetAngle = (int)((Float)animation.getAnimatedValue() * 360);
                        Log.i("JayZhao ", "offsetAngle " + offsetAngle);
                        postInvalidateOnAnimation();
                    }
                });
                mAnimator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {

                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        offsetAngle = 0;
                        postInvalidateOnAnimation();
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                mAnimator.setInterpolator(new LinearInterpolator());
                mAnimator.setDuration(5000);
                //mAnimator.setRepeatMode(ValueAnimator.REVERSE);
                mAnimator.setRepeatCount(ValueAnimator.INFINITE);
            }
            if(!mAnimator.isStarted()) {
                mAnimator.start();
            }
        }
    }

    public void stopLoading() {
        if(mStyle == LOADING) {
            if(mAnimator == null) {
                return;
            }
            if(mAnimator.isRunning()) {
                mAnimator.cancel();
                mAnimator = null;
            }
        }
    }

    /**
     * 初始化Gradient
     */
    private void initGradient() {
        //颜色的渐变
        if(mSweepGradient == null) {
            mSweepGradient = new SweepGradient(mWidth / 2, mHeight / 2, getResources().getColor(R.color.transparency), mStrokeColor);
            mPaint.setShader(mSweepGradient);
        }
        Matrix matrix = new Matrix();
        matrix.setRotate(offsetAngle, mWidth/2, mHeight/2);
        mSweepGradient.setLocalMatrix(matrix);
    }
}
