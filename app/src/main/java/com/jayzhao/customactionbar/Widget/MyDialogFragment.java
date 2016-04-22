package com.jayzhao.customactionbar.Widget;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.jayzhao.customactionbar.R;

/**
 * Created by hm on 16-4-19.
 */
public class MyDialogFragment extends DialogFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_layout, container, false);
        //getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        view.setFitsSystemWindows(true);

        return view;
    }

    public static void showDialogFragment(Activity activity, Class <? extends DialogFragment> fragmentName) {
        FragmentTransaction ft = activity.getFragmentManager().beginTransaction();
        DialogFragment fragment = (DialogFragment) Fragment.instantiate(activity, fragmentName.getName());

        fragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.DimPanel);

        fragment.show(ft, fragmentName.getName());
    }

}
