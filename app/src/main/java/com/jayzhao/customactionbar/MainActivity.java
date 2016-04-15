package com.jayzhao.customactionbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends MyBaseTitleActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private TextView mRecyclerViewText;
    private TextView mTableText;
    private TextView mWebText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);

        setStyle(STYLE.SINGLE_BACK);

        mRecyclerViewText = (TextView) findViewById(R.id.recyclerViewText);
        mRecyclerViewText.setOnClickListener(this);

        mTableText = (TextView) findViewById(R.id.tableLayoutText);
        mTableText.setOnClickListener(this);

        mWebText = (TextView) findViewById(R.id.webText);
        mWebText.setOnClickListener(this);

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
            case R.id.tableLayoutText:
                startActivity(new Intent(MainActivity.this, MyTableLayout.class));
                break;
            case R.id.webText:
                Intent intent = new Intent(MainActivity.this, MyWebActivity.class);
                intent.putExtra("URL", "http://www.sina.com.cn/");
                startActivity(intent);
                break;
        }
    }
}
