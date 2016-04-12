
package com.jayzhao.customactionbar;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static com.jayzhao.customactionbar.MyBaseTitleActivity.STYLE.BACK_AND_MORE;
import static com.jayzhao.customactionbar.MyBaseTitleActivity.STYLE.FULL_SCREEN;
import static com.jayzhao.customactionbar.MyBaseTitleActivity.STYLE.SINGLE_BACK;

/**
 * Created by Administrator on 2016/4/7.
 */
public class MyBaseTitleActivity extends Activity {

    private static final String TAG = "MyBaseTitleActivity";
    private FrameLayout mContentParent;
    private Button mLeftButton;
    private Button mRightButton;

    private View mContentView = null;

    private RelativeLayout mTitle;

    public enum STYLE {
        BACK_AND_MORE,
        SINGLE_BACK,
        FULL_SCREEN
    }

    private STYLE mStyle = SINGLE_BACK;

    public Button getRightButton() {
        return mRightButton;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.base_title_parent);


        mContentParent = (FrameLayout) findViewById(R.id.title_parent);
        mTitle = (RelativeLayout) findViewById(R.id.title);

        mLeftButton  = (Button) findViewById(R.id.left);
        mRightButton = (Button) findViewById(R.id.right);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            // Translucent status bar
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        setMiuiStatusBarDarkMode(this, false);

        int x = getStatusBarHeight();

        View statusView = findViewById(R.id.status_view);

        statusView.getLayoutParams().height = x;

    }

    public void setStyle(STYLE style) {
        mStyle = style;

        switch(style) {
            case BACK_AND_MORE:
                mLeftButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
                break;
            case SINGLE_BACK:
                mRightButton.setVisibility(View.GONE);
                mLeftButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
                break;
            case FULL_SCREEN:
                mTitle.setVisibility(View.GONE);
                updateView();
                break;
            default:
                break;
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        View view = View.inflate(this, layoutResID, null);
        setContentView(view);
    }

    @Override
    public void setContentView(View view) {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.TOP;

        setContentView(view, params);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        mContentView = view;
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) params;

        int titleHeight = this.getResources().getDimensionPixelSize(R.dimen.title_height);
        layoutParams.topMargin = titleHeight + getStatusBarHeight();

        mContentParent.addView(mContentView, layoutParams);
        updateView();
    }

    private void updateView() {
        if(mContentView != null && mStyle == FULL_SCREEN) {
            //Log.e(TAG, "mContentView is not null");
            //直接从mContentView中拿到的LayoutParams似乎是NULL，具体为什么不知道。
            //mContentView.getLayoutParams();  //the value is NULL
            //(mContentParent.addView())，当然是null啊
            if(mContentParent.getChildAt(1) != null) {
                //Log.e(TAG, "child is not null");
                FrameLayout.LayoutParams params =
                        (FrameLayout.LayoutParams) mContentParent.getChildAt(1).getLayoutParams();
                params.topMargin = 0;
                mContentView.setLayoutParams(params);
            } else
                return;
        }
    }


    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static boolean setMiuiStatusBarDarkMode(Activity activity, boolean darkmode) {
        Class<? extends Window> clazz = activity.getWindow().getClass();
        try {
            int darkModeFlag = 0;
            Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            extraFlagField.invoke(activity.getWindow(), darkmode ? darkModeFlag : 0, darkModeFlag);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


}
