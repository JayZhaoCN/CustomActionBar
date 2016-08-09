package com.jayzhao.customactionbar.another_world;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jayzhao.customactionbar.MyUtils;
import com.jayzhao.customactionbar.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Jay on 16-8-9.
 */

/**
 * 在这里写好泛型，让方法的返回值不需要进行转换
 *                                       *
 *                                           *
 *                                               *
 *                                                   *
 *                                                       *
 */
public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder> {
    private Context mContext = null;
    private LayoutInflater mLayoutInflater = null;
    private List<String> mDatas = null;
    private static final String TAG = "MyRecyclerAdapter";

    public MyRecyclerAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mDatas = new ArrayList<String>();
        mDatas.add("幽鬼");
        mDatas.add("宙斯");
        mDatas.add("痛苦女王");
        mDatas.add("影魔");
        mDatas.add("黑暗游侠");
        mDatas.add("剑圣");
        mDatas.add("龙骑士");
        mDatas.add("屠夫");
        mDatas.add("编织者");
        mDatas.add("祈求者");
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.layout_recycler_child, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.mChildText.setText(mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.child_text)
        TextView mChildText = null;
        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.child_text)
        public void onClick() {
            Log.i(TAG, "onClick" + mChildText.getText());
            MyUtils.showToast(mContext, mChildText.getText() + "");
        }
    }
}
