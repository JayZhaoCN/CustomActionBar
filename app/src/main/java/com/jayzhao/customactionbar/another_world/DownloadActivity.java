package com.jayzhao.customactionbar.another_world;

import android.app.DownloadManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.jayzhao.customactionbar.MyBaseTitleActivity;
import com.jayzhao.customactionbar.R;
import com.jayzhao.customactionbar.another_world.Widget.DownloadProgressView;

import java.io.File;
import java.lang.ref.WeakReference;

/**
 * Created by Jay on 16-9-1.
 */
public class DownloadActivity extends MyBaseTitleActivity implements View.OnClickListener {
    private static final String TAG = "DownloadActivity";

    private TextView mStartText = null;
    private TextView mPauseText = null;
    private DownloadProgressView mProgressView = null;

    private DownloadManager mDownloadManager = null;
    private long mReference = 0;
    private DownloadThread mThread = null;
    private WeakHandler mHandler = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        setStyle(STYLE.BACK_AND_MORE);
        initViews();
        mDownloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        mHandler = new WeakHandler(this);
    }

    private void initViews() {
        mStartText = (TextView) findViewById(R.id.start_btn);
        mPauseText = (TextView) findViewById(R.id.pause_btn);
        mProgressView = (DownloadProgressView) findViewById(R.id.download_progress);
        mStartText.setOnClickListener(this);
        mPauseText.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch(id) {
            case R.id.start_btn:
                Uri uri = Uri.parse("http://www.wandoujia.com/apps/com.xiaomi.hm.health/binding?source=wandoujia-web_inner_referral_binded");
                DownloadManager.Request request = new DownloadManager.Request(uri);
                request.setTitle("Jay");
                request.setDescription("Android DownloadManager Test");
                File downloadFile = new File("/storage/sdcard1/", "jayzhao.apk");
                request.setDestinationUri(Uri.fromFile(downloadFile));
                //加入下载队列，正在下载
                mReference = mDownloadManager.enqueue(request);
                mThread = new DownloadThread(this);
                mThread.start();
                break;
            case R.id.pause_btn:
                break;
        }
    }

    /**
     * 使用静态内部类，防止内存泄露
     */
    static class DownloadThread extends Thread {
        private WeakReference<DownloadActivity> mWeakReference = null;
        private long total = 0;
        private long done = 0;

        public DownloadThread(DownloadActivity activity) {
            mWeakReference = new WeakReference<DownloadActivity>(activity);
        }

        @Override
        public void run() {
            super.run();
            DownloadActivity activity = null;
            DownloadManager.Query query = new DownloadManager.Query();
            while(true) {
                activity = mWeakReference.get();
                if(activity == null) {
                    return;
                }
                Log.i(TAG, "" + activity.mReference);
                query.setFilterById(activity.mReference);
                //这里在执行到一个特定的进度0.61附近时就会抛出异常，具体原因未知
                //为毛啊!
                //真是坑爹
                Cursor cursor = activity.mDownloadManager.query(query);
                if(cursor != null && cursor.moveToFirst()) {
                    done =  cursor.getLong(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                    total = cursor.getLong(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                    if(done == total) {
                        break;
                    }
                    Log.i(TAG, "progress: " + done * 100 / total);
                    activity.mHandler.sendEmptyMessage((int) (done * 100 / total));

                }
            }
        }
    }

    static class WeakHandler extends Handler {
        WeakReference<DownloadActivity> mWeakReference = null;
        public WeakHandler(DownloadActivity activity) {
            mWeakReference = new WeakReference<DownloadActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            DownloadActivity activity = mWeakReference.get();
            if(activity == null) {
                return;
            }
            int progress = msg.what;
            activity.mProgressView.setProgress(progress);
        }
    }
}
