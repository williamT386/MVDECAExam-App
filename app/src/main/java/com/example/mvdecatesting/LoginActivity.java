package com.example.mvdecatesting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * LoginActivity.java
 *
 * This is the Activity to compile, and is the first
 * Activity displayed. Asks the user for a password
 * or allows them to press a button if they forgot
 * the password.
 *
 * @author William Tang - MVDECA Team 18 Director of Testing
 * @since 12/30/2020
 */

public class LoginActivity extends AppCompatActivity {
    private static final String password = "PasswordIn";
    private static boolean allInfoStored = false;
    private TextView passwordRed;
    private EditText loginPassword;

    /**
     * Sets all the Views with their Ids
     * @param savedInstanceState Bundle for onCreate
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        passwordRed = (TextView) findViewById(R.id.textView_passwordRed);
        loginPassword = (EditText) findViewById(R.id.editTextPassword_loginPassword);

        //store all the information as the login page loads
        if(!allInfoStored) {
            FileUtilities.storeAllInfo(this);
            allInfoStored = true;
        }
    }

    /**
     * Gets the password entered by the user. If the
     * password is right, creates a new Intent to move
     * to to the MainPageActivity, and ends this
     * Activity. If there is not password entered,
     * prompts the user to enter a password. If the
     * password entered is wrong, prompts the user to
     * enter the password again.
     * @param v the Button for submitting
     */
    public void submitOnClick(View v) {
        String inputPassword = loginPassword.getText().toString();
        if(inputPassword.equals(password)) {
            startActivity(IntentUtilities.moveActivity(this,
                    MainPageActivity.class));
            finish();
        }
        else if(inputPassword.length() == 0)
            passwordRed.setText(R.string.enter_the_password);
        else
            passwordRed.setText(R.string.try_again);
    }

    /**
     * Creates a new Intent to move to the ForgotPasswordActivity.
     * @param v the Button for forgot password
     */
    public void forgotPasswordOnClick(View v) {
        startActivity(IntentUtilities.moveActivity(this,
                ForgotPasswordActivity.class));
    }

}