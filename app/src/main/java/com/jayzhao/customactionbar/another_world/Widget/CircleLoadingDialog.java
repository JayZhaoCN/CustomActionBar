package com.jayzhao.customactionbar.another_world.Widget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jayzhao.customactionbar.R;

/**
 * Created by Jay on 16-8-29.
 */
public class CircleLoadingDialog extends DialogFragment {
    private static final String TAG = "CircleLoadingDialog";

    private CircleLoadingView mCircleLoadingView = null;
    private OnLoadingDoneListener mListener = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.circle_loading, container, false);
        mCircleLoadingView = (CircleLoadingView) view.findViewById(R.id.circle_loading_view);
        if(mListener != null) {
            mCircleLoadingView.setListener(mListener);
        }
        return view;
    }

    public void setViewListener(OnLoadingDoneListener listener) {
        mListener = listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int style = DialogFragment.STYLE_NORMAL;
        /**
         * <item name="android:backgroundDimEnabled">true</item>
         * 表示DialogFragment出现是背景是否会变暗
         * true:变暗
         * false:不变暗
         */
        int theme = R.style.WeiboLoadingDialog;
        setStyle(style, theme);
        //在onCreate()方法中拿不到Dialog
        //getDialog().getWindow().setGravity(Gravity.TOP);
    }
}
