package com.jayzhao.customactionbar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class MaskableLayout extends RelativeLayout {

    private final Path mPath = new Path();
    private float mRadius = 0;
    private float mPosX = 0;
    private float mPosY = 0;

    public MaskableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setOvalMask(float x, float y, float r) {
        mPosX = x;
        mPosY = y;
        mRadius = r;
        invalidate();
    }

    //似乎用onDraw()不可以
    @Override
    protected void dispatchDraw(Canvas canvas) {
        updatePath();
        canvas.clipPath(mPath, Region.Op.DIFFERENCE);
        super.dispatchDraw(canvas);
    }

    private void updatePath() {
        mPath.reset();
        mPath.addOval(new RectF(mPosX - mRadius, mPosY - mRadius, mPosX + mRadius, mPosY + mRadius), Path.Direction.CW);
    }
}
