package com.jayzhao.customactionbar;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.RadioButton;

import com.jayzhao.customactionbar.Widget.Bezier2;
import com.jayzhao.customactionbar.Widget.Bezier3;
import com.jayzhao.customactionbar.Widget.MyDialogFragment;

/**
 * Created by Jay on 16-7-27.
 */
public class BezierActivity extends MyBaseTitleActivity {
    RadioButton mRadioButton1 = null;
    RadioButton mRadioButton2 = null;

    FrameLayout mBezier3Container = null;
    Bezier3 mBezier3 = null;
    Bezier2 mBezier2 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE.BACK_AND_MORE);
        setContentView(R.layout.activity_bezier);
        setTitle("Bezier");
        initView();
    }

    private void initView() {
        mBezier3 = (Bezier3) findViewById(R.id.bezier3);
        mBezier2 = (Bezier2) findViewById(R.id.bezier2);
        mRadioButton1 = (RadioButton) findViewById(R.id.radio_1);
        mRadioButton2 = (RadioButton) findViewById(R.id.radio_2);

        mBezier3Container = (FrameLayout) findViewById(R.id.beaier3_container);
        mRadioButton1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    mBezier3.setMode(Bezier3.MODE.MVOE_CONTROL_1);
                    mRadioButton2.setChecked(false);
                } else {
                    //do nothing
                }
            }
        });
        mRadioButton2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    mBezier3.setMode(Bezier3.MODE.MVOE_CONTROL_2);
                    mRadioButton1.setChecked(false);
                } else {
                    //do nothing
                }
            }
        });

        getRightButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyAlertDialog dialog = new MyAlertDialog(BezierActivity.this);
                final Dialog myDialog = dialog.getDialog(2, "二阶贝塞尔", "三阶贝塞尔");
                dialog.setmOnItemClickListener(new MyAlertDialog.MyOnItemClickListener() {
                    @Override
                    public void firstItemClick(View v) {
                        mBezier2.setVisibility(View.VISIBLE);
                        mBezier3Container.setVisibility(View.GONE);
                        myDialog.dismiss();
                    }

                    @Override
                    public void secondItemClick(View v) {
                        mBezier2.setVisibility(View.GONE);
                        mBezier3Container.setVisibility(View.VISIBLE);
                        myDialog.dismiss();
                    }

                    @Override
                    public void thirdItemClick(View v) {

                    }

                    @Override
                    public void fourthItemClick(View v) {

                    }
                });
                myDialog.show();
            }
        });




    }

}
