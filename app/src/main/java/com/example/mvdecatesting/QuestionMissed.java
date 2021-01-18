package com.example.mvdecatesting;

/**
 * QuestionMissed.java
 *
 * This file stores the information to access
 * a missed question.
 *
 * @author William Tang - MVDECA Team 18 Director of Testing
 * @since 1/17/2020
 */

public class QuestionMissed {
    private String testNum;
    private int questionNumber;

    public QuestionMissed(String testNumIn, int questionNumberIn) {
        testNum = testNumIn;
        questionNumber = questionNumberIn;
    }

    /**
     * Returns testNum.
     * @return testNum
     */
    public String getTestNum() {
        return testNum;
    }

    /**
     * Returns questionNumber.
     * @return questionNumber
     */
    public int getQuestionNumber() {
        return questionNumber;
    }
}
