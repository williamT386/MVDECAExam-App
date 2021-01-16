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

public class NewQuestionsActivity extends AppCompatActivity {
    private String mode;
    private RadioGroup radioGroup;
    private RadioButton[] radioButtonAnswers;
    private ImageView questionImageView;
    private String[] qAndA;
    private String answer, testNum;
    private static int indexAllTests, questionNumber;

    /**
     * Initializes the Views and then tells FileUtilities
     * to open the file to read, and then reads the information
     * from the files opened by FileUtilities and saves it.
     * @param savedInstanceState Bundle for onCreate
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_questions);

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
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup_answerChoices);

        radioButtonAnswers = new RadioButton[4];
        int[] allIds = new int[]{R.id.radioButton_answerChoiceA,
                R.id.radioButton_answerChoiceB, R.id.radioButton_answerChoiceC,
                R.id.radioButton_answerChoiceD};
        for(int i = 0; i < allIds.length; i++)
            radioButtonAnswers[i] = (RadioButton) findViewById(allIds[i]);

        questionImageView = (ImageView) findViewById(R.id.imageView_questionImage);
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
            status = "correct";
        }
        else {
            toastText = "Try again.";
            status = "incorrect";
        }

        Toast.makeText(getApplicationContext(), toastText,
                Toast.LENGTH_SHORT).show();

        //if it is wrong, it is wrong forever
        if(!FileUtilities.getStatus(indexAllTests, testNum, questionNumber).
                equals("incorrect"))
            FileUtilities.setStatus(indexAllTests, testNum, questionNumber,
                    status);
    }

    /**
     * Sets the testNum in this class to any random index
     * of the testNum in FileUtilities. Randomize the
     * questionNumber between 1 and 100.
     */
    private void setRandomTestNumAndQuestionNumber() {
        String originalTestNum = testNum;
        int originalQuestionNumber = questionNumber;

        while(true) {
            int testNumIndex = (int)(Math.random()*FileUtilities.
                    getTestNum(indexAllTests).size());
            testNum = FileUtilities.getTestNum(indexAllTests).
                    get(testNumIndex);
            questionNumber = (int)(Math.random()*99+1);

            //if this is the "Try New Questions" mode and the
            // status is null, then this is a good question
            if(mode.equals("Try New Questions") &&
                    FileUtilities.getStatus(indexAllTests, testNum,
                            questionNumber) == null)
                break;
            /*
            * if this is the "Review Missed Questions" mode and
            * the status is non-null, the status is "incorrect",
            * and either the testNum or the questionNumber is not
            * the original, then this is a good question */
            else if(mode.equals("Review Missed Questions") &&
                    FileUtilities.getStatus(indexAllTests, testNum, questionNumber) != null &&
                    FileUtilities.getStatus(indexAllTests, testNum, questionNumber).equals("incorrect") &&
                    (!originalTestNum.equals(testNum) || originalQuestionNumber != questionNumber))
                break;
            //this is an error
            else if(!mode.equals("Try New Questions") && !mode.equals("Review Missed Questions")) {
                Log.e("mode is invalid", "" + mode);
                System.exit(1);
            }
        }

        //check if there is an image for this question, and set the image if there is
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