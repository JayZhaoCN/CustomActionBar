package com.frankzhu.recyclerviewdemo.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;

import com.frankzhu.recyclerviewdemo.R;
import com.frankzhu.recyclerviewdemo.fragment.ItemsFragment;

import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //判断savedInstanceState是否为空可以保证屏幕翻转时不做多余的操作。
        if (savedInstanceState == null) {
            /**
             *这两种写法应该是一样的
             */
            /*getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, ItemsFragment.newInstance())
                    .commit();*/
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.container, ItemsFragment.newInstance());
            ft.commit();
        }
    }
}
