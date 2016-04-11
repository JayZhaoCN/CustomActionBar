package com.jayzhao.customactionbar;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


public class MainActivity extends MyBaseTitleActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private TextView recyclerViewText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
        setStyle(STYLE.BACK_AND_MORE);
        recyclerViewText = (TextView) findViewById(R.id.recyclerViewText);
        recyclerViewText.setOnClickListener(this);
    }

    public void click(View view) {
        //Intent intent = new Intent(MainActivity.this, MyWebActivity.class);
        //intent.putExtra("URL", "https://www.baidu.com/");
        //startActivity(intent);
        startActivity(new Intent(MainActivity.this, JayActivity.class));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.recyclerViewText:

                startActivity(new Intent(MainActivity.this, RecyclerViewActivity.class));
                break;
        }
    }
}
