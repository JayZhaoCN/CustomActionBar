package com.jayzhao.customactionbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by hm on 16-4-15.
 */
public class MyProgressBar extends View {

    private float mActualRadius = 345;

    private Context mContext = null;
    private int mBackgroundColor = Color.TRANSPARENT;
    private int mStrockColor = 0x008ecd;
    private int mProgress = 0;
    private int mProgressColor = Color.BLACK;
    private float mProgressWidth = 0;
    private int mTotal = 100;
    private float mStrockWidth = 3;
    private int mRadius = 345;
    private String mText = "";
    private int mTextColor = 0xffbababa;
    private float mTextSize = 45;
    private float mProgressBarBackgroundWidth = 10;
    private int mProgressBackgroundColor = 0x33ffffff;
    private int mSizeWidth = 0;
    private int mSizeHeight = 0;

    private float mSweepAngle = 0;

    private Paint mProgressPaint = null;
    private Paint mBackgroundPaint = null;
    private RectF mBound = null;
    private RectF mArcBound = null;
    private Paint mStrockPaint = null;
    private Paint mTextPaint = null;
    private Paint mProgressBackgroundPaint = null;

    public MyProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyProgressBar(Context context) {
        this(context, null);
    }

    public MyProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        obtainStyledAttr(attrs, defStyleAttr);
    }

    private void obtainStyledAttr(AttributeSet attrs, int defStyleAttr) {
        TypedArray ta = mContext.obtainStyledAttributes(attrs, R.styleable.MyProgressBar, 0, defStyleAttr);
        mBackgroundColor = ta.getColor(R.styleable.MyProgressBar_MyProgressBarBackgroundColor, mBackgroundColor);
        mStrockColor = ta.getColor(R.styleable.MyProgressBar_MyProgressBarStrockColor, mStrockColor);
        mProgress = ta.getInt(R.styleable.MyProgressBar_MyProgressBarProgress, mProgress);
        mProgressColor = ta.getColor(R.styleable.MyProgressBar_MyProgressBarProgressColor, mProgressColor);
        mProgressWidth = ta.getDimension(R.styleable.MyProgressBar_MyProgressBarProgressWidth, mProgressWidth);
        mTotal = ta.getInt(R.styleable.MyProgressBar_MyProgressBarTotal, mTotal);
        mStrockWidth = ta.getDimension(R.styleable.MyProgressBar_MyProgressBarStrockWidth, mStrockWidth);
        mRadius = ta.getInt(R.styleable.MyProgressBar_MyProgressBarRadius, mRadius);
        String text = "";
        mText = (text = ta.getString(R.styleable.MyProgressBar_MyProgressBarText)) == null ? "" : text;
        mTextSize = ta.getDimension(R.styleable.MyProgressBar_MyProgressBarTextSize, mTextSize);
        mTextColor = ta.getColor(R.styleable.MyProgressBar_MyProgressBarTextColor, mTextColor);
        mProgressBarBackgroundWidth = ta.getDimension(R.styleable.MyProgressBar_MyProgressBarProgressBgWidth, mProgressBarBackgroundWidth);
        mProgressBackgroundColor = ta.getColor(R.styleable.MyProgressBar_MyProgressBarBackgroundColor, mProgressBackgroundColor);
    }

    private void refresh() {
        mBackgroundPaint.setColor(mBackgroundColor);
        mStrockPaint.setColor(mStrockColor);
        mStrockPaint.setStrokeWidth(mStrockWidth);
        mProgressPaint.setColor(mProgressColor);
        mProgressPaint.setStrokeWidth(mProgressWidth);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(mTextSize);
        mSweepAngle = (float) mProgress / mTotal * 360;
        mProgressBackgroundPaint.setStrokeWidth(mProgressBarBackgroundWidth);
        mProgressBackgroundPaint.setColor(mProgressBackgroundColor);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //暂时不能理解mStrockWidth 和 mProgressWidth的含义
        int maxContentSize = (int) (mRadius * 2 + Math.max(mProgressWidth, mStrockWidth));
        int minw = getPaddingLeft() + getPaddingRight() + maxContentSize;
        int w = resolveSize(minw, widthMeasureSpec);

        int minh = getPaddingTop() + getPaddingRight() + maxContentSize;
        int h = resolveSize(minh, heightMeasureSpec);

        setMeasuredDimension(w, h);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        float offset = Math.max(mProgressWidth, mStrockWidth);
        float width = w - getPaddingLeft() - getPaddingRight() - offset;
        float height = h - getPaddingTop() - getPaddingBottom() - offset;
        mActualRadius = Math.min(width / 2, height / 2);

        mSizeWidth = w / 2;
        mSizeHeight = h / 2;
        mBound = new RectF(mSizeWidth - mActualRadius, mSizeHeight - mActualRadius, mSizeWidth + mActualRadius,
                mSizeHeight + mActualRadius);
        mArcBound = new RectF(mSizeWidth - mActualRadius, mSizeHeight - mActualRadius, mSizeWidth + mActualRadius,
                mSizeHeight + mActualRadius);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        refresh();
        canvas.drawOval(mBound, mBackgroundPaint);
        if (mProgress == 0) {
            canvas.drawCircle(mSizeWidth, mSizeHeight, mActualRadius, mStrockPaint);
        } else {
            canvas.drawCircle(mSizeWidth, mSizeHeight, mActualRadius, mProgressBackgroundPaint);
            canvas.drawArc(mArcBound, -90, mSweepAngle, false, mProgressPaint);
        }

        String[] lines = mText.split("\n");
        if (lines == null || lines.length == 0) {
            return;
        }

        int length = lines.length - 1;
        float y = mSizeHeight - (mTextPaint.ascent() + mTextPaint.descent()) / 2 - length * (mTextPaint.descent() - mTextPaint.ascent()) / 2;

        for (String line : lines) {
            if (line == null) {
                continue;
            }
            canvas.drawText(line, mSizeWidth, y, mTextPaint);
            y += mTextPaint.descent() - mTextPaint.ascent();
        }
    }




}
