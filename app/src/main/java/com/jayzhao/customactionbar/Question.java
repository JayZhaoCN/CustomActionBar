package com.jayzhao.customactionbar;

/**
 * Created by Jay on 16-7-26.
 */
public class Question {
    public String mQuestionStr = null;
    public boolean isTrue = false;
    public boolean isCorrect = false;

    public Question(String questionStr, boolean isTrue) {
        this.mQuestionStr = questionStr;
        this.isTrue = isTrue;
    }
}
