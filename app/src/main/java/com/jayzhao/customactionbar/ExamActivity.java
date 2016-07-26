package com.jayzhao.customactionbar;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jay on 16-7-26.
 * 测试Activity
 */
public class ExamActivity extends MyBaseTitleActivity implements View.OnClickListener {
    TextView mQuestionText = null;
    TextView mTrueButton = null;
    TextView mFalseButton = null;
    TextView mNextButton = null;

    private int mQuestionIndex = 0;
    private List<Question> mQuestionList = null;
    private int mCorrectAmount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
        setStyle(STYLE.SINGLE_BACK);
        setTitle("测试");

        initViews();
        initDatas();
    }

    private void initViews() {
        mQuestionText = (TextView) findViewById(R.id.question_text);
        mTrueButton = (TextView) findViewById(R.id.true_button);
        mFalseButton = (TextView) findViewById(R.id.false_button);
        mNextButton = (TextView) findViewById(R.id.next_button);

        mTrueButton.setOnClickListener(this);
        mFalseButton.setOnClickListener(this);
        mNextButton.setOnClickListener(this);
    }

    private void initDatas() {
        mQuestionList = new ArrayList<Question>();
        mQuestionList.add(new Question("中国是世界上人口最多的国家", true));
        mQuestionList.add(new Question("中国是世界上面积最大的国家", false));
        mQuestionList.add(new Question("中国是世界上历史最悠久的国家", true));
        updateQuestionText();
    }

    public void updateQuestionText() {
        mQuestionText.setText(mQuestionList.get(mQuestionIndex).mQuestionStr);
    }

    private void checkCorrect(boolean isTrue) {
        if(mQuestionList.get(mQuestionIndex).isTrue == isTrue) {
            MyUtils.showToast(ExamActivity.this, "CORRECT");
            mQuestionList.get(mQuestionIndex).isCorrect = true;
        } else {
            MyUtils.showToast(ExamActivity.this, "FALSE");
            mQuestionList.get(mQuestionIndex).isCorrect = false;
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        int id = v.getId();
        switch(id) {
            case R.id.true_button:
                checkCorrect(true);
                break;
            case R.id.false_button:
                checkCorrect(false);
                break;
            case R.id.next_button:
                if(mQuestionIndex+1 >= mQuestionList.size()) {
                    for(int i=0; i<mQuestionList.size(); i++) {
                        if(mQuestionList.get(i).isCorrect) {
                            mCorrectAmount ++;
                        }
                    }
                    MyUtils.showToast(ExamActivity.this, "没有问题啦!" + "\n总共有" + mQuestionList.size() + "题，" + "答对了" + mCorrectAmount + "题"
                    + "\n正确率为" + (float)mCorrectAmount/mQuestionList.size());
                    mQuestionIndex = 0;
                    mCorrectAmount = 0;
                    updateQuestionText();
                    return;
                }
                mQuestionIndex ++;
                updateQuestionText();
                break;
            default:
                break;
        }
    }
}
