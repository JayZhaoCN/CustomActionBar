package com.jayzhao.customactionbar;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jayzhao.customactionbar.Widget.MyDialogFragment;
import com.jayzhao.customactionbar.another_world.AnotherMainActivity;
import com.jayzhao.customactionbar.another_world.CustomViewActivity;
import com.jayzhao.customactionbar.another_world.DownloadActivity;
import com.jayzhao.customactionbar.another_world.FragmentAnimationActivity;
import com.jayzhao.customactionbar.another_world.WeekActiveTimeActivity;
import com.jayzhao.customactionbar.another_world.WheelMainActivity;
import com.jayzhao.customactionbar.another_world.mvp.LoginActivity;
import com.jayzhao.customactionbar.another_world.mvp.LoginListener;
import com.jayzhao.customactionbar.another_world.weekactive.TestWeekActiveActivity;

import java.io.File;
import java.lang.ref.WeakReference;


public class MainActivity extends MyBaseTitleActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private TextView mRecyclerViewText;
    private TextView mTableText;
    private TextView mWebText;
    private TextView mChangeAnimation;
    private TextView mPromptDialogText;
    private TextView mNextPage;
    private TextView mFullScreen;

    private MyLoadingDialog mDialog;

    private MyHandler mMyHandler = null;
    private WeakHandler mWeakHandler = null;
    private DownloadHandler mDownloadHandler = null;

    private BroadcastReceiver mReceiver;

    class MyHandler extends Handler {
        @Override
        public void dispatchMessage(Message msg) {
            /**
             * 额，虽然我复写了dispatchMessgae()方法，但是，额，也调用了super.dispatchMessage(msg)啊
             * 我好蠢...
             */
            super.dispatchMessage(msg);
            Log.e(TAG, "disaptch message" + ", data is: "+ msg.getData().getString(TAG));
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.e(TAG, "handle message");
        }
    }

    static class DownloadHandler extends Handler {
        WeakReference<MainActivity> mWeakReference = null;

        public DownloadHandler(MainActivity activity) {
            mWeakReference = new WeakReference<MainActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    Log.i(TAG, "Download started!");
                    long reference = msg.getData().getLong("reference");
                    break;

            }
        }
    }

    /**
     * 如何避免在使用Handler时导致的内存泄露？
     * 很简单，把需要使用的Handler定义为静态内部类即可。
     * 静态内部类不会持有外部类的引用，故不会导致内存泄露。
     * 详见：http://www.cnblogs.com/xujian2014/p/5025650.html
     */
    static class WeakHandler extends Handler {
        WeakReference<MainActivity> mWeakReference = null;
        public WeakHandler(MainActivity activity) {
            mWeakReference = new WeakReference<MainActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            final MainActivity activity = mWeakReference.get();
            if(msg.what == 1) {
                activity.mTableText.setText("Weak Reference");
                sendEmptyMessageDelayed(2, 1000);
            } else if(msg.what == 2) {
                activity.mTableText.setText("TableLayout  tel: 18119602814");
            }
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 1) {
                mDialog.setSuccess();
            }
            super.handleMessage(msg);
        }
    };

    /**
     * 关于onSaveInstanceState()和onRestoreInstanceState()这两个方法，请看这篇博文：
     * http://www.cnblogs.com/hanyonglu/archive/2012/03/28/2420515.html
     */



    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause>>>>>>");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume>>>>>>");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart>>>>>>");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
        Log.i(TAG, "onDestoroy>>>>>>");
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        Log.i(TAG, "onSaveInstanceState>>>>>>");
        savedInstanceState.putString("Name:", "Jay Zhao");
        savedInstanceState.putInt("Age:", 23);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop>>>>>>");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(TAG, "onRestoreInstanceState>>>>>>");
    }

    private void initViews() {
        mFullScreen = (TextView) findViewById(R.id.full_screen);
        mFullScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, WeekActiveTimeActivity.class));
            }
        });

        mFullScreen.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                startActivity(new Intent(MainActivity.this, JayActivity.class));
                return true;
            }
        });
    }

    private ValueAnimator mColorAnimator = null;

    private void initAnimator() {
        if(mColorAnimator == null) {
            //关于Color的动画，调用ValueAnimator.ofArgb(int ...values)
            mColorAnimator = ValueAnimator.ofInt
                    (ContextCompat.getColor(this, R.color.bg_color_red),
                            ContextCompat.getColor(this, R.color.colorPrimary), ContextCompat.getColor(this, R.color.blue_light));
            //如果调用了ofArgb方法，就不用再去设置Evaluator了
            mColorAnimator.setEvaluator(new ArgbEvaluator());
            mColorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                int currentColor;
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    currentColor = (int) animation.getAnimatedValue();
                    setColorValue(currentColor);
                }
            });
            mColorAnimator.setRepeatCount(ValueAnimator.INFINITE);
            mColorAnimator.setDuration(5000);
            mColorAnimator.setRepeatMode(ValueAnimator.REVERSE);
            mColorAnimator.setInterpolator(new LinearInterpolator());
            mColorAnimator.start();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
        initAnimator();

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        float density = metrics.density;
        int densityDPI = metrics.densityDpi;

        /**
         * 使用dp可以保证在不同的设备上显示效果大致相同
         */
        Log.i(TAG + " widthPixels", width + "");
        Log.i(TAG + " heightPixels", height + "");
        Log.i(TAG + " density", density + "");
        Log.i(TAG + " densityDPI", densityDPI + "");

        initViews();

        if(savedInstanceState != null) {
            Log.i(TAG, "savedInstanceState: >>>>>>" + savedInstanceState.getString("Name:"));
        }

        Log.i(TAG, "onCreate>>>>>>");
        setStyle(STYLE.BACK_AND_MORE);

        /**
         * 测试Handler
         * 设置Message的mCallback
         * 在Handler发送Message后，Looper对象会调用Handler的dispatchMessage方法，从而调用Message的mCallback
         */
        mMyHandler = new MyHandler();
        Message msg = Message.obtain(mMyHandler, new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "Message Callback");
            }
        });
        Bundle data = new Bundle();
        data.putString(TAG, "Good");
        msg.setData(data);
        mMyHandler.sendMessage(msg);

        mWeakHandler = new WeakHandler(this);
        mWeakHandler.sendEmptyMessageDelayed(1, 1000);
        mDownloadHandler = new DownloadHandler(this);


        mRecyclerViewText = (TextView) findViewById(R.id.recyclerViewText);
        mRecyclerViewText.setOnClickListener(this);

        mRecyclerViewText.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(MainActivity.this, BezierActivity.class);
                startActivity(intent);
                return true;
            }
        });

        mTableText = (TextView) findViewById(R.id.tableLayoutText);
        mTableText.setOnClickListener(this);
        mTableText.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                startActivity(new Intent(MainActivity.this, TestViewActivity.class));
                return true;
            }
        });

        mWebText = (TextView) findViewById(R.id.webText);
        mWebText.setOnClickListener(this);

        mChangeAnimation = (TextView) findViewById(R.id.changeAnimation);
        mChangeAnimation.setOnClickListener(this);
        mChangeAnimation.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                startActivity(new Intent(MainActivity.this, AnotherMainActivity.class));
                return true;
            }
        });

        mPromptDialogText = (TextView) findViewById(R.id.promptDiglog);
        mPromptDialogText.setOnClickListener(this);
        mPromptDialogText.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                startActivity(new Intent(MainActivity.this, TestWeekActiveActivity.class));
                return true;
            }
        });

/*        mPromptDialogText.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                DownloadManager manager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                Uri uri = Uri.parse("http://www.wandoujia.com/apps/com.xiaomi.hm.health/binding?source=wandoujia-web_inner_referral_binded");
                DownloadManager.Request request = new DownloadManager.Request(uri);
                //设置下载标题
                request.setTitle("JayZhao");
                //设置下载的详细描述
                request.setDescription("asdfalifguyqageruy");
                //很奇怪，为什么下不到外部存储中，总是只能下载到内部存储？
                //这种写法会下载到内部存储的jay_file目录下
                //request.setDestinationInExternalPublicDir("/download/", "jayzhao.apk");
                //request.setDestinationInExternalPublicDir("jay_file", "jayzhao.apk");
                //这种写法会下载到内部存储的DOWNLOAD目录下
                //request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "jayzhao.apk");
                //下到专用文件夹中
                //request.setDestinationInExternalFilesDir(MainActivity.this, Environment.DIRECTORY_DOWNLOADS, "jayzhao.apk");

                //关于DownloadManager
                //可以参考这篇博文：http://www.open-open.com/lib/view/open1428024407365.html

                //下载到指定路径
                File downloadFile = new File("/storage/sdcard1/", "jayzhao.apk");
                request.setDestinationUri(Uri.fromFile(downloadFile));

                //reference是此次下载的唯一ID
                long reference = manager.enqueue(request);
                Bundle bundle = new Bundle();
                bundle.putLong("reference", reference);
                Message message = Message.obtain();
                message.setData(bundle);
                message.what = 1;
                mDownloadHandler.sendMessage(message);
                return true;
            }
        });*/

        mNextPage = (TextView) findViewById(R.id.nextPage);
        mNextPage.setOnClickListener(this);
        mNextPage.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                startActivity(new Intent(MainActivity.this, FragmentAnimationActivity.class));
                return true;
            }
        });

        getRightButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MyAlertDialog dialog = new MyAlertDialog(MainActivity.this);
                final Dialog myDialog = dialog.getDialog(3, "Toast", "Custom Toast", "Custom Toast 2");
                dialog.setmOnItemClickListener(new MyAlertDialog.MyOnItemClickListener() {
                    @Override
                    public void firstItemClick(View v, Dialog realDialog) {
                        startActivity(new Intent(MainActivity.this, WheelMainActivity.class));
                        MyUtils.showToast(MainActivity.this, "Custom Toast");
                        myDialog.dismiss();
                    }

                    @Override
                    public void secondItemClick(View v, Dialog realDialog) {
                        MyUtils.showIconToast(MainActivity.this, "Icon Toast!", R.mipmap.ic_launcher);
                        myDialog.dismiss();
                        startActivity(new Intent(MainActivity.this, DownloadActivity.class));
                    }

                    @Override
                    public void thirdItemClick(View v, Dialog realDialog) {
                        MyUtils.showCustomToast(MainActivity.this, "Jay Zhao", "Custom Toast", R.mipmap.ic_launcher);
                        startActivity(new Intent(MainActivity.this, CustomViewActivity.class));
                        myDialog.dismiss();
                    }

                    @Override
                    public void fourthItemClick(View v, Dialog realDialog) {

                    }
                });
                myDialog.show();
            }
        });


        IntentFilter intentFilter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);

        //注册一个接收下载完成的广播接收器
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                long reference = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                Log.i(TAG, "reference is: " + reference);
            }
        };
        registerReceiver(mReceiver, intentFilter);
    }

    public void click(View view) {
        Intent intent = new Intent(MainActivity.this, MyWebActivity.class);
        intent.putExtra("URL", "http://mobile.baidu.com/item?pid=1120893&from=1019105c");
        startActivity(intent);
        //startActivity(new Intent(MainActivity.this, JayActivity.class));
    }

    static class MyThread extends Thread {
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.recyclerViewText:
                startActivity(new Intent(MainActivity.this, RecyclerViewActivity.class));
                break;
            case R.id.tableLayoutText:
                startActivity(new Intent(MainActivity.this, MyTableLayout.class));
                break;
            case R.id.webText:
                intent = new Intent(MainActivity.this, MyWebActivity.class);
                intent.putExtra("URL", "http://mobile.baidu.com/item?pid=1120893&from=1019105c");
                startActivity(intent);
                break;
            case R.id.changeAnimation:
                intent = new Intent(MainActivity.this, ChangeAnimation.class);
                startActivity(intent);
                break;
            case R.id.promptDiglog:
                mDialog = new MyLoadingDialog(this);
                mDialog.showDialog();
                mDialog.setOnLoadingDoneListener(new MyLoadingDialog.OnLoadingDoneListener() {
                    @Override
                    public void onLoadingDone() {
                        MyUtils.showToast(MainActivity.this, "done");
                    }
                });
                mHandler.sendEmptyMessageDelayed(1, 2000);
                break;
            case R.id.nextPage:
                startActivity(new Intent(MainActivity.this, NextActivity.class));
                break;
        }
    }
}
