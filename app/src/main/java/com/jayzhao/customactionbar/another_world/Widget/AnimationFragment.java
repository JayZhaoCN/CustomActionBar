package com.jayzhao.customactionbar.another_world.Widget;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jayzhao.customactionbar.R;

/**
 * Created by Jay on 16-8-15.
 */
public class AnimationFragment extends Fragment {
    private static final String TAG = "AnimationFragment";

    private TextView mCenterText = null;
    private Bundle mBundle = null;

    @Override
    public void onAttach(Context context) {
        Log.i(TAG, "onAttach");
        super.onAttach(context);
        mBundle = getArguments();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView");
        View view = inflater.inflate(R.layout.animation_fragment, container, false);
        mCenterText = (TextView) view.findViewById(R.id.center_text);
        if(mBundle != null) {
            mCenterText.setText(mBundle.getString("hero"));
            mCenterText.setTextColor(mBundle.getInt("color"));
            mCenterText.setBackgroundColor(mBundle.getInt("background"));
        }
        return view;
    }
}
