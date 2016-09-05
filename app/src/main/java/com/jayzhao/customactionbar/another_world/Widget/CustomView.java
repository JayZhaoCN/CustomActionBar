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
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
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
}
