package com.jayzhao.customactionbar;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/4/18.
 */
public class MyUtils {
    private static Toast myToast;
    private static Toast myIconToast;
    private static Toast myCustomToast;

    public static void showToast(Context context, String str) {
        if(myToast == null) {
            myToast = Toast.makeText(context, str, Toast.LENGTH_LONG);
        } else {
            myToast.setText(str);
        }
        myToast.show();
    }

    public static void showIconToast(Context context, String str, int resId) {

        if(myIconToast == null) {
            myIconToast = Toast.makeText(context, str, Toast.LENGTH_SHORT);
            myIconToast.setGravity(Gravity.CENTER, 0, 0);
            LinearLayout toastLinearLayout = (LinearLayout) myIconToast.getView();
            ImageView imageView = new ImageView(context);
            imageView.setImageResource(resId);
            toastLinearLayout.addView(imageView, 0);
        } else {
            myIconToast.setText(str);
            LinearLayout toastLinearLayout = (LinearLayout) myToast.getView();
            ImageView imageView = new ImageView(context);
            imageView.setImageResource(resId);
            toastLinearLayout.removeViewAt(0);
            toastLinearLayout.addView(imageView, 0);
        }
        myIconToast.show();
    }

    public static void showCustomToast(Activity activity, String title, String text, int resId) {

        LayoutInflater inflater = activity.getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast_layout,
                (ViewGroup) activity.findViewById(R.id.llToast));

        TextView titleView = (TextView) layout.findViewById(R.id.tvTitleToast);
        titleView.setText(title);
        ImageView imageView = (ImageView) layout.findViewById(R.id.tvImageToast);
        imageView.setImageResource(resId);
        TextView textView = (TextView) layout.findViewById(R.id.tvTextToast);
        textView.setText(text);

        if(myCustomToast == null) {
            myCustomToast = new Toast(activity);
            myCustomToast.setGravity(Gravity.RIGHT | Gravity.TOP, 300, 300);
            myCustomToast.setDuration(Toast.LENGTH_LONG);
            myCustomToast.setView(layout);
        } else {
            myCustomToast.setView(layout);
        }
        myCustomToast.show();
    }



}
