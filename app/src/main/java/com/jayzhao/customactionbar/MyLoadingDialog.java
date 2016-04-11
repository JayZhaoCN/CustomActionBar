package com.jayzhao.customactionbar;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;

/**
 * Created by hm on 16-4-1.
 */
public class MyLoadingDialog {
    private Dialog mDialog;
    private Context mContext;
    private MyLoadingView mMyLoadingView;

    public MyLoadingDialog(Context context) {
        mContext = context;
        mDialog = new Dialog(mContext, R.style.dialog);
        mDialog.setCanceledOnTouchOutside(false);
        //mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = View.inflate(mContext, R.layout.dialog, null);
        mDialog.setContentView(view);
        mMyLoadingView = (MyLoadingView) mDialog.findViewById(R.id.myLoadingView);
        mMyLoadingView.startLoading();
    }

    public void showDialog() {
        mDialog.show();
    }





}
