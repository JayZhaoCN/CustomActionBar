package com.jayzhao.customactionbar.another_world;

import android.animation.ValueAnimator;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import com.jayzhao.customactionbar.MyBaseTitleActivity;
import com.jayzhao.customactionbar.R;
import com.jayzhao.customactionbar.another_world.Widget.AnimationFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Jay on 16-8-15.
 */
public class FragmentAnimationActivity extends MyBaseTitleActivity {
    @Bind(R.id.view_pager)
    ViewPager mViewPager = null;

    MyAdapter mAdapter = null;
    List<AnimationFragment> mFragments = null;

    AnimationFragment mFragment01 = null;
    AnimationFragment mFragment02 = null;
    AnimationFragment mFragment03 = null;
    AnimationFragment mFragment04 = null;
    AnimationFragment mFragment05 = null;

    private Resources mResources = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        setStyle(STYLE.SINGLE_BACK, R.color.bg_color_red);
        setTitle("FragmentAnimation");
        ButterKnife.bind(this);
        mResources = getResources();
        mFragments = new ArrayList<AnimationFragment>();

        Bundle bundle01 = new Bundle();
        bundle01.putString("hero", "Fragment01");
        bundle01.putInt("color", mResources.getColor(R.color.bg_color_red));
        bundle01.putInt("background", mResources.getColor(R.color.bg_color_steps));
        mFragment01 = (AnimationFragment) Fragment.instantiate(FragmentAnimationActivity.this, AnimationFragment.class.getName(), bundle01);

        Bundle bundle02 = new Bundle();
        bundle02.putString("hero", "Fragment02");
        bundle02.putInt("color", mResources.getColor(R.color.blue_light));
        bundle02.putInt("background", mResources.getColor(R.color.bluegrass));
        mFragment02 = (AnimationFragment) Fragment.instantiate(FragmentAnimationActivity.this, AnimationFragment.class.getName(), bundle02);

        Bundle bundle03 = new Bundle();
        bundle03.putString("hero", "Fragment03");
        bundle03.putInt("color", mResources.getColor(R.color.colorPrimary));
        bundle03.putInt("background", mResources.getColor(R.color.change_sport_goal_confirm_solid));
        mFragment03 = (AnimationFragment) Fragment.instantiate(FragmentAnimationActivity.this, AnimationFragment.class.getName(), bundle03);

        Bundle bundle04 = new Bundle();
        bundle04.putString("hero", "Fragment04");
        bundle04.putInt("color", mResources.getColor(R.color.agree_text_color));
        bundle04.putInt("background", mResources.getColor(R.color.bg_color_red));
        mFragment04 = (AnimationFragment) Fragment.instantiate(FragmentAnimationActivity.this, AnimationFragment.class.getName(), bundle04);

        Bundle bundle05 = new Bundle();
        bundle05.putString("hero", "Fragment05");
        bundle05.putInt("color", mResources.getColor(R.color.bg_color_grey));
        bundle05.putInt("background", mResources.getColor(R.color.bg_mode_weight));
        mFragment05 = (AnimationFragment) Fragment.instantiate(FragmentAnimationActivity.this, AnimationFragment.class.getName(), bundle05);

        mFragments.add(mFragment01);
        mFragments.add(mFragment02);
        mFragments.add(mFragment03);
        mFragments.add(mFragment04);
        mFragments.add(mFragment05);

        mAdapter = new MyAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        //设置ViewPager的过渡动画
        mViewPager.setPageTransformer(true, new ZoomOutPageTransformer());
    }

    class MyAdapter extends FragmentPagerAdapter {
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }
    }

    public class ZoomOutPageTransformer implements ViewPager.PageTransformer {
        private static final String TAG = "ZoomOutPageTransformer";
        //最小缩小倍数
        private float MIN_SCALE = 0.85f;
        //最小透明度
        private float MIN_ALPHA = 0.4f;

        @Override
        public void transformPage(View page, float position) {
            Log.i(TAG, "position is: " + position);

            float scale = 1 - Math.abs(position);
            if(scale < MIN_SCALE) {
                scale = MIN_SCALE;
            }
            page.setScaleX(scale);
            page.setScaleY(scale);

            float alpha = 1 - Math.abs(position);
            if(alpha < MIN_ALPHA) {
                alpha = MIN_ALPHA;
            }
            page.setAlpha(alpha);
        }
    }

    /**
     * 这个不是我写的
     */
    public class DepthPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.75f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0);

            } else if (position <= 0) { // [-1,0]
                // Use the default slide transition when moving to the left page
                view.setAlpha(1);
                view.setTranslationX(0);
                view.setScaleX(1);
                view.setScaleY(1);

            } else if (position <= 1) { // (0,1]
                // Fade the page out.
                view.setAlpha(1 - position);

                // Counteract the default slide transition
                //抵消平移
                view.setTranslationX(pageWidth * -position);

                // Scale the page down (between MIN_SCALE and 1)
                float scaleFactor = MIN_SCALE
                        + (1 - MIN_SCALE) * (1 - Math.abs(position));
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);
            }
        }
    }

    public class CustomPageTransformer implements ViewPager.PageTransformer {
        private static final String TAG = "CustomPagerTransformer";

        @Override
        public void transformPage(final View page, float position) {
            if(position == 1 || position == -1) {
                Log.i(TAG, "DONE");
            }

            ValueAnimator animator = ValueAnimator.ofFloat(0, 1, 0);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float animatorValue = (float) animation.getAnimatedValue();
                    page.setTranslationX(animatorValue * 50);
                }
            });
            animator.start();
        }
    }

}
