package com.jayzhao.customactionbar.another_world.weekactive;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.jayzhao.customactionbar.MyUtils;

/**
 * Created by Jay on 2016/9/13.
 * 周活跃详情柱状图
 */
public class WeekActiveTime extends View {
    private static final String TAG = "WeekActiveTime";

    private static final int WIDTH = 307;
    private static final int HEIGHT = 168;

    private Context mContext;
    private Resources mResource;
    private float mDensity;

    private Paint mColumnPaint;
    private Paint mTextPaint;
    private Paint mLinePaint;

    public WeekActiveTime(Context context) {
        this(context, null);
    }

    public WeekActiveTime(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WeekActiveTime(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mContext = context;
        mDensity = WeekActiveCircle.getDensity(context);
        mResource = mContext.getResources();

        mColumnPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mColumnPaint.setColor(0xffe2ded9);
        mColumnPaint.setStyle(Paint.Style.FILL);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(0x66000000);
        mTextPaint.setTextSize(MyUtils.sp2px(mContext, 10));

        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setColor(0x1a000000);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setStrokeWidth((float) (0.3 * mDensity));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = View.resolveSize((int) (HEIGHT * mDensity), heightMeasureSpec);
        int width = View.resolveSize((int) (WIDTH * mDensity), widthMeasureSpec);

        Log.i(TAG, "height is: " + height);
        Log.i(TAG, "width is: " + width);
        setMeasuredDimension(width, height);
    }
}
