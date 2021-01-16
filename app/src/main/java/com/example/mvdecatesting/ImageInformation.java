package com.example.mvdecatesting;

import android.util.Log;

/**
 * Question.java
 *
 * This stores all the information for a single image.
 *
 * @author William Tang - MVDECA Team 18 Director of Testing
 * @since 1/15/2020
 */

public class ImageInformation {
    public String[] allInfoImage;
    private final String[] allChoices;
    private int initializeIndex;

    public ImageInformation() {
        allChoices = new String[] {"testNumber", "testType",
                                    "questionNumber", "imageName"};
        allInfoImage = new String[allChoices.length];
        //used to figure out the index to set when initializing allInfoImage
        initializeIndex = 0;
    }

    /**
     * Sets the value in allInfoImage at the index
     * of initializeIndex to changeInto.
     * @param changeInto the value to change into
     */
    public void setValueImage(String changeInto) {
        allInfoImage[initializeIndex] = changeInto;
        initializeIndex++;
    }

    /**
     * Gets the value given the type expected.
     * @param typeExpected the type expected
     * @return the value that was expected
     */
    public String getValueImage(String typeExpected) {
        for(int i = 0; i < allChoices.length; i++) {
            if(allChoices[i].equals(typeExpected)) return allInfoImage[i];
        }
        Log.e("typeExpected", "typeExpected 3 was an invalid typeExpected");
        return null;
    }
}
