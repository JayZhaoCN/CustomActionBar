package com.jayzhao.customactionbar;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
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
            myToast = Toast.makeText(context, str, Toast.LENGTH_SHORT);
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

    public static void showCustomToast(Context context, String title, String text, int resId) {

        //三种获得LayoutInflater的方法
        //LayoutInflater inflater = activity.getLayoutInflater();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //LayoutInflater inflater = LayoutInflater.from(context);


        //事实上并不需要ViewGroup,直接传null即可.
        View layout = inflater.inflate(R.layout.custom_toast_layout, null);


        //总结一下，Inflater.inflate()方法找到的布局，如果想让其Layout_width和Layout_height生效，需要在外层添加一个FrameLayout（也可以是其他类型的Layout）

        View content = layout.findViewById(R.id.llToast);
        if(content.getLayoutParams() == null) {
            Log.e("MyUtils", "NULL");
        } else {
            Log.e("MyUtils", content.getLayoutParams().width + "");
        }


        //ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(500, 300);
        //layout.setLayoutParams(lp);
        //Log.e("height: ", layout.getLayoutParams().height + "");
        //Log.e("width: ", layout.getLayoutParams().width + "");

        /*
        真是坑爹，找到的竟然是null
        if(activity.findViewById(R.id.llToast) == null) {
            Log.e("MyUtils", "NULL");
        }
        View layout = inflater.inflate(R.layout.custom_toast_layout, (ViewGroup) activity.findViewById(R.id.llToast), true);
        */

        TextView titleView = (TextView) layout.findViewById(R.id.tvTitleToast);
        titleView.setText(title);
        ImageView imageView = (ImageView) layout.findViewById(R.id.tvImageToast);
        imageView.setImageResource(resId);
        TextView textView = (TextView) layout.findViewById(R.id.tvTextToast);
        textView.setText(text);

        if(myCustomToast == null) {
            myCustomToast = new Toast(context);
            myCustomToast.setGravity(Gravity.RIGHT | Gravity.TOP, 300, 300);
            myCustomToast.setDuration(Toast.LENGTH_SHORT);
            myCustomToast.setView(layout);
        } else {
            myCustomToast.setView(layout);
        }
        myCustomToast.show();
    }



}
