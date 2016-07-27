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
 * Created by Jay on 16-7-27.
 * 二阶贝塞尔曲线
 */
public class Bezier2 extends View {

    private Paint mPaint = null;
    private int centerX, centerY;

    private PointF start, end, control;

    /**
     * 在程序中动态创建一个View时会调用一个参数的构造方法。
     * @param context
     */
    public Bezier2(Context context) {
        this(context, null);
    }

    /**
     * 在布局文件中创建一个View，会调用两个参数的构造方法。
     * 在Xml文件设定的参数会加载到AttributeSet中。
     * @param context
     * @param attrs
     */
    public Bezier2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * xml里通过某种方式指定了view的style时,会调用三个参数的构造方法
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public Bezier2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(8);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setTextSize(60);

        start = new PointF(0, 0);
        end = new PointF(0, 0);
        control = new PointF(0, 0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        control.x = event.getX();
        control.y = event.getY();
        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setColor(Color.GRAY);
        mPaint.setStrokeWidth(20);
        //画起始结束点和控制点
        canvas.drawPoint(control.x, control.y, mPaint);
        canvas.drawPoint(start.x, start.y, mPaint);
        canvas.drawPoint(end.x, end.y, mPaint);

        mPaint.setStrokeWidth(4);
        canvas.drawLine(start.x, start.y, control.x, control.y, mPaint);
        canvas.drawLine(control.x, control.y, end.x, end.y, mPaint);

        mPaint.setColor(Color.CYAN);
        mPaint.setStrokeWidth(2);
        Path path = new Path();
        path.moveTo(start.x, start.y);
        path.quadTo(control.x, control.y, end.x, end.y);
        canvas.drawPath(path, mPaint);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mWidth = View.resolveSize(300, widthMeasureSpec);
        int mHeight = View.resolveSize(300, heightMeasureSpec);
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = w/2;
        centerY = h/2;

        start.x = centerX - 200;
        start.y = centerY;
        end.x = centerX + 200;
        end.y = centerY;
        control.x = centerX;
        control.y = centerY - 100;
    }
}
