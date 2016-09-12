package com.jayzhao.customactionbar.another_world.weekactive;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.jayzhao.customactionbar.R;

/**
 * Created by Jay on 16-9-12.
 * 周活跃详情圆形自定义控件
 */
public class WeekActiveCircle extends View {
    private static final String TAG = "WeekActiveCircle";

    private float mDensity = 0f;
    private static float STROKE = 1f;
    private static float LINE_WIDTH = 1f;
    private static float GAP = 8f;
    private static float LINE_LENGTH = 15f;


    private Context mContext = null;
    private int mRadius = 0;
    private int mDegree = 0;
    private int mWidth = 0;
    private int mHeight = 0;
    private float mStrokeWidth = 0;
    private float mLineWidth = 0;
    private float mLineLength = 0;
    private float mGap = 0;

    private Paint mCirclePaint = null;
    private Paint mLinePaint = null;
    private Paint mProgressPaint = null;
    private Resources mResources = null;
    private RectF mRect = null;
    private RectF mProgressRect = null;
    private float mLinePoints[] = null;

    public WeekActiveCircle(Context context) {
        this(context, null);
    }

    public WeekActiveCircle(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WeekActiveCircle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        obtainStyledAttr(attrs, defStyleAttr);
    }

    private void init(Context context) {
        mContext = context;
        mResources = mContext.getResources();

        mDensity = getDensity(mContext);
        mStrokeWidth = STROKE * mDensity;
        mLineWidth = LINE_WIDTH * mDensity;
        mGap = GAP * mDensity;
        mLineLength = LINE_LENGTH * mDensity;

        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setStrokeWidth(mStrokeWidth);
        mCirclePaint.setColor(mResources.getColor(R.color.blue_light));

        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setStrokeWidth(mLineWidth);
        mLinePaint.setColor(mResources.getColor(R.color.blue_light));

        mProgressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mProgressPaint.setColor(mResources.getColor(R.color.blue_light));
        mProgressPaint.setStyle(Paint.Style.STROKE);
        mProgressPaint.setStrokeWidth(mLineLength);
    }

    private void obtainStyledAttr(AttributeSet attrs, int defStyleAttr) {
        TypedArray ta = mContext.obtainStyledAttributes(attrs, R.styleable.WeekActiveCircle, 0, defStyleAttr);
        mRadius = ta.getDimensionPixelOffset(R.styleable.WeekActiveCircle_CircleRadius, 100);
        mDegree = ta.getInt(R.styleable.WeekActiveCircle_CircleDegree, 120);
        ta.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int desireHeight = (int) (mRadius + mRadius * Math.sin(Math.toRadians(90 - mDegree / 2)));
        int desireWidth = 2 * mRadius;

        mWidth = View.resolveSize(desireWidth, widthMeasureSpec);
        mHeight = View.resolveSize(desireHeight, heightMeasureSpec);

        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Rect rect = new Rect();
        rect.left = getPaddingLeft();
        rect.top = getPaddingTop();
        rect.right = w - getPaddingRight();
        rect.bottom = h - getPaddingBottom();

        mRect = new RectF(mStrokeWidth, mStrokeWidth, w - mStrokeWidth, w - mStrokeWidth);
        mProgressRect = new RectF(mGap + mLineLength / 2, mGap + mLineLength / 2, w - mGap - mLineLength / 2, w - mGap - mLineLength / 2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawArc(mRect, 90 + mDegree / 2, 360 - mDegree, false, mCirclePaint);
        drawLines(canvas);
        canvas.drawArc(mProgressRect, 90 + mDegree / 2, 360 - mDegree - 90, false, mProgressPaint);
    }

    private void drawLines(Canvas canvas) {
        if(mLinePoints == null) {
            Log.i(TAG, "create new lines!");
            mLinePoints = new float[(360 - mDegree + 1) * 4];
            float outerX, outerY, innerX, innerY;
            float outerR = mRadius - mGap;
            float innerR = mRadius - mGap - mLineLength + 0.5f;
            int startDegree = 90 + mDegree / 2;
            for (int i = 0; i <= 360 - mDegree; i++) {
                if(i == 0 || i == 360 - mDegree) {
                    outerX = (float) (mRadius + (mRadius - mStrokeWidth) * Math.cos(Math.toRadians(startDegree + i)));
                    outerY = (float) (mRadius + (mRadius - mStrokeWidth) * Math.sin(Math.toRadians(startDegree + i)));
                } else {
                    outerX = (float) (mRadius + outerR * Math.cos(Math.toRadians(startDegree + i)));
                    outerY = (float) (mRadius + outerR * Math.sin(Math.toRadians(startDegree + i)));
                }
                innerX = (float) (mRadius + innerR * Math.cos(Math.toRadians(startDegree + i)));
                innerY = (float) (mRadius + innerR * Math.sin(Math.toRadians(startDegree + i)));
                mLinePoints[i * 4] = outerX;
                mLinePoints[i * 4 + 1] = outerY;
                mLinePoints[i * 4 + 2] = innerX;
                mLinePoints[i * 4 + 3] = innerY;
            }
        }
        canvas.drawLines(mLinePoints, 0, (360 - mDegree + 1) * 4, mLinePaint);
    }

    public static float getDensity(Context context) {
        float density;
        if (context == null) {
            return 3f;
        }
        density = context.getResources().getDisplayMetrics().density;
        return density;
    }
}
