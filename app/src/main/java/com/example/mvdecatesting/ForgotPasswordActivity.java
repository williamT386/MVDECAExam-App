package com.example.mvdecatesting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

/**
 * ForgotPasswordActivity.java
 *
 * Tells the user to ask an officer for help to get the password.
 * Has a back button for the user to go back to the login Activity.
 *
 * @author William Tang - MVDECA Team 18 Director of Testing
 * @since 12/30/2020
 */

public class ForgotPasswordActivity extends AppCompatActivity {

    /**
     * Serves as on the onCreate() method.
     * @param savedInstanceState Bundle for onCreate
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
    }

    /**
     * Creates a new Intent to move to the LoginActivity.
     * @param v the Button for going back
     */
    public void backOnClick(View v) {
        startActivity(IntentUtilities.moveActivity(this,
                LoginActivity.class));
    }
}