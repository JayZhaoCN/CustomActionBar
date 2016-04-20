package com.jayzhao.customactionbar.Widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jayzhao.customactionbar.R;

/**
 * Created by hm on 16-4-20.
 */
public class SubItem extends RelativeLayout {

    private String mTitle = null;
    private String mSubTitle = null;

    private Drawable mIconRes = null;

    private TextView mTitleText = null;
    private TextView mSubTitleText = null;
    private ImageView mIcon = null;

    public SubItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SubItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.item_layout, this, true);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.item);

        mTitle = ta.getString(R.styleable.item_Mytitle);
        mSubTitle = ta.getString(R.styleable.item_MysubTitle);
        mIconRes = ta.getDrawable(R.styleable.item_Myicon);

        mTitleText = (TextView) this.findViewById(R.id.title);
        mSubTitleText = (TextView) this.findViewById(R.id.subTitle);
        mIcon = (ImageView) this.findViewById(R.id.icon);

        mTitleText.setText(mTitle);
        mSubTitleText.setText(mSubTitle);
        mIcon.setImageDrawable(mIconRes);

        //该方法不起作用，原因未知
        //mIcon.setBackground(mIconRes);

    }
}
