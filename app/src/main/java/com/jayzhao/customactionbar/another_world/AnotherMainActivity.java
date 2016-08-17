package com.jayzhao.customactionbar.another_world;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.BinderThread;
import android.util.Log;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import com.jayzhao.customactionbar.MyBaseTitleActivity;
import com.jayzhao.customactionbar.R;
import com.jayzhao.customactionbar.another_world.Widget.MyProgressView;
import com.jayzhao.customactionbar.another_world.Widget.WeiboLoadingDialog;

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

    @Bind(R.id.custom_view)
    TextView mCustomViewText = null;

    /*@Bind(R.id.my_progress_view)
    MyProgressView mProgressView = null;*/

    @Bind(R.id.start_loading_text)
    TextView mStartLoadingText = null;

    @Bind(R.id.stop_loading_text)
    TextView mStopLoadingText = null;

    private static final String TAG = "AnotherMainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE.SINGLE_BACK, R.color.bg_color_red);
        setContentView(R.layout.another_layout);
        setTitle("Another Home");
        ButterKnife.bind(this);

        /**
         * 模拟进度变化
         */
      /*ValueAnimator progressAnimator = ValueAnimator.ofInt(0, 360);
        progressAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int progress = (int) animation.getAnimatedValue();
                mProgressView.setProgress(progress);
                if(progress == 360) {
                    mProgressView.setCenterText("Loading Done!");
                }
            }
        });
        progressAnimator.setInterpolator(new LinearInterpolator());
        progressAnimator.setDuration(5000);
        progressAnimator.start();*/
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

    @OnClick(R.id.custom_view)
    public void onCustomViewClick() {
        startActivity(new Intent(AnotherMainActivity.this, CustomViewActivity.class));
    }

    @OnClick(R.id.start_loading_text)
    public void startLoading() {

        WeiboLoadingDialog dialog = new WeiboLoadingDialog();
        dialog.show(getSupportFragmentManager(), "WeiboLoadingDialog");

        //mProgressView.startLoading();
    }

    @OnClick(R.id.stop_loading_text)
    public void stopLoading() {
        //mProgressView.stopLoading();
    }
}
