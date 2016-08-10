package com.jayzhao.customactionbar.Widget;

import android.app.Dialog;
import android.content.Context;

import com.jayzhao.customactionbar.MyAlertDialog;

/**
 * Created by Jay on 16-8-10.
 */
public class MyDialogBuilder {
    private Context mContext = null;
    private MyAlertDialog mAlertDialog = null;
    private Dialog mDialog = null;

    public MyDialogBuilder(Context context) {
        mContext = context;
        mAlertDialog = new MyAlertDialog(context);
    }

    public MyDialogBuilder setDatas(int size, String ... title) {
        mDialog = mAlertDialog.getDialog(size, title);
        return this;
    }

    public MyDialogBuilder setListener(MyAlertDialog.MyOnItemClickListener listener ) {
        mAlertDialog.setmOnItemClickListener(listener);
        return this;
    }

    public Dialog getRealDialog() {
        return mDialog;
    }

}
