package com.jayzhao.customactionbar.another_world.Widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.jayzhao.customactionbar.R;

/**
 * Created by Jay on 16-9-1.
 * 下载进度条
 */
public class DownloadProgressView extends View {
    private static final String TAG = "DownloadProgressView";

    private Paint mTotalPaint = null;
    private Paint mProgressPaint = null;
    private Resources mResources = null;

    private int mWidth = 0;
    private int mHeight = 0;
    private int mProgressWidth = 0;
    /**
     * 进度:0 - 100
     */
    private int mProgress = 50;
    //画线可能更好一点
    private int mStrokeWidth = 10;

    public DownloadProgressView(Context context) {
        this(context, null);
    }

    public DownloadProgressView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DownloadProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        mResources = getResources();
        mTotalPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTotalPaint.setColor(mResources.getColor(R.color.status_color));
        mTotalPaint.setStrokeWidth(mStrokeWidth);
        mTotalPaint.setStrokeCap(Paint.Cap.ROUND);
        mProgressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mProgressPaint.setColor(mResources.getColor(R.color.selected_origin));
        mProgressPaint.setStrokeWidth(mStrokeWidth);
        mProgressPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    public void setProgress(int progress) {
        mProgress = progress;
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHeight = h;
        mWidth = w;
        Log.i(TAG, "Height : " + mHeight);
        Log.i(TAG, "Width : " + mWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mProgressWidth = mProgress * mWidth / 100 - mStrokeWidth / 2;
        canvas.drawLine(mStrokeWidth / 2, mHeight / 2, mWidth - mStrokeWidth / 2, mHeight / 2, mTotalPaint);
        canvas.drawLine(mStrokeWidth / 2, mHeight / 2, mProgressWidth, mHeight / 2, mProgressPaint);
    }
}
