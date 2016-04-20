package com.jayzhao.customactionbar;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by hm on 16-4-1.
 */
public class MyLoadingDialog {
    private Dialog mDialog;
    private Context mContext;
    private MyLoadingView mMyLoadingView;
    private ImageView mImageView;
    private TextView mText;

    private final String TAG = "MyLoadingDialog";

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 1) {
                mDialog.dismiss();
                mDialog = null;
            }
        }
    };


    public MyLoadingDialog(Context context) {
        mContext = context;
        mDialog = new Dialog(mContext, R.style.dialog);
        mDialog.setCanceledOnTouchOutside(false);
        //mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = View.inflate(mContext, R.layout.dialog, null);
        mDialog.setContentView(view);
        mMyLoadingView = (MyLoadingView) mDialog.findViewById(R.id.myLoadingView);
        mMyLoadingView.startLoading();
        mImageView = (ImageView) mDialog.findViewById(R.id.errorImage);
        mText = (TextView) mDialog.findViewById(R.id.tips);

        mDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mMyLoadingView.stopLoading();
            }
        });
    }

    public void showDialog() {
        mDialog.show();
    }

    public void setError() {
        Log.e(TAG, "setError");
        mMyLoadingView.setVisibility(View.GONE);
        mImageView.setVisibility(View.VISIBLE);
        mText.setText("加载失败>_<");

        mMyLoadingView.stopLoading();

        mHandler.sendEmptyMessageDelayed(1, 1000);
    }
}
