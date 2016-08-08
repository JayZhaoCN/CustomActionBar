package com.jayzhao.customactionbar.Widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PathMeasure;
import android.graphics.Region;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.jayzhao.customactionbar.MyUtils;
import com.jayzhao.customactionbar.R;

/**
 * <p>title:运动心率自定义控件</p>
 * <p>desc:</p>
 * author:liu-yuwu
 * data:16-7-27
 */
public class SportHeartRateView extends View {

    private Builder builder = new Builder(this);

    private String TAG = "SportHeartRateView";

    private Context mContext;

    /**
     * 视图总高度
     */
    private float mViewWidth = 0f;
    /**
     * 视图总宽度
     */
    private float mViewHeight = 0f;

    /**
     * 提示文本画笔
     */
    private TextPaint textLablePaint;
    /**
     * 刻度文本画笔
     */
    private TextPaint textXScalePaint;
    /**
     * 心率线画笔
     */
    private Paint bezierLinePaint;
    /**
     * 刻度线画笔
     */
    private Paint xScaleLinePaint;
    /**
     * 背景画笔
     */
    private Paint areaPaint;


    /**
     * 红心对应的X轴的刻度值
     */
    private float xValue = 0;


    public SportHeartRateView(Context context) {
        this(context, null);
    }

    public SportHeartRateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initValue();
    }

    public SportHeartRateView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mViewHeight = getMeasuredHeight();
        mViewWidth = getMeasuredWidth();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //X轴的长度
        float xScaleLength = mViewWidth - getPaddingLeft() - getPaddingRight();
        //每个刻度的宽度
        float perXScaleWidth = xScaleLength / (builder.lables == null ? 1 : builder.lables.length);
        //X坐标轴开始绘制的起点
        int startX = getPaddingLeft();
        //Y坐标轴开始绘制的起点
        int startY = (int) (mViewHeight - textXScalePaint.getTextSize() - getPaddingBottom());
        //X轴的终点
        float endX = mViewWidth - getPaddingRight();

        //绘制6段 2阶 贝塞尔曲线
        Path path = new Path();
        float yOffest = MyUtils.dp2px(mContext, builder.bezierYOffset);//Y轴的偏移量和控制点的偏移量
        float bezierMarginXScale = MyUtils.dp2px(mContext, builder.bezierMarginXScale);
        float bezierStartY = startY - bezierMarginXScale;
        path.moveTo(startX, bezierStartY);
        for (int i = 0; i < builder.bezierNum; i++) {
            float xOffset = xScaleLength / builder.bezierNum;//X轴的偏移量
            if (i % 2 == 0) {
                path.rQuadTo(xOffset / 2, yOffest, xOffset, -yOffest);
            } else {
                path.rQuadTo(xOffset / 2, -yOffest * 2, xOffset, -yOffest);
            }
        }
        canvas.drawPath(path, bezierLinePaint);
        PathMeasure pathMeasure = new PathMeasure(path, false);


        //绘制半透明区域背景
        Path area = new Path();
        area.set(path);
        area.lineTo(endX, startY);
        area.lineTo(startX, startY);
        area.lineTo(startX, bezierStartY);
        area.close();
        canvas.drawPath(area, areaPaint);


        //绘制坐标轴和刻度线，先画一条底部的线
        canvas.drawLine(startX, startY, endX, startY, xScaleLinePaint);
        //画虚线刻度
        xScaleLinePaint.setStyle(Paint.Style.STROKE);//设置空心
        float gap = MyUtils.dp2px(mContext, 5f);
        PathEffect effect = new DashPathEffect(new float[]{gap, gap, gap, gap}, 1);
        xScaleLinePaint.setPathEffect(effect);
        //绘制4条虚线刻度
        if (builder.scaleNum > 0) {
            for (int i = 0; i < builder.scaleNum; i++) {
                Path scalePath = new Path();
                float scaleX = startX + (i + 1) * perXScaleWidth;
                scalePath.moveTo(scaleX, startY);
                scalePath.lineTo(scaleX, startY - mViewHeight);
                //刻度线不能超过背景区域，所以要clipPath，求一个交集
                canvas.save();
                canvas.clipPath(area, Region.Op.INTERSECT);
                canvas.drawPath(scalePath, xScaleLinePaint);
                canvas.restore();
            }
            //刻度文本
            for (int i = 0; i < builder.scaleNum; i++) {
                int scaleOffset = (builder.maxXScale - builder.minXScale) / (builder.scaleNum + 1);
                String scaleStr = String.valueOf(builder.minXScale + scaleOffset * (i + 1));
                float textWidth = textXScalePaint.measureText(scaleStr);
                canvas.drawText(scaleStr, 0, scaleStr.length(), (startX + (i + 1) * perXScaleWidth) - textWidth / 2f, startY + textXScalePaint.getTextSize(), textXScalePaint);
            }
        }


        //提示文本
        if (builder.lables != null && builder.lables.length > 0) {
            for (int i = 0; i < builder.lables.length; i++) {
                String scaleStr = builder.lables[i];
                float textWidth = textLablePaint.measureText(scaleStr);
                float lableMarginiBezier = MyUtils.dp2px(getContext(), builder.lableMarginiBezier);
                canvas.drawText(scaleStr, 0, scaleStr.length(), startX + (0.5f + i) * perXScaleWidth - textWidth / 2f, bezierStartY - lableMarginiBezier * (i + 1) - textLablePaint.getTextSize(), textLablePaint);
            }
        }


        //绘制心形图片
        float[] bezierPosition = getBezierPosition(xValue, pathMeasure, xScaleLength);
        canvas.drawBitmap(builder.heartBmp, bezierPosition[0] - builder.heartBmp.getWidth() / 2, bezierPosition[1] - builder.heartBmp.getHeight() / 2, bezierLinePaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (builder.canTouch) {
            xValue = event.getX() - getPaddingLeft();
            invalidate();
            return true;
        }
        return super.onTouchEvent(event);
    }


    /**
     * 获取在曲线中的位置，因为是规则的曲线，可以根据曲线的总长和X轴的长度比例，通过X轴的坐标确定在曲线上的位置
     *
     * @param xValue       X所处轴的值
     * @param pathMeasure
     * @param xScaleLength X轴的总长
     * @return
     */
    private float[] getBezierPosition(float xValue, PathMeasure pathMeasure, float xScaleLength) {
        float[] pos = {0f, 0f};
        if (pathMeasure != null) {
            float pathLength = pathMeasure.getLength();
            float distance = pathLength * xValue / xScaleLength;
            pathMeasure.getPosTan(distance, pos, null);//根据距离曲线起点的距离获得一个曲线上的坐标
        }
        return pos;
    }

    /**
     * 初始化图片和画笔
     */
    private void initValue() {
        Log.d(TAG, "initValue:");

        textLablePaint = new TextPaint();
        textLablePaint.setAntiAlias(true);
        textLablePaint.setColor(builder.textLableColor);
        textLablePaint.setTextSize(MyUtils.sp2px(mContext, builder.lableTextSize));

        textXScalePaint = new TextPaint();
        textXScalePaint.setAntiAlias(true);
        textXScalePaint.setColor(builder.textXScaleColor);
        textXScalePaint.setTextSize(MyUtils.sp2px(mContext, builder.xScaleTextSize));

        bezierLinePaint = new Paint();
        bezierLinePaint.setAntiAlias(true);
        bezierLinePaint.setColor(builder.bezierLineColor);
        bezierLinePaint.setStrokeWidth(5);
        bezierLinePaint.setStyle(Paint.Style.STROKE);

        xScaleLinePaint = new Paint();
        xScaleLinePaint.setAntiAlias(true);
        xScaleLinePaint.setStrokeWidth(2);
        xScaleLinePaint.setColor(builder.xScaleLineColor);

        areaPaint = new Paint();
        areaPaint.setAntiAlias(true);
        areaPaint.setColor(builder.areaColor);
        areaPaint.setStyle(Paint.Style.FILL);
    }


    public void setBuilder(Builder builder) {
        this.builder = builder;
        initValue();
        invalidate();
    }


    /**
     * 设置心率值
     *
     * @param heartRateValue 测量到的心率值
     */
    public void setHeartRateValue(final int heartRateValue) {
        //X轴的长度
        this.post(new Runnable() {
            @Override
            public void run() {
                if (mViewWidth > 0 && builder.minXScale > 0 && builder.maxXScale >= heartRateValue && heartRateValue >= builder.minXScale) {
                    float xScaleLength = mViewWidth - getPaddingLeft() - getPaddingRight();
                    SportHeartRateView.this.xValue = (heartRateValue - builder.minXScale) * xScaleLength / (builder.maxXScale - builder.minXScale);
                    invalidate();
                } else {
                    Log.e(TAG, "数据错误，传入值必须在" + builder.minXScale + "-" + builder.maxXScale+"之间");
                }
            }
        });
    }

    /**
     * 参数构造器
     */
    public static class Builder {
        private SportHeartRateView sportHeartRateView;

        /**
         * 是否能移动红心
         */
        private boolean canTouch = false;

        /**
         * 小红心
         */
        private Bitmap heartBmp;
        /**
         * 贝塞尔曲线段数
         */
        private int bezierNum = 6;

        /**
         * 贝塞尔曲线到X轴的距离
         * unit dp
         */
        private float bezierMarginXScale = 40f;
        /**
         * 文字和贝塞尔曲线的距离
         * unit dp
         */
        private float lableMarginiBezier = 7.3f;
        /**
         * 贝塞尔曲线控制点沿Y轴的偏移量
         * unit dp
         */
        private float bezierYOffset = 5f;
        /**
         * 提示文本字体色
         */
        private int textLableColor = Color.parseColor("#777777");
        /**
         * 刻度文本字体色
         */
        private int textXScaleColor = Color.parseColor("#999999");
        /**
         * 心率线曲线颜色
         */
        private int bezierLineColor = Color.parseColor("#ED4F4F");
        /**
         * 刻度线颜色
         */
        private int xScaleLineColor = Color.parseColor("#FF4081");
        /**
         * 区域背景色
         */
        private int areaColor = Color.parseColor("#30ED4F4F");

        /**
         * 提示文本字体大小
         * unit sp
         */
        private float lableTextSize = 14f;

        /**
         * 刻度文本体大小
         * unit sp
         */
        private float xScaleTextSize = 13f;

        /**
         * 最大的刻度值
         */
        private int maxXScale = 200;
        /**
         * 最小的刻度值
         */
        private int minXScale = 100;
        /**
         * 显示的刻度数量
         */
        private int scaleNum = 4;

        /**
         * 默认的lable值
         */
        private String[] lables = {"热身放松", "脂肪燃烧", "心肺强化", "耐力强化", "无氧极限"};


        public Builder(SportHeartRateView sportHeartRateView) {
            this.sportHeartRateView = sportHeartRateView;
            heartBmp = BitmapFactory.decodeResource(sportHeartRateView.getResources(), R.mipmap.icon_heart);
        }

        public Builder canTouch(boolean canTouch) {
            this.canTouch = canTouch;
            return this;
        }

        public Builder bezierNum(int bezierNum) {
            this.bezierNum = bezierNum;
            return this;
        }

        /**
         * 贝塞尔曲线到X轴的距离 unit dp
         *
         * @param bezierMarginXScale
         * @return
         */
        public Builder bezierMarginXScale(int bezierMarginXScale) {
            this.bezierMarginXScale = bezierMarginXScale;
            return this;
        }

        /**
         * 贝塞尔曲线控制点沿Y轴的偏移量
         * unit dp
         *
         * @param bezierYOffset
         * @return
         */
        public Builder bezierYOffset(int bezierYOffset) {
            this.bezierYOffset = bezierYOffset;
            return this;
        }

        public Builder heartBmp(Bitmap heartBmp) {
            this.heartBmp = heartBmp;
            return this;
        }

        public Builder heartBmp(int resId) {
            this.heartBmp = BitmapFactory.decodeResource(sportHeartRateView.getResources(), resId);
            return this;
        }

        public Builder textLableColor(int textLableColor) {
            this.textLableColor = textLableColor;
            return this;
        }

        public Builder textXScaleColor(int textXScaleColor) {
            this.textXScaleColor = textXScaleColor;
            return this;
        }

        public Builder bezierLineColor(int bezierLineColor) {
            this.bezierLineColor = bezierLineColor;
            return this;
        }

        public Builder xScaleLineColor(int xScaleLineColor) {
            this.xScaleLineColor = xScaleLineColor;
            return this;
        }

        public Builder areaColor(int areaColor) {
            this.areaColor = areaColor;
            return this;
        }

        /**
         * @param lableTextSize 提示文本体大小 unit sp
         * @return
         */
        public Builder lableTextSize(float lableTextSize) {
            this.lableTextSize = lableTextSize;
            return this;
        }

        /**
         * @param xScaleTextSize 刻度文本体大小 unit sp
         * @return
         */
        public Builder scaleTextSize(float xScaleTextSize) {
            this.xScaleTextSize = xScaleTextSize;
            return this;
        }

        public Builder maxXScale(int maxXScale) {
            this.maxXScale = maxXScale;
            return this;
        }

        public Builder minXScale(int minXScale) {
            this.minXScale = minXScale;
            return this;
        }

        public Builder scaleNum(int scaleNum) {
            this.scaleNum = scaleNum;
            return this;
        }

        public Builder lables(String[] lables) {
            this.lables = lables;
            return this;
        }

        /**
         * 构建，返回一个新对象
         *
         * @return
         */
        public SportHeartRateView build() {
            return this.sportHeartRateView;
        }
    }
}
