package com.example.mvdecatesting;

import android.util.Log;

/**
 * Question.java
 *
 * This file is used in OneTest.java and stores all the
 * information for a single question
 *
 * @author William Tang - MVDECA Team 18 Director of Testing
 * @since 1/2/2020
 */

public class Question {
    private String[] allInfoQuestion;
    private final String[] allChoices;

    public Question() {
        allChoices = new String[] {"testNum", "testType", "question",
                "choiceA", "choiceB", "choiceC", "choiceD",
                "correctAnswer", "explanation", "instructionalArea",
                "status"};

        allInfoQuestion = new String[allChoices.length];
    }

    /**
     * Gets the value given the type expected.
     * @param typeExpected the type expected
     * @return the value that was expected
     */
    public String getValueQuestion(String typeExpected) {
        for(int i = 0; i < allChoices.length; i++) {
            if(allChoices[i].equals(typeExpected)) return allInfoQuestion[i];
        }

        Log.e("typeExpected",
                "typeExpected 1 was an invalid typeExpected");
        return null;
    }

    /**
     * Sets the value given the type expected.
     * @param typeExpected the type expected
     * @param changeInto the value to change into
     */
    public void setValueQuestion(String typeExpected, String changeInto) {
        for(int i = 0; i < allChoices.length; i++) {
            if(allChoices[i].equals(typeExpected)) {
                allInfoQuestion[i] = changeInto;
                return;
            }
        }

        //this means typeExpected was invalid
        Log.e("typeExpected",
                "typeExpected 2 was an invalid typeExpected");
    }
}
