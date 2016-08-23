package com.jayzhao.customactionbar.Widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.jayzhao.customactionbar.MyUtils;
import com.jayzhao.customactionbar.R;

/**
 * Created by Jay on 16-8-22.
 * 周活跃弧形控件
 */
public class WeekActiveCircleView extends View {
    private static final String TAG = "WeekActiveCircleView";

    private Resources mResources = null;
    private Context mContext = null;

    /**
     * 控件宽度
     */
    private int mWidth = 0;
    /**
     * 控件高度
     */
    private int mHeight = 0;

    /**
     * 转过的角度
     */
    private int mSweepAngle = 0;

    /**
     * 画笔
     */
    private Paint mProgressPaint = null;
    private Paint mCirclePaint = null;
    private Paint mCenterTextPaint = null;
    private Paint mUnitTextPaint = null;

    /**
     * 圆弧所在圆的外切矩形
     */
    private RectF mRect = null;

    private ValueAnimator mAnimator = null;

    /**
     * 显示在中间的刻度
     */
    private String mCenterText = null;
    /**
     * 单位
     */
    private String mUnitText = null;

    private Paint.FontMetricsInt mCenterMetrics = null;
    private Paint.FontMetricsInt mUnitMetrics = null;

    private int mCenterTextBaseLine = 0;

    private int mCenterTextWidth = 0;
    private int mCenterTextHeight = 0;

    private int mSkorkeWidth = 0;

    /**
     * 周活跃达标分钟
     */
    private int mReachGoalTime = 420;

    /**
     * 实际的每周活跃时间
     */
    private int mActiveTime = 100;


    public WeekActiveCircleView(Context context) {
        this(context, null);
    }

    public WeekActiveCircleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WeekActiveCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        mResources = getResources();
        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setColor(mResources.getColor(R.color.black_20_percent));
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setStrokeCap(Paint.Cap.ROUND);
        //暂时写死，后面改成从Attr中读取
        mSkorkeWidth = 10;
        mCirclePaint.setStrokeWidth(mSkorkeWidth);

        mProgressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mProgressPaint.setColor(mResources.getColor(R.color.blue_light));
        mProgressPaint.setStyle(Paint.Style.STROKE);
        mProgressPaint.setStrokeCap(Paint.Cap.ROUND);
        //暂时写死，后面改成从Attr中读取
        mProgressPaint.setStrokeWidth(mSkorkeWidth);

        mCenterTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCenterTextPaint.setColor(mResources.getColor(R.color.black_40_percent));
        //字号暂时写死
        mCenterTextPaint.setTextSize(MyUtils.sp2px(mContext, 35f));
        mCenterTextPaint.setTextAlign(Paint.Align.CENTER);
        mCenterMetrics = mCenterTextPaint.getFontMetricsInt();
        //暂时写死
        mCenterText = "120";

        Rect rect = new Rect();
        mCenterTextPaint.getTextBounds(mCenterText, 0, mCenterText.length(), rect);
        mCenterTextWidth = rect.width();
        mCenterTextHeight = rect.height();

        mUnitTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mUnitTextPaint.setColor(mResources.getColor(R.color.black_40_percent));
        //字号暂时写死
        mUnitTextPaint.setTextSize(MyUtils.sp2px(mContext, 15f));
        mUnitTextPaint.setTextAlign(Paint.Align.LEFT);
        mUnitMetrics = mUnitTextPaint.getFontMetricsInt();
        //单位暂时写死
        mUnitText = "分钟";

        //开始加载动画
        startLoading();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHeight = h;
        mWidth = w;

        //不太明白为什么需要缩小8.而不是 mStorkeWidth / 2
        mRect = new RectF(8, 8, mWidth - 8, mWidth - 8);
        mCenterTextBaseLine = (mWidth - mCenterMetrics.bottom - mCenterMetrics.top) / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawArc(mRect, 150, 240, false, mCirclePaint);
        //通过调节控件高度来避免圆弧边界部分被遮挡的问题
        canvas.drawArc(mRect, 150, mSweepAngle, false, mProgressPaint);

        canvas.drawText(mCenterText, mRect.centerX(), mCenterTextBaseLine, mCenterTextPaint);
        canvas.drawText(mUnitText, mRect.centerX() + mCenterTextWidth / 2 + 10, mRect.centerY() + mCenterTextHeight / 2, mUnitTextPaint);
    }

    private void startLoading() {
        if(mAnimator == null) {
            //暂时写死，等UI和服务端处接口再完善。
            mAnimator = ValueAnimator.ofInt(0, 100);
            mAnimator.setDuration(800);
            mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mSweepAngle = (int) animation.getAnimatedValue();
                    postInvalidateOnAnimation();
                }
            });
        }
        if(!mAnimator.isRunning()) {
            mAnimator.start();
        }
    }

    /**
     * 18周岁以下周活跃达标时间为420分钟
     * 18周岁以上周活跃达标时间为150分钟
     * @param age 年龄
     */
    public void setReachGoalTime(int age) {
        mReachGoalTime = age <= 18 ? 420 : 150;
    }

    /**
     * 设置周活跃时间
     * @param activeTime 周活跃时间
     */
    public void setActiceTime(int activeTime) {
        mActiveTime = activeTime;
    }
}
