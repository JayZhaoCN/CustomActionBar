package com.jayzhao.customactionbar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RelativeLayout;

public class MaskableLayout extends RelativeLayout {

    private final Path mPath = new Path();
    private float mRadius = 0;
    private float mPosX = 0;
    private float mPosY = 0;

    private final String TAG = "MaskableLayout";


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
    //现在先记住，在ViewGroup中绘制需要调用dispatchDraw()方法
    @Override
    protected void dispatchDraw(Canvas canvas) {

        Log.e(TAG, "dispatchDraw");

        //原理：
        //MaskableLayout在创建时会调用dispatchDraw(Canvas canvas)方法
        //在ChangeAnimation中每次Fragment02.getRoot().setOvalMask()方法会调用
        //动态改变Oval的大小，canvas.clipPath()方法会将canvas切割成需要的形状。
        //Region.Op.DIFFERENCE表示显示第一次canvas和第二次canvas不同的区域。

        //差不多有点明白了
        updatePath();
        canvas.clipPath(mPath, Region.Op.DIFFERENCE);
        super.dispatchDraw(canvas);
    }

    private void updatePath() {
        mPath.reset();
        mPath.addOval(new RectF(mPosX - mRadius, mPosY - mRadius, mPosX + mRadius, mPosY + mRadius), Path.Direction.CW);
    }
}
