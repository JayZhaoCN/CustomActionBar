package com.jayzhao.customactionbar.another_world.Widget;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.picture_loading, container, false);
        loadingPictureView = (LoadingPictureView) view.findViewById(R.id.weibo_picture_view);
        ValueAnimator animator = ValueAnimator.ofInt(0, 360);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(5000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                loadingPictureView.setProgress((int) animation.getAnimatedValue());
                if((int) animation.getAnimatedValue() == 360) {
                    dismiss();
                }
            }
        });
        animator.start();
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int style = DialogFragment.STYLE_NO_FRAME;
        int theme = R.style.WeiboLoadingDialog;
        setStyle(style, theme);
    }
}
