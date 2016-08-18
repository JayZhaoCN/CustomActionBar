package com.jayzhao.customactionbar.another_world.Widget;

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
 * Created by Jay on 16-8-17.
 * 高仿微博的加载图片的进度提示控件
 */
public class LoadingPictureView extends View {

    private Context mContext = null;

    private Paint mCriclePaint = null;
    private Paint mInnerPaint = null;

    private int mHeight = 0;
    private int mWidth = 0;

    private RectF mRect = null;
    private RectF mInnerRect = null;

    private int mLineWidth = 2;
    private int mInterSpaceWidth = 2;
    private int mOffsetAngle = 90;

    private Resources mResources = null;

    private static final String TAG = "LoadingPictureView";

    public LoadingPictureView(Context context) {
        this(context, null);
    }

    public LoadingPictureView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingPictureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mResources = getResources();
        mContext = context;
        mResources = getResources();
        mCriclePaint = new Paint();
        mCriclePaint.setColor(mResources.getColor(R.color.black_40_percent));
        mCriclePaint.setAntiAlias(true);
        mCriclePaint.setStyle(Paint.Style.STROKE);
        mCriclePaint.setStrokeWidth(2);

        mInnerPaint = new Paint();
        mInnerPaint.setColor(mResources.getColor(R.color.black_40_percent));
        mInnerPaint.setStyle(Paint.Style.FILL);
        mInnerPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int desireHeight = 300;
        int desireWidth = 300;

        mWidth = View.resolveSize(desireWidth, widthMeasureSpec);
        mHeight = View.resolveSize(desireHeight, heightMeasureSpec);

        Log.i(TAG, "mWidth: " + mWidth + " mHeight: " + mHeight);

        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mRect = new RectF(mLineWidth/2, mLineWidth/2, w - mLineWidth/2, h - mLineWidth/2);
        mInnerRect = new RectF(mInterSpaceWidth + mLineWidth, mInterSpaceWidth + mLineWidth,
                                w - mInterSpaceWidth - mLineWidth, h - mInterSpaceWidth - mLineWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawOval(mRect, mCriclePaint);
        canvas.drawArc(mInnerRect, -90, mOffsetAngle, true, mInnerPaint);
    }

    /**
     * 设置进度 0 - 360
     * @param angle
     */
    public void setProgress(int angle) {
        mOffsetAngle = angle;
        invalidate();
    }
}
