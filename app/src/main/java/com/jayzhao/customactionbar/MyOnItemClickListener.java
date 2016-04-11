package com.jayzhao.customactionbar;

import android.view.View;

/**
 * Created by Administrator on 2016/4/11.
 */
public interface MyOnItemClickListener {
    void onItemClick(View view, int position);

    void onItemLongClick(View view , int position);
}
