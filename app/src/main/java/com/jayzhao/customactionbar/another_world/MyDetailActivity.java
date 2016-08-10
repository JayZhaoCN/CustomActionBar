package com.jayzhao.customactionbar.another_world;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jayzhao.customactionbar.MyAlertDialog;
import com.jayzhao.customactionbar.MyBaseTitleActivity;
import com.jayzhao.customactionbar.R;
import com.jayzhao.customactionbar.Widget.MyDialogBuilder;

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
        setStyle(STYLE.BACK_AND_MORE, R.color.bg_color_red);
        setTitle("DOTA2");
        //千万别忘了这句话！
        ButterKnife.bind(this);
        mRecyclerView.setAdapter(new MyRecyclerAdapter(this));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        getRightButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDialogBuilder builder = new MyDialogBuilder(MyDetailActivity.this);
                Dialog dialog = builder.setDatas(2, "Linear RecyclerView", "Grid RecyclerView")
                        .setListener(new MyAlertDialog.MyOnItemClickListenerAdapter() {
                            @Override
                            public void firstItemClick(View v, Dialog dialog) {
                                mRecyclerView.setLayoutManager(new LinearLayoutManager(MyDetailActivity.this));
                                dialog.dismiss();
                            }

                            @Override
                            public void secondItemClick(View v, Dialog dialog) {
                                mRecyclerView.setLayoutManager(new GridLayoutManager(MyDetailActivity.this, 3));
                                dialog.dismiss();
                            }
                        }).getRealDialog();
                dialog.show();
            }
        });
    }
}