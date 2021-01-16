package com.example.mvdecatesting;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * AllTestsOneType.java
 *
 * This file is used in FileUtilities.java and stores all the
 * information for all the tests of 1 type in a HashMap.
 *
 * @author William Tang - MVDECA Team 18 Director of Testing
 * @since 1/2/2020
 */

public class AllTestsOneType {
    private ArrayList<String> testNum;
    private HashMap<String, OneTest> hashMapOneTest;

    /**
     * Initialize testNum and hashMapOneTest.
     */
    public AllTestsOneType() {
        testNum = new ArrayList<String>();
        hashMapOneTest = new HashMap<>();
    }

    /**
     * Adds a new OneTest to hashMapOneTest given the
     * key, which is the test name.
     * @param key the key, which is the name of the test
     */
    public void addTest(String key) {
        hashMapOneTest.put(key, new OneTest());
    }

    /**
     * Gets the OneTest given the specific key,
     * which is the test name.
     * @param key the key, which is the name of the test
     * @return the OneTest with that key
     */
    public OneTest getTest(String key) {
        if(hashMapOneTest.containsKey(key)) {
            return hashMapOneTest.get(key);
        }

        //this means that the HashMap does not contain the key, so it is an error
        Log.e("error HashMap key", "" + key);
        System.exit(1);
        return null;
    }

    /**
     * Adds testNumInString to the end of testNum.
     * @param testNumInString the String to add
     */
    public void addToTestNum(String testNumInString) {
        testNum.add(testNumInString);
    }

    /**
     * Returns the testNum.
     * @return testNum
     */
    public ArrayList<String> getTestNum() {
        return testNum;
    }
}
