package com.jayzhao.customactionbar.another_world;

import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.jayzhao.customactionbar.MyBaseTitleActivity;
import com.jayzhao.customactionbar.R;
import com.jayzhao.customactionbar.another_world.Widget.MyListView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Jay on 16-8-10.
 */
public class MyListActivity extends MyBaseTitleActivity {
    @Bind(R.id.my_list)
    MyListView mMyListView = null;

    private List<ApplicationInfo> mAppList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE.SINGLE_BACK, R.color.bg_color_red);
        setContentView(R.layout.activity_mylist);
        setTitle("MyListActivity");
        ButterKnife.bind(this);

        initData();
    }

    private void initData() {
        mAppList = getPackageManager().getInstalledApplications(0);
        mMyListView.setAdapter(new MyAdapter());
    }



    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mAppList.size();
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
            ApplicationInfo appInfo = mAppList.get(position);
            ViewHolder viewHolder = null;
            if(convertView == null) {
                convertView = LayoutInflater.from(MyListActivity.this).inflate(R.layout.app_list, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.mAppImg = (ImageView) convertView.findViewById(R.id.app_icon);
                viewHolder.mAppName = (TextView) convertView.findViewById(R.id.app_name);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.mAppImg.setImageDrawable(appInfo.loadIcon(getPackageManager()));
            viewHolder.mAppName.setText(appInfo.loadLabel(getPackageManager()));
            return convertView;
        }

        class ViewHolder {
            public ImageView mAppImg = null;
            public TextView mAppName = null;
        }
    }
}
