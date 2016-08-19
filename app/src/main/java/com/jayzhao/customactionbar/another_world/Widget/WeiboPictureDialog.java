package com.jayzhao.customactionbar.another_world.Widget;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;

import com.jayzhao.customactionbar.R;

/**
 * Created by Jay on 16-8-18.
 */
public class WeiboPictureDialog extends DialogFragment {
    private LoadingPictureView loadingPictureView = null;
    private ValueAnimator mAnimator = null;

    private static final String TAG = "WeiboPictureDialog";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.picture_loading, container, false);
        loadingPictureView = (LoadingPictureView) view.findViewById(R.id.weibo_picture_view);
        mAnimator = ValueAnimator.ofInt(0, 360);
        mAnimator.setInterpolator(new LinearInterpolator());
        mAnimator.setDuration(5000);
        /**
         * 这里是否已经出现了内存泄露？
         */
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                loadingPictureView.setProgress((int) animation.getAnimatedValue());
                if((int) animation.getAnimatedValue() == 360) {
                    dismiss();
                }
            }
        });
        mAnimator.start();
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int style = DialogFragment.STYLE_NO_FRAME;
        int theme = R.style.WeiboLoadingDialog;
        setStyle(style, theme);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
        /**
         * 这段代码可以修复一个bug:
         * 当屏幕发生旋转时，DialogFragment会被销毁，并重建。但是销毁之前的mAnimator还在，当mAnimator结束后，
         * 去dismiss这个DialogFragment时，会发现，原先的DialogFragment已经是null了
         * 所以需要在原先DialogFragment销毁（onDestroy）时把这个动画取消掉
         */
        if(mAnimator != null) {
            mAnimator.cancel();
            mAnimator = null;
        }
    }
}
