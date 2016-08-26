package com.jayzhao.customactionbar.another_world.Widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

import com.jayzhao.customactionbar.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jay on 16-8-26.
 */
public class CircleLoadingView extends View {
    private static final String TAG = "CircleLoadingView";

    private int mHeight = 0;
    private int mWidth = 0;
    private int mRadius = 0;
    private int mPointRadius = 5;

    private Paint mPaint = null;
    private Resources mResources = null;
    private int mStartColor = 0;
    private int mEndColor = 0;
    private List<Point> mPointList = null;

    public CircleLoadingView(Context context) {
        this(context, null);
    }

    public CircleLoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mResources = context.getResources();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mStartColor = mResources.getColor(R.color.blue_light);
        mEndColor = mResources.getColor(R.color.transparency);
        mPaint.setColor(mStartColor);
        mPaint.setStyle(Paint.Style.FILL);
        mPointList = new ArrayList<>();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHeight = h;
        mWidth = w;
        mRadius = Math.min(mHeight, mWidth) / 2;

        mPointList.add(new Point(mRadius, 0));
        mPointList.add(new Point((int) (mRadius * (1 + Math.sin(Math.PI / 4))), (int) (mRadius * (1 - Math.sin(Math.PI / 4)))));
        mPointList.add(new Point(mRadius * 2, mRadius));
        mPointList.add(new Point((int) (mRadius * (1 + Math.sin(Math.PI / 4))), (int) (mRadius * (1 + Math.sin(Math.PI / 4)))));
        mPointList.add(new Point(mRadius, mRadius * 2));
        mPointList.add(new Point((int) (mRadius * (1 - Math.sin(Math.PI / 4))), (int) (mRadius * (1 + Math.sin(Math.PI / 4)))));
        mPointList.add(new Point(0, mRadius));
        mPointList.add(new Point((int) (mRadius * (1 - Math.sin(Math.PI / 4))), (int) (mRadius * (1 - Math.sin(Math.PI / 4)))));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for(Point point : mPointList) {
            canvas.drawCircle(point.x, point.y, mPointRadius, mPaint);
        }
    }
}
