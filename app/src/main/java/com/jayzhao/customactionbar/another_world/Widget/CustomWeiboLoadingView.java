package com.jayzhao.customactionbar.another_world.Widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
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
 * Created by Jay on 16-8-17.
 * 仿微博的LoadingView
 */
public class CustomWeiboLoadingView extends View {
    private static final String TAG = "CustomWeiboLoadingView";

    private int mStartColor = 0;
    private int mEndColor = 0;

    private Context mContext = null;
    private Resources mResources = null;

    private int mWidth = 0;
    private int mHeight = 0;

    private int r1 = 200;
    private int r2 = 100;

    private int mStrokeWidth = 0;

    private Paint mPaint = null;

    private List<Point> mPoints = null;

    private SweepGradient mSweep = null;

    private int mOffsetAngle = 0;

    private ValueAnimator mAnimator = null;

    private Matrix mMatrix = null;

    private OnLoadingCompleteListener mListener = null;

    public CustomWeiboLoadingView(Context context) {
        this(context, null);
    }

    public CustomWeiboLoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomWeiboLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mResources = getResources();
        mContext = context;
        obtainAttrs(context, attrs);
        initData();
        startLoading();
    }

    private void obtainAttrs(Context context, AttributeSet attrs) {
        TypedArray ta = getResources().obtainAttributes(attrs, R.styleable.CustomWeiboLoadingView);
        mStartColor = ta.getColor(R.styleable.CustomWeiboLoadingView_startColor, mResources.getColor(R.color.transparency));
        mEndColor = ta.getColor(R.styleable.CustomWeiboLoadingView_endColor, mResources.getColor(R.color.bg_color_red));
        mStrokeWidth = ta.getDimensionPixelOffset(R.styleable.CustomWeiboLoadingView_strokeWidth2, 10);
        r1 = ta.getDimensionPixelOffset(R.styleable.CustomWeiboLoadingView_radius1, 200);
        r2 = ta.getDimensionPixelOffset(R.styleable.CustomWeiboLoadingView_radius2, 100);
        Log.i(TAG, mStrokeWidth + "");
        ta.recycle();
    }

    private void initData() {
        mPoints = new ArrayList<>();
        mPoints.add(new Point(r1, 0));
        mPoints.add(new Point(r1, r1 - r2));

        mPoints.add(new Point(r1/2, (int)((1 - Math.cos(Math.toRadians(30))) * r1)));
        mPoints.add(new Point(r1 - r2/2, (int)(r1 - Math.cos(Math.toRadians(30)) * r2)));

        mPoints.add(new Point((int)(r1 * (1 - Math.cos(Math.toRadians(30)))), r1/2));
        mPoints.add(new Point((int)(r1 - r2 * Math.cos(Math.toRadians(30))), r1 - r2/2));

        mPoints.add(new Point(0, r1));
        mPoints.add(new Point(r1 - r2, r1));

        mPoints.add(new Point((int)(r1 * (1 - Math.cos(Math.toRadians(30)))), r1 + r1/2));
        mPoints.add(new Point((int)(r1 - r2 * Math.cos(Math.toRadians(30))), r1 + r2/2));

        mPoints.add(new Point(r1/2, (int)((1 + Math.cos(Math.toRadians(30))) * r1)));
        mPoints.add(new Point(r1 - r2/2, (int)(r1 + Math.cos(Math.toRadians(30)) * r2)));

        mPoints.add(new Point(r1, r1 * 2));
        mPoints.add(new Point(r1, r1 + r2));

        mPoints.add(new Point(r1/2 + r1, (int)((1 + Math.cos(Math.toRadians(30))) * r1)));
        mPoints.add(new Point(r1 + r2/2, (int)(r1 + Math.cos(Math.toRadians(30)) * r2)));

        mPoints.add(new Point((int)(r1 * (1 + Math.cos(Math.toRadians(30)))), r1 + r1/2));
        mPoints.add(new Point((int)(r1 + r2 * Math.cos(Math.toRadians(30))), r1 + r2/2));

        mPoints.add(new Point(r1 * 2, r1));
        mPoints.add(new Point(r1 + r2, r1));

        mPoints.add(new Point((int)(r1 * (1 + Math.cos(Math.toRadians(30)))), r1/2));
        mPoints.add(new Point((int)(r1 + r2 * Math.cos(Math.toRadians(30))), r1 - r2/2));

        mPoints.add(new Point(r1/2 + r1, (int)((1 - Math.cos(Math.toRadians(30))) * r1)));
        mPoints.add(new Point(r1 + r2/2, (int)(r1 - Math.cos(Math.toRadians(30)) * r2)));

        for(Point point : mPoints) {
            point.x += mStrokeWidth/2;
            point.y += mStrokeWidth/2;
        }

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setColor(mResources.getColor(R.color.bg_color_red));
        mPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    public void setListener(OnLoadingCompleteListener listener) {
        this.mListener = listener;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int desireWidth = r1 * 2 + mStrokeWidth;
        int desireHeight = r1 * 2 + mStrokeWidth;
        mWidth = View.resolveSize(desireWidth, widthMeasureSpec) + mStrokeWidth;
        mHeight = View.resolveSize(desireHeight, heightMeasureSpec) + mStrokeWidth;

        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initGradient();
        canvas.drawLine(mPoints.get(0).x, mPoints.get(0).y, mPoints.get(1).x, mPoints.get(1).y, mPaint);
        canvas.drawLine(mPoints.get(2).x, mPoints.get(2).y, mPoints.get(3).x, mPoints.get(3).y, mPaint);
        canvas.drawLine(mPoints.get(4).x, mPoints.get(4).y, mPoints.get(5).x, mPoints.get(5).y, mPaint);
        canvas.drawLine(mPoints.get(6).x, mPoints.get(6).y, mPoints.get(7).x, mPoints.get(7).y, mPaint);
        canvas.drawLine(mPoints.get(8).x, mPoints.get(8).y, mPoints.get(9).x, mPoints.get(9).y, mPaint);
        canvas.drawLine(mPoints.get(10).x, mPoints.get(10).y, mPoints.get(11).x, mPoints.get(11).y, mPaint);
        canvas.drawLine(mPoints.get(12).x, mPoints.get(12).y, mPoints.get(13).x, mPoints.get(13).y, mPaint);
        canvas.drawLine(mPoints.get(14).x, mPoints.get(14).y, mPoints.get(15).x, mPoints.get(15).y, mPaint);
        canvas.drawLine(mPoints.get(16).x, mPoints.get(16).y, mPoints.get(17).x, mPoints.get(17).y, mPaint);
        canvas.drawLine(mPoints.get(18).x, mPoints.get(18).y, mPoints.get(19).x, mPoints.get(19).y, mPaint);
        canvas.drawLine(mPoints.get(20).x, mPoints.get(20).y, mPoints.get(21).x, mPoints.get(21).y, mPaint);
        canvas.drawLine(mPoints.get(22).x, mPoints.get(22).y, mPoints.get(23).x, mPoints.get(23).y, mPaint);
    }

    private void initGradient() {
        if(mSweep == null) {
            mSweep = new SweepGradient((r1 * 2 + mStrokeWidth)/2,(r1 * 2 + mStrokeWidth)/2, mStartColor, mEndColor);
            mPaint.setShader(mSweep);
        }

        if(mMatrix == null) {
            mMatrix = new Matrix();
        }
        mMatrix.setRotate(mOffsetAngle, (r1 * 2 + mStrokeWidth)/2,(r1 * 2 + mStrokeWidth)/2);
        mSweep.setLocalMatrix(mMatrix);
    }

    public void startLoading() {
        if(mAnimator == null) {
            mAnimator = ValueAnimator.ofInt(0, 360);
            mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mOffsetAngle = (int) animation.getAnimatedValue();
                    postInvalidateOnAnimation();
                }
            });
            mAnimator.setInterpolator(new LinearInterpolator());
            mAnimator.setDuration(500);
            mAnimator.setRepeatCount(5);

            mAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    mListener.onComplete(CustomWeiboLoadingView.this);
                }
            });
        }
        if(!mAnimator.isRunning()) {
            mAnimator.start();
        }
    }

    public void stopLoading() {
        if(mAnimator != null) {
            if(mAnimator.isRunning()) {
                mAnimator.cancel();
                mAnimator = null;
            }
        }
    }

    public interface OnLoadingCompleteListener {
        void onComplete(View view);
    }
}
