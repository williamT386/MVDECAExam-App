package com.example.mvdecatesting;

import com.example.mvdecatesting.QuestionMissed;

import java.util.ArrayList;

public class TestTypeMissed {
    private ArrayList<QuestionMissed> questionsMissedTestType;

    public TestTypeMissed() {
        questionsMissedTestType = new ArrayList<>();
    }

    public void addQuestionMissed(String testNum, int questionNumber) {
        questionsMissedTestType.add(new QuestionMissed(testNum, questionNumber));
    }

    public ArrayList<QuestionMissed> getQuestionsMissedTestType() {
        return questionsMissedTestType;
    }
}
