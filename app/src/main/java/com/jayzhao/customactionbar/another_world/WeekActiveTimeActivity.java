package com.jayzhao.customactionbar.another_world;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.jayzhao.customactionbar.MyBaseTitleActivity;
import com.jayzhao.customactionbar.R;
import com.jayzhao.customactionbar.Widget.WeekActiveCircleView;
import com.jayzhao.customactionbar.Widget.WeekActiveTimeView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Jay on 16-8-22.
 */
public class WeekActiveTimeActivity extends MyBaseTitleActivity {
    private static final String TAG = "WeekActiveTimeActivity";
    private ViewPager mViewPager = null;

    private LayoutInflater mInflater = null;

    //模拟数据
    private List<int[]> datas = null;

    private HashMap<Integer, View> mHashMap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_week_active_time);
        this.setStyle(STYLE.SINGLE_BACK, R.color.blue_dark);
        this.setTitle("周活跃时长");
        init();
    }

    private void init() {
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPager.setAdapter(new MyViewPagerAdapter());
        mInflater = LayoutInflater.from(this);
        mViewPager.setCurrentItem(MyViewPagerAdapter.MAX_NUM - 1);

        datas = new ArrayList<>();
        datas.add(new int[]{100, 120, 10, 20, 50, 50, 90});
        datas.add(new int[]{10, 20, 10, 20, 50, 50, 90});
        datas.add(new int[]{50, 100, 10, 20, 50, 50, 90});
        datas.add(new int[]{10, 100, 10, 20, 50, 50, 90});
        datas.add(new int[]{10, 120, 10, 20, 50, 50, 90});
        datas.add(new int[]{10, 110, 10, 20, 50, 50, 90});
        datas.add(new int[]{10, 10, 10, 20, 50, 50, 90});
        datas.add(new int[]{10, 60, 10, 20, 50, 50, 90});
        datas.add(new int[]{10, 10, 10, 20, 50, 50, 90});
        datas.add(new int[]{10, 20, 10, 20, 50, 50, 90});
        datas.add(new int[]{10, 50, 10, 20, 50, 50, 90});
        datas.add(new int[]{10, 110, 10, 20, 50, 50, 90});

        mHashMap = new HashMap<>();

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                View view = mHashMap.get(position);
                WeekActiveCircleView circleView = (WeekActiveCircleView) view.findViewById(R.id.circle_view);
                WeekActiveTimeView timeView = (WeekActiveTimeView) view.findViewById(R.id.active_time_view);
                timeView.isDrawText(false);
                circleView.startLoading();
                timeView.startLoading();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    class MyViewPagerAdapter extends PagerAdapter {

        //为什么这里设置为Integer.MAX_VALUE就会出现BUG?

        public static final int MAX_NUM = 9999;
        @Override
        public int getCount() {
            return MAX_NUM;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //前index周的数据
            int index = MAX_NUM - position - 1;

            View view = mInflater.inflate(R.layout.week_active_fragment, container, false);
            WeekActiveCircleView circleView = (WeekActiveCircleView) view.findViewById(R.id.circle_view);
            circleView.setCenterText(position + "");
            WeekActiveTimeView timeView = (WeekActiveTimeView) view.findViewById(R.id.active_time_view);
            timeView.setData(datas.get(index));
            container.addView(view);
            if(position == MAX_NUM - 1) {
                circleView.startLoading();
                timeView.startLoading();
            }

            mHashMap.put(position, view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
            mHashMap.remove(position);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
