package com.jayzhao.customactionbar.Widget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jayzhao.customactionbar.R;

/**
 * Created by Jay on 16-5-4.
 * 使用说明：
 * 可以更改布局，可以自定义布局中所有的View特性
 */
public class SimpleDialogFragment extends MyDialogFragment {

    @Override
    public int inflateLayout() {
        return R.layout.dialog_layout;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        TextView titleText = (TextView) view.findViewById(R.id.dialog_title);
        titleText.setText("参照MIUI的下方弹出提示框");
        return view;
    }
}
