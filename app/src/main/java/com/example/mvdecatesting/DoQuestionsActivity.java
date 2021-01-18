package com.example.mvdecatesting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * NewQuestionsActivity.java
 *
 * In this activity, the user practices new questions. They choose
 * their answer choice using one of the RadioButtons and then click
 * Next, or they can press the Back button to return to the main Activity.
 *
 * @author William Tang - MVDECA Team 18 Director of Testing
 * @since 12/30/2020
 */

public class DoQuestionsActivity extends AppCompatActivity {
    private String mode;
    private RadioGroup radioGroup;
    private RadioButton[] radioButtonAnswers;
    private ImageView questionImageView;
    private String[] qAndA;
    private String answer;
    private static String testNum;
    private static int indexAllTests, questionNumber;
    private boolean hasStatusChanges = false;

    /**
     * Initializes the Views and then tells FileUtilities
     * to open the file to read, and then reads the information
     * from the files opened by FileUtilities and saves it.
     * @param savedInstanceState Bundle for onCreate
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_questions);

        Intent caller = getIntent();
        //retract the mode from the Extra using the key and set it to mode
        mode = caller.getStringExtra("Choose Test Type Mode");

        //retract the testType from the Extra using the key and set
        // it to indexAllTests
        indexAllTests = caller.getIntExtra("testType", 0);
        setTestTypeText();
        initializeById();

        setRandomTestNumAndQuestionNumber();
        getData();

        //TODO - remove this after the problem with doing duplicate questions for review is fixed
        if("Review Missed Questions".equals(mode)) {
            Toast.makeText(getApplicationContext(),
                    "*NOTE* Will not give you the same question " +
                            "as the previous one, but may give duplicates " +
                            "beyond that.", Toast.LENGTH_LONG).show();
        }


    }

    /**
     * If there are any status changes,
     * call FileUtilities.copyStatus()
     * and then set the hasStatusChanges
     * to false.
     */
    @Override
    protected void onPause() {
        super.onPause();
        if(hasStatusChanges) {
            //TODO - call FileUtilities.copyStatus for next version
//            FileUtilities.copyStatus(this, getApplicationContext(), indexAllTests);
            hasStatusChanges = false;
        }
    }

    /**
     * Sets the text of textView_testTypeText to show the type of test clicked.
     */
    private void setTestTypeText() {
        String[] choices = {"Business Administration Core Exam",
                            "Business Management and Administration Cluster Exam",
                            "Entrepreneurship Cluster Exam",
                            "Finance Cluster Exam",
                            "Hospitality and Tourism Cluster Exam",
                            "Marketing Cluster Exam",
                            "Personal Financial Literacy Exam"};
        ((TextView) findViewById(R.id.textView_testTypeText)).
                setText(choices[indexAllTests]);
    }

    /**
     * Initializes the Views by their Ids.
     */
    private void initializeById() {
        radioGroup = findViewById(R.id.radioGroup_answerChoices);

        radioButtonAnswers = new RadioButton[4];
        int[] allIds = new int[]{R.id.radioButton_answerChoiceA,
                R.id.radioButton_answerChoiceB, R.id.radioButton_answerChoiceC,
                R.id.radioButton_answerChoiceD};
        for(int i = 0; i < allIds.length; i++)
            radioButtonAnswers[i] = findViewById(allIds[i]);

        questionImageView = findViewById(R.id.imageView_questionImage);
    }

    /**
     * Gets the information from FileUtilities and sets it to qAndA.
     * Afterwards, add that information as the text of the
     * textView_questionText and radioButtonAnswers.
     */
    private void getData() {
        qAndA = FileUtilities.getAllInfoFromQuestion(indexAllTests, testNum,
                questionNumber);

        //set the text for the textView_questionText to the question,
        // which is at index 2 within qAndA
        ((TextView) findViewById(R.id.textView_questionText)).setText(qAndA[2]);

        //sets the text for each Radio Button in radioButtonAnswers to
        // the answer choices, starting with index 3 and ending at index 6
        for(int i = 0; i < radioButtonAnswers.length; i++) {
            radioButtonAnswers[i].setText(qAndA[i+3]);
        }

        /* sets the answer to the correct answer, which is at index 7.
         * At index 7, only has a String showing the letter of the
         * correct answer, but need the actual String, so convert
         * and set answer to the actual String for the right answer.
        */
        //check last char to determine what is the correct answer
        char lastChar = qAndA[7].charAt(qAndA[7].length() - 1);
        //by subtracting 62, converts the lastChar into the correct index
        // within qAndA, which is saved into answer
        answer = qAndA[lastChar - 62];
    }

    /**
     * Creates a new Intent to move to the MainPageActivity.
     * @param v the Button for going back
     */
    public void backOnClick(View v) {
        startActivity(IntentUtilities.moveActivity(this,
                MainPageActivity.class));
        finish();
    }

    /**
     * Determines which RadioButton was clicked. If the text
     * for that question matches the text for the correct answer,
     * then advance to the next question. Otherwise, this method
     * creates a Toast to tell the user to try again.
     * @param v the Button for next
     */
    public void nextOnClick(View v) {
        //add what happens if the next button is clicked
        int radioButtonID = radioGroup.getCheckedRadioButtonId();
        View radioButton = radioGroup.findViewById(radioButtonID);
        int indexOfClick = radioGroup.indexOfChild(radioButton);

        //if the indexOfClick is -1, then the user did not click anything
        if(indexOfClick == -1) {
            Toast.makeText(getApplicationContext(),
                    "Please pick an answer.", Toast.LENGTH_SHORT).show();
            return;
        }

        //add 3 because 0th index for click should be choice A, which
        // is at index 3 in qAndA, choice B is index 4 in qAndA, etc.
        String message = qAndA[indexOfClick+3];

        //check if the answer choice is correct
        if(answer.equals(message)) {
            choseAnswer(true);

            //clear the selection in radioGroup
            radioGroup.clearCheck();

            setRandomTestNumAndQuestionNumber();

            getData();
        }
        //this means answer choice was incorrect
        else {
            choseAnswer(false);
        }
    }

    /**
     * If the user chose an answer and then clicked Next,
     * then creates a Toast and sets the status depending
     * on whether the user got the question correct or
     * incorrect.
     * @param isCorrect whether or not the user got the question correct
     */
    private void choseAnswer(boolean isCorrect) {
        String toastText, status;
        if(isCorrect) {
            toastText = "Correct. Good job!";
            status = "Correct";
        }
        else {
            toastText = "Try again.";
            status = "Incorrect";
        }

        Toast.makeText(getApplicationContext(), toastText,
                Toast.LENGTH_SHORT).show();

        //either the value stored is "No Status" currently and the status
        // is "Incorrect", or the value stored is already "Incorrect"
        if(("No Status".equals(FileUtilities.getStatus(indexAllTests, testNum, questionNumber)) &&
                "Incorrect".equals(status))) {
            FileUtilities.setStatus(indexAllTests, testNum, questionNumber, "Incorrect");

            //this test type has questions that are wrong
            FileUtilities.setHasWrongQuestionsTrue(indexAllTests);
        }
        else if("Correct".equals(FileUtilities.getStatus(indexAllTests, testNum, questionNumber)))
            FileUtilities.setStatus(indexAllTests, testNum, questionNumber, "Correct");
        hasStatusChanges = true;
    }

    /**
     * If the mode is "Try New Questions", choose random
     * testNum and random questionNumber until the status
     * for that pair of testNum and questionNumber is
     * "No Status". Afterwards, set the possible image.
     * If the mode is "Review Missed Questions", choose
     * random testNum and random questionNumber until
     * that testNum and questionNumber pair was not the
     * equal to the previous pair.
     */
    private void setRandomTestNumAndQuestionNumber() {
        //TODO - implement writing to file so that the user knows which "review" questions were finished
        String originalTestNum = testNum;
        int originalQuestionNumber = questionNumber;

        //if this is the "Try New Questions", search for new
        // questions until one is found.
        //TODO - take into consideration if the client does all the questions
        if ("Try New Questions".equals(mode)) {
            //keep searching until find question that was never completed before
            do {
                int testNumIndex = (int) (Math.random() * FileUtilities.
                        getTestNum(indexAllTests).size());
                testNum = FileUtilities.getTestNum(indexAllTests).
                        get(testNumIndex);
                questionNumber = (int) (Math.random() * 99 + 1);

                //if the status is "No Status", then this is a good question
            } while (!"No Status".equals(FileUtilities.getStatus(indexAllTests,
                    testNum, questionNumber)));

            setPossibleImage();
        }
        //if this is the "Review Missed Questions" mode
        else if("Review Missed Questions".equals(mode)) {
            TestTypeMissed testTypeMissed = FileUtilities.getTestTypeMissed(indexAllTests);
            ArrayList<QuestionMissed> questionMissed = testTypeMissed.getQuestionsMissedTestType();

            /*if the user already this question, then there are no more questions to try
            if(tried) {
                Toast.makeText(getApplicationContext(),
                        "No more questions missed for this type of test. " +
                                "Try this question again for more practice or " +
                                "go back to the main page.", Toast.LENGTH_SHORT).show();
            } **/

            //keep picking questions until the previous question is not picked
            Log.i("questionMissed.size()", "" + questionMissed.size());
            do {
                int index = (int) (Math.random() * questionMissed.size());
                testNum = questionMissed.get(index).getTestNum();
                questionNumber = questionMissed.get(index).getQuestionNumber();

            } while(questionMissed.size() != 1 && testNum.equals(originalTestNum) &&
                    originalQuestionNumber == questionNumber);
            setPossibleImage();
        }
        //this is an error
        else {
            Log.e("mode is invalid", "" + mode);
            System.exit(1);
        }

    }

    /**
     * Checks if there are any images for this question, and if
     * there are, sets the questionImageView to display that
     * image.
     */
    private void setPossibleImage() {
        //get all the image information
        ArrayList<ImageInformation> allImageInfo = FileUtilities.getAllImageInformation();
        for(int i = 0; i < allImageInfo.size(); i++) {
            ImageInformation temp = allImageInfo.get(i);

            //gets the questionNumber as a String and converts it into an int
            int questionNumberImage = Integer.parseInt(temp.getValueImage(
                    "questionNumber").substring(9));
            //check if the testNumber and the questionNumber are the same as the
            // ones for ImageInformation
            if(testNum.equals(temp.getValueImage("testNumber")) &&
                    questionNumber == questionNumberImage) {
                //sets the questionImageView to the expected image
                String imageName = temp.getValueImage("imageName");
                int resourceID = getResources().getIdentifier(imageName ,
                        "drawable", getPackageName());
                Drawable drawable = getResources().getDrawable(resourceID);
                questionImageView.setImageDrawable(drawable);
            }
        }
    }

}