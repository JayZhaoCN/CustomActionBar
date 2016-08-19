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
        void onEmptyAreaClick(DialogFragment dialog);
        void onRightClick(DialogFragment dialog);
        void onLeftClick(DialogFragment dialog);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        //这段代码的写法的合理性有待商榷
        if(inflateLayout() != 0) {
            view = inflater.inflate(inflateLayout(), container, false);
        } else {
            try {
                throw new Exception("make sure that method inflateLayout() must be override and not return zero!");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        mEmptyArea = view.findViewById(R.id.dlg_empty_area_btn);
        mRightButton = (TextView) view.findViewById(R.id.right_text);
        mLeftButton = (TextView) view.findViewById(R.id.left_text);

        //这三个控件是有可能找不到的
        //因为可能继承的Fragment的布局文件中没有定义这几个控件
        if(mEmptyArea != null) {
            mEmptyArea.setOnClickListener(this);
            try {
                throw new Exception("请定义id为R.id.dlg_empty_area_btn的控件");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(mRightButton != null) {
            mRightButton.setOnClickListener(this);
            try {
                throw new Exception("请定义id为R.id.right_text的控件");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(mLeftButton != null) {
            mLeftButton.setOnClickListener(this);
            try {
                throw new Exception("请定义id为R.id.left_text的控件");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
                mListener.onEmptyAreaClick(this);
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
