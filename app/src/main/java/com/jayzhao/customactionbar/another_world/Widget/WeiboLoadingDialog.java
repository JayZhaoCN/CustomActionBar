package com.jayzhao.customactionbar.another_world.Widget;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jayzhao.customactionbar.R;

/**
 * Created by Jay on 16-8-17.
 */
public class WeiboLoadingDialog extends DialogFragment {
    private CustomWeiboLoadingView mCustomWeiboLoadingView = null;

    private static final String TAG = "CustomWeiboLoadingView";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.weiboi_loading, container, false);
        mCustomWeiboLoadingView = (CustomWeiboLoadingView) view.findViewById(R.id.weibo_loading_view);
        //getDialog().setCanceledOnTouchOutside(true);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int style = DialogFragment.STYLE_NO_FRAME;
        /**
         * <item name="android:backgroundDimEnabled">true</item>
         * 表示DialogFragment出现是背景是否会变暗
         * true:变暗
         * false:不变暗
         */
        int theme = R.style.WeiboLoadingDialog;
        setStyle(style, theme);

    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        Log.i(TAG, "onCancel");
        mCustomWeiboLoadingView.stopLoading();
    }
}
