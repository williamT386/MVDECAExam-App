package com.example.mvdecatesting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_test_type);

        Intent caller = getIntent();
        //retract the mode from the Extra using the key and set it to mode
        String mode = caller.getStringExtra("Choose Test Type Mode");

        //sets the text for the ListView options
        ListView listViewTestType = (ListView) findViewById(R.id.listView_testType);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.arrayTestTypes,
                android.R.layout.simple_list_item_1);
        listViewTestType.setAdapter(adapter);

        Context context = this.getApplicationContext();

        listViewTestType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view,
                                    int position, long id) {
                Intent changeActivity = IntentUtilities.moveActivity(context,
                        NewQuestionsActivity.class);
                //adds the mode and the position as extras with keys to the Intent instance
                changeActivity.putExtra("Choose Test Type Mode", mode);
                changeActivity.putExtra("testType", position);
                startActivity(changeActivity);
            }
        });
    }
}