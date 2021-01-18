package com.example.mvdecatesting;

public class QuestionMissed {
    private String testNum;
    private int questionNumber;

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
