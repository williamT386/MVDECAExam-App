package com.example.mvdecatesting;

import android.content.Context;
import android.content.Intent;

/**
 * IntentUtilities.java
 *
 * This is an utilities java file for creating and returning an Intent.
 *
 * @author William Tang - MVDECA Team 18 Director of Testing
 * @since 12/30/2020
 */

public class IntentUtilities {

    private IntentUtilities() { super(); }

    /**
     * Creates an Intent and calls setClass using the input context
     * and the classIn. Returns the created Intent.
     * @param context the context of the client class
     * @param classIn the class for the Intent to change activity to
     * @return the Intent to return
     */
    public static Intent moveActivity(Context context, Class<?> classIn) {
        Intent changeActivity = new Intent();
        changeActivity.setClass(context, classIn);
        return changeActivity;
    }
}
