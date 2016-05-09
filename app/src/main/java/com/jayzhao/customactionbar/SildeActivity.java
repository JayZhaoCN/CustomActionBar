package com.jayzhao.customactionbar;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.jayzhao.customactionbar.MyBaseTitleActivity;
import com.jayzhao.customactionbar.R;

public class SildeActivity extends MyBaseTitleActivity {

    private ListView mListView = null;
    private String[] mList = {
            "awdawd",
            "afgaerfg",
            "afgaerfg",
            "afgaerfg",
            "afgaerfg",
            "ergaerfg",
            "arfgasdfg",
            "arfgasdfg",
            "arfgasdfg",
            "arfgasdfg",
            "arfgasdfg",
            "arfgasdfg",
            "aWEDFQWEF",
            "aWEDFQWEF",
            "aWEDFQWEF",
            "aWEDFQWEF",
            "aWEDFQWEF",
            "aWEDFQWEF",
            "aWEDFQWEF",
            "AEFawef"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.silde_layout);
        this.setStyle(STYLE.BACK_AND_MORE);
        this.setTitle("Come on!");

        mListView = (ListView) findViewById(R.id.listView);

        /**
         * 这是ArrayAdapter的一般用法，注意总结
         */
        mListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, mList));
    }



}