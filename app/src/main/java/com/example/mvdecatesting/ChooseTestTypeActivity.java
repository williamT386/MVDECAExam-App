package com.example.mvdecatesting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * ChooseTestTypeActivity.java
 *
 * The user chooses between the different test types, and that
 * choice is passed on.
 *
 * @author William Tang - MVDECA Team 18 Director of Testing
 * @since 1/15/2020
 */

public class ChooseTestTypeActivity extends AppCompatActivity {

    /**
     * Sets the mode to the test type coming in
     * from the Intent that created this instance.
     * Sets the text for the ListView and what
     * to do when the list view is clicked.
     * @param savedInstanceState Bundle for onCreate
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_test_type);

        Intent caller = getIntent();
        //retract the mode from the Extra using the key and set it to mode
        String mode = caller.getStringExtra("Choose Test Type Mode");

        //sets the text for the ListView options
        ListView listViewTestType = findViewById(R.id.listView_testType);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.arrayTestTypes,
                android.R.layout.simple_list_item_1);
        listViewTestType.setAdapter(adapter);

        Context context = this.getApplicationContext();

        listViewTestType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view,
                                    int position, long id) {
                //checks if this is the "Review Missed Questions" mode
                // and if there are no missed questions
                if(mode.equals("Review Missed Questions") &&
                        !FileUtilities.getHasWrongQuestions(position)) {
                    Toast.makeText(context,
                            "There are no missed questions for this type of test.",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent changeActivity = IntentUtilities.moveActivity(context,
                            DoQuestionsActivity.class);
                    //adds the mode and the position as extras with keys to the Intent instance
                    changeActivity.putExtra("Choose Test Type Mode", mode);
                    changeActivity.putExtra("testType", position);
                    startActivity(changeActivity);
                    finish();
                }
            }
        });
    }

    /**
     * Creates a new Intent to move to the LoginActivity.
     * @param v the Button for going back
     */
    public void backOnClick(View v) {
        startActivity(IntentUtilities.moveActivity(this,
                MainPageActivity.class));
        finish();
    }
}