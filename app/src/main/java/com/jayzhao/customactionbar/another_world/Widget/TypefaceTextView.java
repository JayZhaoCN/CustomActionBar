package com.jayzhao.customactionbar.another_world.Widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by Jay on 16-8-25.
 * 可以自定义字体的TextView.
 * 用法：
 * 在layout xml文件中使用TypefaceTextView，并增加
 * android:fontFamily="miui_light"
 * android:fontFamily="miui_bold"
 * android:fontFamily="miui_normal"
 * android:fontFamily="din_med"
 * android:fontFamily="pt_din"
 * 共五种
 */
public class TypefaceTextView extends TextView {
    private static final String TAG = "TypefaceTextView";

    /**
     * 字体枚举
     */
    public enum Font {
        MIUI_BOLD("miui_bold", "fonts/MIUI-Bold.ttf"),
        MIUI_LIGHT("miui_light", "fonts/MIUI-Light.ttf"),
        MIUI_NORMAL("miui_normal", "fonts/MIUI-Normal.ttf"),
        PT_DIN("pt_din", "fonts/pt_din_condensed_cyrillic.ttf"),
        DIN_MED("din_med", "fonts/dincond_medium.otf");

        private String mFontName = null;
        private String mFontPath = null;

        Font(String fontName, String fontPath) {
            this.mFontName = fontName;
            this.mFontPath = fontPath;
        }
    }

    public TypefaceTextView(Context context) {
        this(context, null);
    }

    public TypefaceTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TypefaceTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        /**
         * obtainStyledAttributes()
         * params 1: atrrs 集合
         * params 2: attr数组，自己定义的
         * don't know the means of params 3 and 4, unhappy...
         */
        TypedArray ta = context.obtainStyledAttributes(attrs, new int[]{android.R.attr.fontFamily}, 0, defStyleAttr);
        Log.i(TAG, android.R.attr.fontFamily + "");
        String typeFaceStr = ta.getString(0);
        Log.i(TAG, "typeFaceStr: " + typeFaceStr);
        ta.recycle();

        String typeFacePath = null;
        if (typeFaceStr == null) {
            return;
        } else if (typeFaceStr.equals(Font.MIUI_BOLD.mFontName)) {
            typeFacePath = Font.MIUI_BOLD.mFontPath;
        } else if (typeFaceStr.equals(Font.MIUI_LIGHT.mFontName)) {
            typeFacePath = Font.MIUI_LIGHT.mFontPath;
        } else if (typeFaceStr.equals(Font.MIUI_NORMAL.mFontName)) {
            typeFacePath = Font.MIUI_LIGHT.mFontPath;
        } else if (typeFaceStr.equals(Font.PT_DIN.mFontName)) {
            typeFacePath = Font.PT_DIN.mFontPath;
        } else if (typeFaceStr.equals(Font.DIN_MED.mFontName)) {
            typeFacePath = Font.DIN_MED.mFontPath;
        }
        Log.i(TAG, "typeFacePath: " + typeFacePath);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), typeFacePath));
    }
}
