package com.example.mvdecatesting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * MainPageActivity.java
 *
 * This is the after the user enters the correct password. The
 * user is given 3 choices: "Try new questions", "Review missed questions",
 * and "Show missed questions in a list".
 *
 * @author William Tang - MVDECA Team 18 Director of Testing
 * @since 12/30/2020
 */

public class MainPageActivity extends AppCompatActivity {

    /**
     * Serves as on the onCreate() method.
     * @param savedInstanceState Bundle for onCreate
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
    }

    /**
     * Creates a new Intent to move to the ChooseTestTypeActivity.
     * @param v the Button for trying new questions
     */
    public void tryNewQuestionsOnClick(View v) {
        Intent intent = IntentUtilities.moveActivity(this,
                ChooseTestTypeActivity.class);
        intent.putExtra("Choose Test Type Mode",
                "Try New Questions");
        startActivity(intent);
    }

    /**
     * Creates a new Intent to move to the ChooseTestTypeActivity.
     * @param v the Button for trying new questions
     */
    public void reviewMissedQuestionsOnClick(View v) {
        Intent intent = IntentUtilities.moveActivity(this,
                ChooseTestTypeActivity.class);
        intent.putExtra("Choose Test Type Mode",
                "Review Missed Questions");
        startActivity(intent);
    }
}