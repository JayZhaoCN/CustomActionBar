package com.jayzhao.customactionbar;

import android.app.Dialog;
import android.content.Context;
import android.text.Layout;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * Created by hm on 16-4-12.
 */
public class MyAlertDialog {
    private Dialog mDialog;
    private Context mContext;

    private int mVisibleNum = 4;

    private TextView text1;
    private TextView text2;
    private TextView text3;
    private TextView text4;

    private MyOnItemlickListener mOnItemlickListener;

    public MyAlertDialog(Context context) {
        mContext = context;
    }



    public Dialog getDialog(int visibleNum) {

        if (visibleNum < 0 || visibleNum > 5)
            throw new IllegalArgumentException("visibleNum is illegal!");
        mVisibleNum = visibleNum;

        mDialog = new Dialog(mContext, R.style.dialog2);
        Window window = mDialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        window.setGravity(Gravity.RIGHT | Gravity.TOP);

        lp.y = mContext.getResources().getDimensionPixelSize(R.dimen.title_height);
        lp.x = 0;

        window.setAttributes(lp);

        mDialog.setContentView(R.layout.dialog_item);

        text1 = (TextView) mDialog.findViewById(R.id.num1);
        text1.setVisibility(View.VISIBLE);
        text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemlickListener.firstItemClick(v);
            }
        });

        if (mVisibleNum > 1) {
            text2 = (TextView) mDialog.findViewById(R.id.num2);
            text2.setVisibility(View.VISIBLE);
            text2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemlickListener.secondItemClick(v);
                }
            });
        }

        if (mVisibleNum > 2) {
            text3 = (TextView) mDialog.findViewById(R.id.num3);
            text3.setVisibility(View.VISIBLE);
            text3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemlickListener.thirdItemClick(v);
                }
            });
        }

        if (mVisibleNum > 3) {
            text4 = (TextView) mDialog.findViewById(R.id.num4);
            text4.setVisibility(View.VISIBLE);
            text4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemlickListener.firstItemClick(v);
                }
            });
        }


        return mDialog;
    }

    public void setmOnItemlickListener(MyOnItemlickListener listener) {
        mOnItemlickListener = listener;
    }

    public interface MyOnItemlickListener {
        void firstItemClick(View v);
        void secondItemClick(View v);
        void thirdItemClick(View v);
        void fourthItemClick(View v);
    }
}
