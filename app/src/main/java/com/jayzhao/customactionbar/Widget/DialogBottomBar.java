package com.jayzhao.customactionbar.Widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jayzhao.customactionbar.R; 

import org.w3c.dom.Text;

/**
 * Created by hm on 16-4-19.
 */
public class DialogBottomBar extends FrameLayout {

    private TextView mLeftText = null;
    private TextView mRightText = null;

    public DialogBottomBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DialogBottomBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.promptDialog);

        String leftText = ta.getString(R.styleable.promptDialog_left_text);
        String rightText = ta.getString(R.styleable.promptDialog_right_text);
        int leftColor = ta.getColor(R.styleable.promptDialog_left_textColor, getResources().getColor(R.color.white_20_percent));
        int rightColor = ta.getColor(R.styleable.promptDialog_right_textColor, getResources().getColor(R.color.white_20_percent));

        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.dialog_bottombar, this);

        mLeftText = (TextView) findViewById(R.id.left_text);
        mRightText = (TextView) findViewById(R.id.right_text);

        mLeftText.setText(leftText);
        mRightText.setText(rightText);
        mLeftText.setTextColor(leftColor);
        mRightText.setTextColor(rightColor);

    }

    public TextView getRightButton() {
        return mRightText;
    }

    public TextView getmLeftButton() {
        return mLeftText;
    }


}
