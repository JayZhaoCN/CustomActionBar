package com.jayzhao.customactionbar;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * Created by Jay on 16-4-12.
 */
public class MyAlertDialog {
    private Dialog mDialog;
    private Context mContext;

    private int mVisibleNum = 4;

    private TextView text1;
    private TextView text2;
    private TextView text3;
    private TextView text4;

    private MyOnItemClickListener mOnItemClickListener;

    public MyAlertDialog(Context context) {
        mContext = context;
    }

    public Dialog getDialog() {
        return mDialog;
    }


    public Dialog getDialog(int visibleNum, String ...textTitle) {
        try {
            if (visibleNum < 0 || visibleNum > 5)
                throw new IllegalArgumentException("visibleNum is illegal!");
            mVisibleNum = visibleNum;

            mDialog = new Dialog(mContext, R.style.dialog2);
            Window window = mDialog.getWindow();
            WindowManager.LayoutParams lp = window.getAttributes();
            window.setWindowAnimations(R.style.dialogWindowAnim); //设置窗口弹出动画

            window.setGravity(Gravity.RIGHT | Gravity.TOP);
            //默认是居中显示的，y为正，则向下移动，y为负，则向上移动。x正，则向右移动，x负，则向左移动。
            lp.y = mContext.getResources().getDimensionPixelSize(R.dimen.title_height);
            lp.x = 0;

            window.setAttributes(lp);

            mDialog.setContentView(R.layout.dialog_item);

            text1 = (TextView) mDialog.findViewById(R.id.num1);
            text1.setText(textTitle[0]);
            text1.setVisibility(View.VISIBLE);
            text1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.firstItemClick(v, mDialog);
                }
            });

            if (mVisibleNum > 1) {
                text2 = (TextView) mDialog.findViewById(R.id.num2);
                text2.setText(textTitle[1]);
                text2.setVisibility(View.VISIBLE);
                text2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickListener.secondItemClick(v, mDialog);
                    }
                });
            }

            if (mVisibleNum > 2) {
                text3 = (TextView) mDialog.findViewById(R.id.num3);
                text3.setText(textTitle[2]);
                text3.setVisibility(View.VISIBLE);
                text3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickListener.thirdItemClick(v, mDialog);
                    }
                });
            }

            if (mVisibleNum > 3) {
                text4 = (TextView) mDialog.findViewById(R.id.num4);
                text4.setText(textTitle[3]);
                text4.setVisibility(View.VISIBLE);
                text4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickListener.fourthItemClick(v, mDialog);
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return mDialog;
    }

    public void setmOnItemClickListener(MyOnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public interface MyOnItemClickListener {
        void firstItemClick(View v, Dialog realDialog);
        void secondItemClick(View v,Dialog realDialog);
        void thirdItemClick(View v, Dialog realDialog);
        void fourthItemClick(View v, Dialog realDialog);
    }

    public static class MyOnItemClickListenerAdapter implements MyOnItemClickListener {

        @Override
        public void firstItemClick(View v, Dialog dialog) {

        }

        @Override
        public void secondItemClick(View v, Dialog dialog) {

        }

        @Override
        public void thirdItemClick(View v, Dialog dialog) {

        }

        @Override
        public void fourthItemClick(View v, Dialog dialog) {

        }
    }
}
