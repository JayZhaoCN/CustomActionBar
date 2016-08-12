package com.jayzhao.customactionbar.another_world.Widget;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.SweepGradient;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.jayzhao.customactionbar.MyUtils;
import com.jayzhao.customactionbar.R;

/**
 * Created by Jay on 16-8-12.
 */
public class MyProgressView extends View {

    private static final String TAG = "MyProgressView";

    private int mStrokeColor = 0;
    private float mStrokeWidth = 0;
    private Context mContext = null;

    private Drawable mCenterDrawable = null;

    private int mWidth = 0;
    private int mHeight = 0;

    private Paint mPaint = null;

    private SweepGradient mSweepGradient = null;

    private int offsetAngle = 0;

    private ValueAnimator mAnimator = null;

    public MyProgressView(Context context) {
        this(context, null);
    }

    public MyProgressView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        obtainAttrs(attrs);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(mStrokeColor);
        mPaint.setStrokeWidth(mStrokeWidth);
        //不太明白这句代码是做什么用的
        //mPaint.setStrokeCap(Paint.Cap.SQUARE);

        //不自动Loading
        //startLoading();
    }

    private void obtainAttrs(AttributeSet attrs) {
        TypedArray ta = getResources().obtainAttributes(attrs, R.styleable.ProgressView);
        mStrokeColor = ta.getColor(R.styleable.ProgressView_strokeColor1, getResources().getColor(R.color.blue_light));
        mStrokeWidth = ta.getDimensionPixelOffset(R.styleable.ProgressView_strokeWidth1, 10);
        mCenterDrawable = ta.getDrawable(R.styleable.ProgressView_picture);
        Log.i(TAG, "" + mStrokeWidth);
        ta.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int desireWidth = getPaddingLeft() + getPaddingRight() + (int)MyUtils.dp2px(mContext, 100f);
        int desireHeight = getPaddingTop() + getPaddingBottom() + (int)MyUtils.dp2px(mContext, 100f);

        mWidth = View.resolveSize(desireWidth, widthMeasureSpec);
        mHeight = View.resolveSize(desireHeight, heightMeasureSpec);
        Log.i(TAG + "_mWidth", "" + mWidth);
        Log.i(TAG + "_mHeight", "" + mHeight);

        setMeasuredDimension(mWidth, mHeight);
    }

    public static void drawBitmapCenter(Canvas canvas, float x, float y, float scale, Bitmap bitmap, Paint paint) {
        drawBitmapCenter(canvas, x, y, scale, true, true, bitmap, paint);
    }


    public static void drawBitmapCenter(Canvas canvas, float x, float y, float scale, boolean horizonCenter,
                                        boolean verticalCenter, Bitmap bitmap, Paint paint) {
        float offsetX = x;
        float offsetY = y;
        if (horizonCenter) {
            offsetX -= bitmap.getWidth() * scale / 2;
        }
        if (verticalCenter) {
            offsetY -= bitmap.getHeight() * scale / 2;
        }

        Matrix matrix = new Matrix();
        matrix.setScale(scale, scale);
        matrix.postTranslate(offsetX, offsetY);

        canvas.drawBitmap(bitmap, matrix, paint);
    }

    public static Bitmap drawable2Bitmap(Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initGradient();
        canvas.drawCircle(mWidth / 2, mHeight / 2, (mHeight - mStrokeWidth) / 2, mPaint);
        mCenterDrawable.setBounds(mWidth/2 - mCenterDrawable.getIntrinsicWidth()/2,
                mHeight/2 - mCenterDrawable.getIntrinsicHeight()/2,
                mWidth/2 + mCenterDrawable.getIntrinsicWidth()/2,
                mHeight/2 + mCenterDrawable.getIntrinsicHeight()/2);
        mCenterDrawable.draw(canvas);
        //drawBitmapCenter(canvas, mWidth / 2, mHeight / 2, 2.0f, drawable2Bitmap(mCenterDrawable), null);
    }

    public void startLoading() {
        if(mAnimator == null) {
            mAnimator = ValueAnimator.ofFloat(0, 1);
            mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    offsetAngle = (int)((Float)animation.getAnimatedValue() * 360);
                    Log.i("JayZhao ", "offsetAngle " + offsetAngle);
                    postInvalidateOnAnimation();
                }
            });
            mAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {

                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    offsetAngle = 0;
                    postInvalidateOnAnimation();
                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            mAnimator.setInterpolator(new LinearInterpolator());
            mAnimator.setDuration(5000);
            mAnimator.setRepeatCount(ValueAnimator.INFINITE);
        }
        if(!mAnimator.isStarted()) {
            mAnimator.start();
        }
    }

    public void stopLoading() {
        if(mAnimator == null) {
            return;
        }
        if(mAnimator.isRunning()) {
            mAnimator.cancel();
            mAnimator = null;
        }
    }

    private void initGradient() {
        if(mSweepGradient == null) {
            mSweepGradient = new SweepGradient(mWidth / 2, mHeight / 2, getResources().getColor(R.color.transparency), mStrokeColor);
            mPaint.setShader(mSweepGradient);
        }
        Matrix matrix = new Matrix();
        matrix.setRotate(offsetAngle, mWidth/2, mHeight/2);
        mSweepGradient.setLocalMatrix(matrix);
    }
}
