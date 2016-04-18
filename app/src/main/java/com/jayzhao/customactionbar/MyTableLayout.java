package com.jayzhao.customactionbar;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * Created by hm on 16-4-12.
 */
public class MyTableLayout extends MyBaseTitleActivity implements View.OnClickListener {

    private Dialog mDialog = null;
    private Context mContext = null;

    private MyAlertDialog mMyAlertDialog = null;

    private TextView mRightButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.table_layout);
        this.setStyle(STYLE.BACK_AND_MORE);
        this.setTitle("My TableLayout");

        mContext = this;

        mRightButton = getRightButton();
        mRightButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.right:
                mMyAlertDialog = new MyAlertDialog(mContext);
                mDialog = mMyAlertDialog.getDialog(2, "First Layout", "Second Layout");
                mDialog.show();

                mMyAlertDialog.setmOnItemClickListener(new MyAlertDialog.MyOnItemClickListener() {
                    @Override
                    public void firstItemClick(View v) {
                        MyTableLayout.this.setContentView(R.layout.table_layout);
                        mDialog.cancel();
                    }

                    @Override
                    public void secondItemClick(View v) {
                        MyTableLayout.this.setContentView(R.layout.table_layout2);
                        mDialog.cancel();
                    }

                    @Override
                    public void thirdItemClick(View v) {

                    }

                    @Override
                    public void fourthItemClick(View v) {

                    }
                });
                break;
        }
    }
}
