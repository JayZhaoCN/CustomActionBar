package com.jayzhao.customactionbar;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jayzhao.customactionbar.Widget.MyDialogFragment;


public class MainActivity extends MyBaseTitleActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private TextView mRecyclerViewText;
    private TextView mTableText;
    private TextView mWebText;
    private TextView mChangeAnimation;
    private TextView mPromptDialogText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);

        setStyle(STYLE.BACK_AND_MORE);

        mRecyclerViewText = (TextView) findViewById(R.id.recyclerViewText);
        mRecyclerViewText.setOnClickListener(this);

        mTableText = (TextView) findViewById(R.id.tableLayoutText);
        mTableText.setOnClickListener(this);

        mWebText = (TextView) findViewById(R.id.webText);
        mWebText.setOnClickListener(this);

        mChangeAnimation = (TextView) findViewById(R.id.changeAnimation);
        mChangeAnimation.setOnClickListener(this);

        mPromptDialogText = (TextView) findViewById(R.id.promptDiglog);
        mPromptDialogText.setOnClickListener(this);


        getRightButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final MyAlertDialog dialog = new MyAlertDialog(MainActivity.this);
                final Dialog myDialog = dialog.getDialog(3, "Toast", "Custom Toast", "Custom Toast 2");
                dialog.setmOnItemClickListener(new MyAlertDialog.MyOnItemClickListener() {
                    @Override
                    public void firstItemClick(View v) {

                        MyUtils.showToast(MainActivity.this, "Custom Toast");

                        myDialog.dismiss();
                    }

                    @Override
                    public void secondItemClick(View v) {
                        MyUtils.showIconToast(MainActivity.this, "Icon Toast!", R.mipmap.ic_launcher);
                        myDialog.dismiss();
                    }

                    @Override
                    public void thirdItemClick(View v) {
                        MyUtils.showCustomToast(MainActivity.this, "Jay Zhao", "Custom Toast", R.mipmap.ic_launcher);
                        myDialog.dismiss();
                    }

                    @Override
                    public void fourthItemClick(View v) {

                    }
                });
                myDialog.show();
            }
        });

    }

    public void click(View view) {
        //Intent intent = new Intent(MainActivity.this, MyWebActivity.class);
        //intent.putExtra("URL", "https://www.baidu.com/");
        //startActivity(intent);
        startActivity(new Intent(MainActivity.this, JayActivity.class));
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.recyclerViewText:
                startActivity(new Intent(MainActivity.this, RecyclerViewActivity.class));
                break;
            case R.id.tableLayoutText:
                startActivity(new Intent(MainActivity.this, MyTableLayout.class));
                break;
            case R.id.webText:
                intent = new Intent(MainActivity.this, MyWebActivity.class);
                intent.putExtra("URL", "http://www.sina.com.cn/");
                startActivity(intent);
                break;
            case R.id.changeAnimation:
                intent = new Intent(MainActivity.this, ChangeAnimation.class);
                startActivity(intent);
                break;
            case R.id.promptDiglog:
                MyDialogFragment.showDialogFragment(MainActivity.this, MyDialogFragment.class);
                break;
        }
    }
}
