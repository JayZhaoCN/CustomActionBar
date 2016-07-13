
package com.jayzhao.customactionbar;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.lang.reflect.Method;



/**
 * Created by Zhao Jiabao on 2016/4/7.
 */
public class MyBaseTitleActivity extends FragmentActivity implements View.OnClickListener {

    private static final String TAG = "MyBaseTitleActivity";
    private FrameLayout mContentParent;
    private Button mLeftButton;
    private Button mRightButton;

    private ISearch mSearchListener = null;

    private View mContentView = null;
    private TextView mTitleText = null;
    private EditText mEditText  = null;

    private Button mSearchButton = null;

    private RelativeLayout mTitle;

    @Override
    public void onClick(View v) {
        Log.e(TAG, "some button has been clicked");
        switch(v.getId()) {
            //任何情况下点击LeftButton都会返回上一级。
            case R.id.left:
                Log.e(TAG, "back click!");
                finish();
                break;
            case R.id.right:
                switch(mStyle) {
                    case BACK_AND_EDIT:
                        changeToEdit();
                        /**
                         * 下面的代码展示了如何调起软键盘
                         */
                        mEditText.setFocusable(true);
                        mEditText.setFocusableInTouchMode(true);
                        mEditText.requestFocus();
                        InputMethodManager inputManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputManager.showSoftInput(mEditText, InputMethodManager.SHOW_FORCED);
                        break;
                }
                break;
            case R.id.search_button:
                mSearchListener.onSearchClicked();
                break;
        }
    }

    /**
     * Sytle枚举
     */
    public enum STYLE {
        BACK_AND_MORE,
        SINGLE_BACK,
        FULL_SCREEN,
        BACK_AND_EDIT
    }

    /**默认的风格是BACK_AND_MORE*/
    private STYLE mStyle = STYLE.BACK_AND_MORE;

    public Button getRightButton() {
        return mRightButton;
    }

    public void setTitle(String title) {
        mTitleText.setText(title);
    }

    public TextView getTitleText() {
        return mTitleText;
    }

    public EditText getEditText() {
        return mEditText;
    }

    public Button getSearchButton() {
        return mSearchButton;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.base_title_parent);

        mContentParent = (FrameLayout) findViewById(R.id.title_parent);
        mTitle = (RelativeLayout) findViewById(R.id.title);

        mTitleText = (TextView) findViewById(R.id.title_text);

        mLeftButton  = (Button) findViewById(R.id.left);
        //为什么？？？
        mLeftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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

    /**
     * 设置显示风格
     * @param style 显示风格
     */
    public void setStyle(STYLE style) {
        mStyle = style;
        switch(style) {
            case BACK_AND_MORE:
                /*mLeftButton.setClickable(true);
                mLeftButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(v.getId() == R.id.left) {
                            Log.e(TAG, "fuck clicked");
                            finish();
                        }

                    }
                });*/
                //默认的样式，不用做操作
                break;
            case SINGLE_BACK:
                mRightButton.setVisibility(View.GONE);
                break;
            case FULL_SCREEN:
                mTitle.setVisibility(View.GONE);
                updateView();
                break;
            case BACK_AND_EDIT:
                mSearchButton = (Button) findViewById(R.id.search_button);
                mRightButton.setBackgroundResource(R.drawable.edit_press);
                mEditText = (EditText) findViewById(R.id.edit_text);
                //BACK_AND_EDIT模式下，点击RightButton会转换编辑模式。
                mRightButton.setOnClickListener(this);
                mSearchButton.setOnClickListener(this);
                break;
            default:
                break;
        }
    }

    /**
     * 将标题栏转换为编辑模式
     */
    public void changeToEdit() {
        mTitleText.setVisibility(View.GONE);
        mEditText.setVisibility(View.VISIBLE);
        mRightButton.setVisibility(View.GONE);
        mSearchButton.setVisibility(View.VISIBLE);
    }

    /**
     * 将标题栏转换为正常模式
     */
    public void returnToNormal() {
        mTitleText.setVisibility(View.VISIBLE);
        mEditText.setVisibility(View.GONE);
        mRightButton.setVisibility(View.VISIBLE);
        mSearchButton.setVisibility(View.GONE);
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

        if(mContentParent.getChildCount() > 1) {
            mContentParent.removeViewAt(1);
        }
        mContentParent.addView(mContentView, layoutParams);
        updateView();
    }

    private void updateView() {
        if(mContentView != null && mStyle == STYLE.FULL_SCREEN) {
            /**
               Log.e(TAG, "mContentView is not null");
               直接从mContentView中拿到的LayoutParams似乎是NULL，具体为什么不知道。
               mContentView.getLayoutParams();  //the value is NULL
               (mContentParent.addView())，当然是null啊
             */
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

    public void setSearchListener(ISearch listener) {
        this.mSearchListener = listener;
    }

    public interface ISearch {
        void onSearchClicked();
    }
}
