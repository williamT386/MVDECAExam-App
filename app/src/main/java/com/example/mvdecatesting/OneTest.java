package com.example.mvdecatesting;

/**
 * OneTest.java
 *
 * This file is used in AllTestsOnetype.java and stores all the
 * information for a single test, which is all 100 questions, in
 * an array.
 *
 * @author William Tang - MVDECA Team 18 Director of Testing
 * @since 1/2/2020
 */

public class OneTest {

    private Question[] question;

    /**
     * Initialize question to have 100 Question instances,
     * since there are 100 questions per test.
     */
    public OneTest() {
        question = new Question[100];
        for(int i = 0; i < question.length; i++)
            question[i] = new Question();
    }

    /**
     * Gets the typeExpected at the questionNumber.
     * @param questionNumber the question number
     * @param typeExpected the expected type
     * @return the value that was expected
     */
    public String getValueTest(int questionNumber, String typeExpected) {
        //minus 1 because question numbers go from 1-100, but the array indices go from 0-99
        return question[questionNumber-1].getValueQuestion(typeExpected);
    }

    /**
     * Sets the typeExpected at the questionNumber into changeInto.
     * @param questionNumber the question number
     * @param typeExpected the expected type
     * @param changeInto the value to be changed into.
     */
    public void setValueTest(int questionNumber, String typeExpected, String changeInto) {
        //minus 1 because question numbers go from 1-100, but the array indices go from 0-99
        question[questionNumber-1].setValueQuestion(typeExpected, changeInto);
    }
}
