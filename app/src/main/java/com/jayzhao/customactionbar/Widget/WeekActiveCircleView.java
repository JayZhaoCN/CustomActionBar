package com.jayzhao.customactionbar.Widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.jayzhao.customactionbar.R;

/**
 * Created by Jay on 16-8-22.
 */
public class WeekActiveCircleView extends View {
    private static final String TAG = "WeekActiveCircleView";

    private Resources mResources = null;
    private Context mContext = null;

    private int mWidth = 0;
    private int mHeight = 0;
    private int mProgress = 0;

    private Paint mProgressPaint = null;
    private Paint mCirclePaint = null;

    private RectF mRect = null;

    private int mSweepAngle = 1;

    private ValueAnimator mAnimator = null;

    public WeekActiveCircleView(Context context) {
        this(context, null);
    }

    public WeekActiveCircleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WeekActiveCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        Log.i(TAG, "构造方法");
        init();
    }

    private void init() {
        Log.i(TAG, "init");
        mResources = getResources();
        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setColor(mResources.getColor(R.color.black_20_percent));
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setStrokeCap(Paint.Cap.ROUND);
        //暂时写死
        mCirclePaint.setStrokeWidth(10);

        mProgressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mProgressPaint.setColor(mResources.getColor(R.color.blue_light));
        mProgressPaint.setStyle(Paint.Style.STROKE);
        mProgressPaint.setStrokeCap(Paint.Cap.ROUND);
        //暂时写死
        mProgressPaint.setStrokeWidth(10);

        startLoading();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.i(TAG, "onSizeChanged");
        mHeight = h;
        mWidth = w;

        //这里是为什么
        mRect = new RectF(8, 8, mWidth - 8, mWidth - 8);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i(TAG, "onDraw");

        canvas.drawArc(mRect, 150, 240, false, mCirclePaint);
        //真是奇了怪了
        canvas.drawArc(mRect, 150, mSweepAngle + 1, false, mProgressPaint);
        Log.i(TAG ,"sweepAngle: " + mSweepAngle);
    }

    private void startLoading() {
        Log.i(TAG, "startLoading");
        if(mAnimator == null) {
            mAnimator = ValueAnimator.ofFloat(0, 1);
            mAnimator.setDuration(800);
            mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mSweepAngle = (int) (100 * (float)animation.getAnimatedValue());
                    if(mSweepAngle != 0) {
                        postInvalidateOnAnimation();
                    }
                }
            });
        }
        if(!mAnimator.isRunning()) {
            mAnimator.start();
        }
    }
}
