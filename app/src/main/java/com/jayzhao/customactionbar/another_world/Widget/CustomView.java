package com.jayzhao.customactionbar.another_world.Widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.RegionIterator;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Jay on 16-9-5.
 */
public class CustomView extends View {
    private static final String TAG = "CustomView";

    private Paint mPaint = null;
    private Region mRegion = null;
    private Rect mRect = null;
    private RegionIterator iterator = null;

    private Path mPath = null;
    private float mOldX = 0;
    private float mOldY = 0;
    private float mCenterX = 0;
    private float mCenterY = 0;

    public CustomView(Context context) {
        this(context, null);
    }

    public CustomView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        /**
         * Text
         */
        /*mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(5);
        mPaint.setTextSize(80);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setUnderlineText(true);
        //设置字体水平倾斜度
        mPaint.setTextSkewX(-0.25f);*/

        /**
         * Region
         */
        /*mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2);
        Path path = new Path();
        path.addCircle(200, 200, 100, Path.Direction.CW);
        mRegion = new Region(10, 10, 100, 100);
        mRegion.setPath(path, new Region(0, 0, 800, 800));
        mRect = new Rect();
        iterator = new RegionIterator(mRegion);*/

        /**
         * 写字
         */
        mPath = new Path();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mOldX = event.getX();
                mOldY = event.getY();
                mPath.moveTo(event.getX(), event.getY());
                return true;
            case MotionEvent.ACTION_MOVE:
                //mPath.lineTo(event.getX(), event.getY());

                mCenterX = (mOldX + event.getX()) / 2;
                mCenterY = (mOldY + event.getY()) / 2;
                mPath.quadTo(mOldX, mOldY, mCenterX, mCenterY);
                mOldX = event.getX();
                mOldY = event.getY();
                postInvalidate();
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 执行顺序
     09-05 17:16:31.222 20992-20992/com.jayzhao.customactionbar I/CustomView: onMeasure
     09-05 17:16:31.252 20992-20992/com.jayzhao.customactionbar I/CustomView: onSizeChanged
     09-05 17:16:31.252 20992-20992/com.jayzhao.customactionbar I/CustomView: onLayout
     09-05 17:16:31.262 20992-20992/com.jayzhao.customactionbar I/CustomView: onMeasure
     09-05 17:16:31.262 20992-20992/com.jayzhao.customactionbar I/CustomView: onLayout
     09-05 17:16:31.262 20992-20992/com.jayzhao.customactionbar I/CustomView: onDraw
     */

    @Override
    protected void onDraw(Canvas canvas) {
        Log.i(TAG, "onDraw");
        super.onDraw(canvas);
        canvas.drawPath(mPath, mPaint);
        /**
         * Text
         */
        //canvas.drawText("Jay Zhao", 10, 100, mPaint);

        /**
         * Region
         */
        /*while(iterator.next(mRect)) {
            canvas.drawRect(mRect, mPaint);
        }

        Rect rect = mRegion.getBounds();
        canvas.drawRect(rect, mPaint);*/
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        Log.i(TAG, "onSizeChanged");
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.i(TAG, "onMeasure");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        Log.i(TAG, "onLayout");
        super.onLayout(changed, left, top, right, bottom);
    }
}
