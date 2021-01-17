package com.example.mvdecatesting;

import android.util.Log;

public class QuestionMissed {
    private String testNum;
    private int questionNumber;
    private static final String[] allChoices = new String[] {
            "testNum", "questionNumber"};

    public QuestionMissed(String testNumIn, int questionNumberIn) {
        testNum = testNumIn;
        questionNumber = questionNumberIn;
    }

    public String getTestNum() {
        return testNum;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }
}
