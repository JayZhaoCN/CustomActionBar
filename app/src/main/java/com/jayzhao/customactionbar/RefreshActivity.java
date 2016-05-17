package com.jayzhao.customactionbar;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

/**
 * Created by hm on 16-5-17.
 */
public class RefreshActivity extends MyBaseTitleActivity {

    private PullToRefreshListView mRefreshListView = null;
    private ListView mListView = null;
    private String[] mDatas = {
            "China",
            "America",
            "Canada",
            "Japan",
            "Korea"
    };
    private MyAdapter mAdapter = null;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 1) {
                mRefreshListView.onRefreshComplete();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.refresh_layout);
        mRefreshListView = (PullToRefreshListView) findViewById(R.id.refresh_holder);
        mListView = mRefreshListView.getRefreshableView();
        mAdapter = new MyAdapter();
        mListView.setAdapter(mAdapter);
        mRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                /**
                 * 两种方法都可以，不过第二种感觉有点蠢
                 */
                //mHandler.sendEmptyMessageDelayed(1, 1500);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1500);
                            mHandler.sendEmptyMessage(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
        mRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        mRefreshListView.getLoadingLayoutProxy(true, true).setPullLabel("下拉刷新");
        mRefreshListView.getLoadingLayoutProxy(true, true).setRefreshingLabel("正在刷新");
        mRefreshListView.getLoadingLayoutProxy(true, true).setReleaseLabel("松开刷新");


    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mDatas.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView tv;
            if(convertView == null) {
                convertView = getLayoutInflater().inflate(android.R.layout.simple_list_item_1, null);
                tv = (TextView) convertView.findViewById(android.R.id.text1);
                convertView.setTag(tv);
            } else {
                tv = (TextView) convertView.getTag();
            }
            tv.setText(mDatas[position]);
            return convertView;
        }
    }
}
