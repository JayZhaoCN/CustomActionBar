package com.jayzhao.customactionbar.Widget;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.jayzhao.customactionbar.MyUtils;
import com.jayzhao.customactionbar.R;

/**
 * Created by hm on 16-4-19.
 */
public class MyDialogFragment extends DialogFragment implements View.OnClickListener{

    private View mEmptyArea = null;
    private OnOpClickListener mListener = null;
    private TextView mLeftButton = null;
    private TextView mRightButton = null;

    public interface OnOpClickListener {
        public void onEmptyAreaClick(DialogFragment dialog);
        public void onRightClick(DialogFragment dialog);
        public void onLeftClick(DialogFragment dialog);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(inflateLayout(), container, false);
        mEmptyArea = view.findViewById(R.id.dlg_empty_area_btn);
        mRightButton = (TextView) view.findViewById(R.id.right_text);
        mLeftButton = (TextView) view.findViewById(R.id.left_text);

        //这三个控件是有可能找不到的
        if(mEmptyArea != null)
            mEmptyArea.setOnClickListener(this);
        if(mRightButton != null)
            mRightButton.setOnClickListener(this);
        if(mLeftButton != null)
            mLeftButton.setOnClickListener(this);
        return view;
    }

    public int inflateLayout() {
        return 0;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int style = DialogFragment.STYLE_NORMAL;
        int theme = R.style.DimPanel;
        setStyle(style, theme);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dlg_empty_area_btn:
                dismiss();
                break;
            case R.id.left_text:
                mListener.onLeftClick(this);
                break;
            case R.id.right_text:
                mListener.onRightClick(this);
                break;
        }
    }

    public void setOpClickListener(OnOpClickListener l) {
        mListener = l;
    }
}
