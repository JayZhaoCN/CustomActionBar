package com.jayzhao.customactionbar.Widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by hm on 16-7-27.
 */
public class Bezier3 extends View {
    private Paint mPaint1 = null;
    private Paint mPaint2 = null;

    private PointF mControlPoint1 = null;
    private PointF mControlPoint2 = null;
    private PointF mStartPoint = null;
    private PointF mEndPoint = null;

    private MODE mMode = MODE.MVOE_CONTROL_1;

    /**
     * 模式
     */
    public enum MODE {
        MVOE_CONTROL_1,
        MVOE_CONTROL_2
    }


    public Bezier3(Context context) {
        this(context, null);
    }

    public Bezier3(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Bezier3(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mControlPoint1 = new PointF(0, 0);
        mControlPoint2 = new PointF(0, 0);
        mStartPoint = new PointF(0, 0);
        mEndPoint = new PointF(0, 0);

        mPaint1 = new Paint();
        mPaint2 = new Paint();
        mPaint1.setStyle(Paint.Style.STROKE);
        mPaint2.setStyle(Paint.Style.STROKE);
    }

    public void setMode(MODE mode) {
        this.mMode = mode;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        int centerX = w/2;
        int centerY = h/2;
        mStartPoint.x = centerX - 200;
        mStartPoint.y = centerY;

        mEndPoint.x = centerX + 200;
        mEndPoint.y = centerY;

        mControlPoint1.x = centerX - 100;
        mControlPoint1.y = centerY - 100;

        mControlPoint2.x = centerX + 100;
        mControlPoint2.y = centerY + 100;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = View.resolveSize(300, widthMeasureSpec);
        int height = View.resolveSize(300, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        if(MODE.MVOE_CONTROL_1 == mMode) {
            mControlPoint1.x = x;
            mControlPoint1.y = y;
        } else {
            mControlPoint2.x = x;
            mControlPoint2.y = y;
        }
        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //先画起始结束点和控制点
        mPaint1.setStrokeWidth(20);
        mPaint1.setColor(Color.GRAY);
        canvas.drawPoint(mStartPoint.x, mStartPoint.y, mPaint1);
        canvas.drawPoint(mEndPoint.x, mEndPoint.y, mPaint1);
        canvas.drawPoint(mControlPoint1.x, mControlPoint1.y, mPaint1);
        canvas.drawPoint(mControlPoint2.x, mControlPoint2.y, mPaint1);

        //再画辅助线
        mPaint1.setStrokeWidth(4);
        canvas.drawLine(mStartPoint.x, mStartPoint.y, mControlPoint1.x, mControlPoint1.y, mPaint1);
        canvas.drawLine(mControlPoint1.x, mControlPoint1.y, mControlPoint2.x, mControlPoint2.y, mPaint1);
        canvas.drawLine(mControlPoint2.x, mControlPoint2.y, mEndPoint.x, mEndPoint.y, mPaint1);

        //最后画贝塞尔曲线
        Path path = new Path();
        path.moveTo(mStartPoint.x, mStartPoint.y);
        path.cubicTo(mControlPoint1.x, mControlPoint1.y, mControlPoint2.x, mControlPoint2.y, mEndPoint.x, mEndPoint.y);
        mPaint2.setColor(Color.CYAN);
        mPaint2.setStrokeWidth(4);
        canvas.drawPath(path, mPaint2);
    }
}
