package com.example.mvdecatesting;

import java.util.ArrayList;

/**
 * TestTypeMissed.java
 *
 * This file stores the information to access
 * all the missed questions of a specific test
 * type.
 *
 * @author William Tang - MVDECA Team 18 Director of Testing
 * @since 1/17/2020
 */

public class TestTypeMissed {
    private ArrayList<QuestionMissed> questionsMissedTestType;

    public TestTypeMissed() {
        questionsMissedTestType = new ArrayList<>();
    }

    /**
     * Adds a new QuestionMissed instance
     * to the questionMissedTestType with the
     * testNum and questionNumber set.
     * @param testNum the testNum for the QuestionMissed instance
     * @param questionNumber the questionNumber for the QuestionMissed instance
     */
    public void addQuestionMissed(String testNum, int questionNumber) {
        questionsMissedTestType.add(new QuestionMissed(testNum, questionNumber));
    }

    /**
     * Gets the questionMissedTestType.
     * @return questionsMissedTestType
     */
    public ArrayList<QuestionMissed> getQuestionsMissedTestType() {
        return questionsMissedTestType;
    }
}
