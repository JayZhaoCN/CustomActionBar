package com.jayzhao.customactionbar;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jayzhao.customactionbar.Widget.MyDialogFragment;
import com.jayzhao.customactionbar.another_world.AnotherMainActivity;
import com.jayzhao.customactionbar.another_world.FragmentAnimationActivity;
import com.jayzhao.customactionbar.another_world.WeekActiveTimeActivity;
import com.jayzhao.customactionbar.another_world.WheelMainActivity;

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
                activity.mTableText.setText("TableLayout");
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);

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
                    }

                    @Override
                    public void thirdItemClick(View v, Dialog realDialog) {
                        MyUtils.showCustomToast(MainActivity.this, "Jay Zhao", "Custom Toast", R.mipmap.ic_launcher);
                        myDialog.dismiss();
                    }

                    @Override
                    public void fourthItemClick(View v, Dialog realDialog) {

                    }
                });
                myDialog.show();
            }
        });
    }

    public void click(View view) {
        //Intent intent = new Intent(MainActivity.this, MyWebActivity.class);
        //intent.putExtra("URL", "https://www.baidu.com/");
        //startActivity(intent);
        startActivity(new Intent(MainActivity.this, JayActivity.class));
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
                intent.putExtra("URL", "http://www.hao123.com");
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
