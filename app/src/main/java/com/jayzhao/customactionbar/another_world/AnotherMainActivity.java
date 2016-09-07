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
import com.jayzhao.customactionbar.another_world.Widget.CircleLoadingDialog;
import com.jayzhao.customactionbar.another_world.Widget.CustomWeiboLoadingView;
import com.jayzhao.customactionbar.another_world.Widget.MyProgressView;
import com.jayzhao.customactionbar.another_world.Widget.OnLoadingDoneListener;
import com.jayzhao.customactionbar.another_world.Widget.WeiboLoadingDialog;
import com.jayzhao.customactionbar.another_world.Widget.WeiboPictureDialog;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

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
                mProgressView.setVisibility(View.INVISIBLE);
                final CircleLoadingDialog dialog = new CircleLoadingDialog();
                dialog.setViewListener(new OnLoadingDoneListener() {
                    @Override
                    public void onLoadingDone(View view) {
                        dialog.dismiss();
                        mProgressView.setVisibility(View.VISIBLE);
                    }
                });
                dialog.show(getSupportFragmentManager(), "CircleLoadingDialog");
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

        //Observable: 被观测者
        Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                //在call方法中被观察者Observable在执行代码，并在必要的时候触发事件，通知观察者Observer
                //持有Subscriber的引用，方便向观察者通知事件的发生
                subscriber.onNext("Hello");
                subscriber.onNext("Hi");
                subscriber.onNext("NiHao");
                subscriber.onCompleted();
            }
        });

        //Observer: 观察者，是一个接口
        //Subscriber: 订阅者，是实现了Observer的一个抽象类
        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Log.i(TAG, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "onError");
            }

            @Override
            public void onNext(String s) {
                Log.i(TAG, "onNext: " + s);
            }
        };

        //Observable和Subscriber形成订阅关系
        //注意写法
        //Subscriber实现了Observer和Subscription接口，这里返回一个Subscription是为了方便取消订阅
        Subscription subscription = observable.subscribe(subscriber);
        //解除订阅
        //subscription.unsubscribe();

        String[] names = {"wrr", "fgh", "sdf", "asd"};

        //这段代码这样理解：
        //首先：Observable.from()方法返回一个Observable对象
        //其次，调用subscribe方法，Observable对象被订阅
       /* Observable.from(names)
                .subscribeOn(Schedulers.io())  //被观察者在IO线程
                .observeOn(AndroidSchedulers.mainThread())  //观察者在Android的主线程
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        Log.i(TAG, s);
                    }
                });*/

        //上面这段代码还有另外的写法
        /*Observable.from(names)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.i(TAG, s);
                    }
                });*/

        Observable.just("wrr", "fgh", "sdf", "asd")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.i(TAG, s);
                    }
                });
        //RXJava中创建时间序列的方法有三种：
        //Observable.create();
        //Observable.just();
        //Observable.from();
        //每个事件队列中的对象类型可能都不一样，通过泛型的方式确定。可以是String，也可以是其他任何类型
        //可以对事件的类型进行变换
        //具体如何变换，暂时我也不清楚，不太理解
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
