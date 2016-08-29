package com.jayzhao.customactionbar.another_world;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.BinderThread;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import com.jayzhao.customactionbar.MyBaseTitleActivity;
import com.jayzhao.customactionbar.R;
import com.jayzhao.customactionbar.another_world.Widget.CustomWeiboLoadingView;
import com.jayzhao.customactionbar.another_world.Widget.MyProgressView;
import com.jayzhao.customactionbar.another_world.Widget.OnLoadingDoneListener;
import com.jayzhao.customactionbar.another_world.Widget.WeiboLoadingDialog;
import com.jayzhao.customactionbar.another_world.Widget.WeiboPictureDialog;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Jay on 16-8-8.
 */
public class AnotherMainActivity extends MyBaseTitleActivity {
    @Bind(R.id.recycler_view_new)
    /**
     * 额，这里不可以是private的
     */
    TextView mRecyclerText = null;

    @Bind(R.id.list_view)
    TextView mListText = null;

    @Bind(R.id.my_progress_view)
    MyProgressView mProgressView = null;

    @Bind(R.id.start_loading_text)
    TextView mStartLoadingText = null;

    @Bind(R.id.stop_loading_text)
    TextView mStopLoadingText = null;

    private TextView mCircleLoadingText = null;

    private static final String TAG = "AnotherMainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE.SINGLE_BACK, R.color.bg_color_red);
        setContentView(R.layout.another_layout);
        setTitle("Another Home");
        ButterKnife.bind(this);

        mCircleLoadingText = (TextView) findViewById(R.id.circle_loading_text);
        mCircleLoadingText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        /**
         * 模拟进度变化
         */
      final ValueAnimator progressAnimator = ValueAnimator.ofInt(0, 360);
        progressAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int progress = (int) animation.getAnimatedValue();
                mProgressView.setProgress(progress);
                if (progress == 360) {
                    mProgressView.setCenterText("Loading Done!");
                }
            }
        });
        progressAnimator.setInterpolator(new LinearInterpolator());
        progressAnimator.setDuration(5000);
        mProgressView.postDelayed(new Runnable() {
            @Override
            public void run() {
                progressAnimator.start();
            }
        }, 3000);

        /*ObjectAnimator rotateX = ObjectAnimator.ofFloat(mListText, "rotationX", 0, 180, 0);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(mListText, "alpha", 0, 1, 0);
        rotateX.setDuration(2000);
        alpha.setDuration(2000);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(rotateX, alpha);
        set.start();*/

        PropertyValuesHolder rotationHolder = PropertyValuesHolder.ofFloat("Rotation", 60f, -60f, 40f, -40f, -20f, 20f, 10f, -10f, 0f);
        PropertyValuesHolder colorHolder = PropertyValuesHolder.ofInt("BackgroundColor", 0xffffffff, 0xffff00ff, 0xffffff00, 0xffffffff);
        ObjectAnimator animator1 = ObjectAnimator.ofPropertyValuesHolder(mListText, rotationHolder, colorHolder);
        animator1.setDuration(3000);
        animator1.setInterpolator(new AccelerateInterpolator());
        animator1.start();
    }

    @OnClick(R.id.recycler_view_new)
    public void onClick() {
        Log.i(TAG, "ButterKnife");
        startActivity(new Intent(AnotherMainActivity.this, MyDetailActivity.class));
    }

    @OnClick(R.id.list_view)
    public void onListClick() {
        startActivity(new Intent(AnotherMainActivity.this, MyListActivity.class));
    }

    @OnClick(R.id.start_loading_text)
    public void startLoading() {

        mProgressView.setVisibility(View.INVISIBLE);

        final WeiboLoadingDialog dialog = new WeiboLoadingDialog();
        dialog.show(getSupportFragmentManager(), "WeiboLoadingDialog");

        dialog.setViewListener(new CustomWeiboLoadingView.OnLoadingCompleteListener() {
            @Override
            public void onComplete(View view) {
                mProgressView.setVisibility(View.VISIBLE);
                dialog.dismiss();
            }
        });
        mProgressView.startLoading();
    }

    @OnClick(R.id.stop_loading_text)
    public void stopLoading() {
        mProgressView.setVisibility(View.INVISIBLE);
        final WeiboPictureDialog dialog = new WeiboPictureDialog();
        dialog.setListener(new OnLoadingDoneListener() {
            @Override
            public void onLoadingDone(View view) {
                dialog.dismiss();
                mProgressView.setVisibility(View.VISIBLE);
            }
        });
        dialog.show(getSupportFragmentManager(), "WeiboPictureDialog");
    }
}
