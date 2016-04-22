package com.jayzhao.customactionbar;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by hm on 16-4-18.
 */
public class Fragment2 extends Fragment {

    private MaskableLayout mRoot = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment02_layout, container, false);
        mRoot = (MaskableLayout) view.findViewById(R.id.root02);
        return view;
    }

    public MaskableLayout getRoot() {
        return mRoot;
    }
}
