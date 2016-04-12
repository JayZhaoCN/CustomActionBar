package com.jayzhao.customactionbar;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hm on 16-4-11.
 */
public class RecyclerViewActivity extends MyBaseTitleActivity implements View.OnClickListener {

    private static final String TAG = "RecyclerViewActivity";
    private RecyclerView mRecyclerView;
    List<String> mDatas;
    MyRecyclerViewAdapter mAdapter;

    private LinearLayoutManager mLinearLayoutManager;
    private GridLayoutManager mGridLayoutManager;

    private boolean isVertical = true;
    private boolean isGrid = true;

    public static final int LINEAR_VERTICAL = 0;
    public static final int LINEAR_HORIZONTAL = 1;
    public static final int GRID_VERTICAL = 2;
    public static final int GRID_HORIZONTAL = 3;

    private int mStyle;

    private TextView mRightButton;
    private Dialog mDialog;

    private Context mContext;

    private RecyclerView.ItemDecoration mDecoration;
    TextView addText;
    TextView removeText;

    StaggeredGridLayoutManager mStaggeredGridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setStyle(STYLE.BACK_AND_MORE);

        this.setContentView(R.layout.recycler_layout);

        initViews();
        initDatas();
    }

    private void initDatas() {
        mDatas = new ArrayList<String>();
        for(int i='A'; i<='z'; i++) {
            mDatas.add("" + (char)i);
        }
    }

    private void initViews() {
        mContext = this;
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mStyle = GRID_HORIZONTAL;

        addText = (TextView) findViewById(R.id.add);
        addText.setOnClickListener(this);

        removeText = (TextView) findViewById(R.id.remove);
        removeText.setOnClickListener(this);

        initRecyclerView();
    }

    private void initRecyclerView() {
        if(mDecoration != null) {
            mRecyclerView.removeItemDecoration(mDecoration);
        }
        switch (mStyle) {
            case LINEAR_VERTICAL:
                mLinearLayoutManager = new LinearLayoutManager(this);
                mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                mRecyclerView.setLayoutManager(mLinearLayoutManager);
                mRecyclerView.setAdapter(mAdapter = new MyRecyclerViewAdapter());
                mDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST);
                mRecyclerView.addItemDecoration(mDecoration);
                break;
            case LINEAR_HORIZONTAL:
                mLinearLayoutManager = new LinearLayoutManager(this);
                mLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                mRecyclerView.setLayoutManager(mLinearLayoutManager);
                mRecyclerView.setAdapter(mAdapter = new MyRecyclerViewAdapter());
                mDecoration = new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL_LIST);
                mRecyclerView.addItemDecoration(mDecoration);
                break;
            case GRID_VERTICAL:
                mGridLayoutManager = new GridLayoutManager(this, 4, LinearLayoutManager.VERTICAL, false);
                mRecyclerView.setLayoutManager(mGridLayoutManager);
                mRecyclerView.setAdapter(mAdapter = new MyRecyclerViewAdapter());
                mDecoration = new DividerGridItemDecoration(this, mStyle);
                mRecyclerView.addItemDecoration(mDecoration);
                break;
            case GRID_HORIZONTAL:
                mGridLayoutManager = new GridLayoutManager(this, 4);
                mGridLayoutManager = new GridLayoutManager(this, 4, LinearLayoutManager.HORIZONTAL, false);
                mRecyclerView.setLayoutManager(mGridLayoutManager);
                mRecyclerView.setAdapter(mAdapter = new MyRecyclerViewAdapter());
                mDecoration = new DividerGridItemDecoration(this, mStyle);
                mRecyclerView.addItemDecoration(mDecoration);
                break;
        }

        mAdapter.setOnItemClickListener(new MyOnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(mContext, mDatas.get(position) + " click!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(mContext, mDatas.get(position) + " Long click!", Toast.LENGTH_SHORT).show();

            }
        });

        mRightButton = getRightButton();

        mDialog = new Dialog(mContext, R.style.dialog2);
        Window window = mDialog.getWindow();
        //window.setWindowAnimations(R.anim.dialog_anim);
        WindowManager.LayoutParams lp = window.getAttributes();
        window.setGravity(Gravity.RIGHT | Gravity.TOP);

        lp.y = this.getResources().getDimensionPixelSize(R.dimen.title_height);
        lp.x = 0;

        window.setAttributes(lp);

        mDialog.setContentView(R.layout.dialog_item);

        mDialog.findViewById(R.id.num1).setOnClickListener(this);
        mDialog.findViewById(R.id.num2).setOnClickListener(this);
        mDialog.findViewById(R.id.num3).setOnClickListener(this);
        mDialog.findViewById(R.id.num4).setOnClickListener(this);

        mRightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.num1:
                mStyle = LINEAR_VERTICAL;
                break;
            case R.id.num2:
                mStyle = LINEAR_HORIZONTAL;
                break;
            case R.id.num3:
                mStyle = GRID_VERTICAL;
                break;
            case R.id.num4:
                mStyle = GRID_HORIZONTAL;
                break;
            case R.id.add:
                mAdapter.addData(1);
                break;
            case R.id.remove:
                mAdapter.removeData(1);
                break;
        }
        mDialog.cancel();
        initRecyclerView();
    }

    class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {

        private MyOnItemClickListener mOnClickListener;

        public void addData(int position) {
            mDatas.add(position, "Jay");
            notifyItemInserted(position);
        }

        public void removeData(int position) {
            mDatas.remove(position);
            notifyItemRemoved(position);
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder viewHolder;

            switch (mStyle) {
                case LINEAR_VERTICAL:
                    viewHolder =
                            new MyViewHolder(LayoutInflater.from(RecyclerViewActivity.this)
                                    .inflate(R.layout.recycler_item_linear_vertical, parent, false));
                    return viewHolder;
                case LINEAR_HORIZONTAL:
                    viewHolder =
                            new MyViewHolder(LayoutInflater.from(RecyclerViewActivity.this)
                                    .inflate(R.layout.recycler_item_linear_horizontal, parent, false));
                    return viewHolder;
                case GRID_VERTICAL:
                    viewHolder =
                            new MyViewHolder(LayoutInflater.from(RecyclerViewActivity.this)
                                    .inflate(R.layout.recycler_item_grid_vertical, parent, false));
                    return viewHolder;
                case GRID_HORIZONTAL:
                    viewHolder =
                            new MyViewHolder(LayoutInflater.from(RecyclerViewActivity.this)
                                    .inflate(R.layout.recycler_item_grid_horizontal, parent, false));
                    return viewHolder;
                default:
                    return null;
            }
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            holder.tv.setText(mDatas.get(position));

            //Log.e(TAG, "onBindViewHolder");

            holder.tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnClickListener != null) {
                        mOnClickListener.onItemClick(holder.tv, holder.getLayoutPosition());
                    }
                }
            });

            holder.tv.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnClickListener.onItemLongClick(holder.tv, holder.getLayoutPosition());
                    //这里返回true表示不再处理onClick事件，否则，长按事件响应后，还会响应一次点击（onClick）事件
                    return true;
                }
            });
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }

        public void setOnItemClickListener(MyOnItemClickListener onItemClickListener) {
            this.mOnClickListener = onItemClickListener;
        }

        public class MyViewHolder extends RecyclerView.ViewHolder{
            private TextView tv;

            public MyViewHolder(View itemView) {
                super(itemView);
                tv = (TextView) itemView.findViewById(R.id.id_num);



            }
        }
    }
}
