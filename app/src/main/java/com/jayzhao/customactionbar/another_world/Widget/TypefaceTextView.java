package com.jayzhao.customactionbar.another_world.Widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by Jay on 16-8-25.
 */
public class TypefaceTextView extends TextView {
    private static final String TAG = "TypefaceTextView";

    public static final String MIUI_BOLD = "miui_bold";
    public static final String MIUI_LIGHT = "miui_light";
    public static final String MIUI_NORMAL = "miui_normal";
    public static final String PT_DIN = "pt_din";
    public static final String DIN_MED = "din_med";

    public TypefaceTextView(Context context) {
        this(context, null);
    }

    public TypefaceTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TypefaceTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, new int[]{android.R.attr.fontFamily}, 0, defStyleAttr);
        Log.i(TAG, android.R.attr.fontFamily + "");
        String typeFaceStr = ta.getString(0);
        Log.i(TAG, "typeFaceStr: " + typeFaceStr);
        ta.recycle();

        String typeFacePath = null;
        if (typeFaceStr == null) {
            return;
        } else if (typeFaceStr.equals(MIUI_BOLD)) {
            typeFacePath = "fonts/MIUI-Bold.ttf";
        } else if (typeFaceStr.equals(MIUI_LIGHT)) {
            typeFacePath = "fonts/MIUI-Light.ttf";
        } else if (typeFaceStr.equals(MIUI_NORMAL)) {
            typeFacePath = "fonts/MIUI-Normal.ttf";
        } else if (typeFaceStr.equals(PT_DIN)) {
            typeFacePath = "fonts/pt_din_condensed_cyrillic.ttf";
        } else if (typeFaceStr.equals(DIN_MED)) {
            typeFacePath = "fonts/dincond_medium.otf";
        }
        Log.i(TAG, "typeFacePath: " + typeFacePath);
        super.setTypeface(Typeface.createFromAsset(context.getAssets(), typeFacePath));
    }
}
