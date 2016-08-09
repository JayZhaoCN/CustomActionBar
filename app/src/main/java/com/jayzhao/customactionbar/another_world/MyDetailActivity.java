package com.jayzhao.customactionbar.another_world;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jayzhao.customactionbar.MyBaseTitleActivity;
import com.jayzhao.customactionbar.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created Jay on 16-8-9.
 */
public class MyDetailActivity extends MyBaseTitleActivity {
    @Bind(R.id.my_recycler_view)
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_detail);
        setStyle(STYLE.SINGLE_BACK, R.color.bg_color_red);
        setTitle("DOTA2");
        //千万别忘了这句话！
        ButterKnife.bind(this);
        mRecyclerView.setAdapter(new MyRecyclerAdapter(this));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
