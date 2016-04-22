package com.jayzhao.customactionbar;

import android.os.Bundle;
import android.view.View;

import com.jayzhao.customactionbar.Widget.SubItem;

/**
 * Created by hm on 16-4-20.
 */
public class NextActivity extends MyBaseTitleActivity implements View.OnClickListener {

    private SubItem mSubItem = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStyle(STYLE.BACK_AND_MORE);
        setContentView(R.layout.next_layout);
        setTitle("Hey Young Blood!");

        mSubItem = (SubItem) findViewById(R.id.subItem);
        mSubItem.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.subItem:
                MyUtils.showToast(NextActivity.this, "you have clicked item!");
                break;
        }
    }
}
