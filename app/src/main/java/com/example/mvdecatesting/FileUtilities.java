package com.example.mvdecatesting;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import static android.content.Context.MODE_PRIVATE;

/**
 * FileUtilities.java
 *
 * This is an utilities java file for reading the file which contains
 * all the questions and answer choices.
 *
 * @author William Tang - MVDECA Team 18 Director of Testing
 * @since 1/2/2020
 */

public class FileUtilities {
    private static Scanner scannerQAndA, scannerAnswerKey;
    private static AllTestsOneType[] arrayAllTests;
    private static TestTypeMissed[] allTestTypesMissed;
    private static ArrayList<ImageInformation> imageInfoArrayList;
    private static final String[] questionsAndAnswersArray = {
            "bacquestionsandanswers.txt", "bmaquestionsandanswers.txt",
            "entrequestionsandanswers.txt", "financequestionsandanswers.txt",
            "hospitalityquestionsandanswers.txt", "marketingquestionsandanswers.txt",
            "pflquestionsandanswers.txt"};
    private static boolean[] hasWrongQuestions;

    private static final String imageList = "imagelist.txt";
    private static final int testLength = 100;

    private FileUtilities() { super(); }

    /**
     * Stores all the information from the files into the
     * proper locations within arrayAllTests. There are 7
     * different tests. The order is
     * 1) Business Administration Core Exam
     * 2) Business Management And Administration Cluster Exam
     * 3) Entrepreneurship Exam
     * 4) Finance Cluster Exam
     * 5) Hospitality And Tourism Cluster Exam,
     * 6) Marketing Cluster Exam
     * 7) Personal Financial Literacy Exam
     * Afterwards, sets all values of hasWrongQuestions to
     * false and sets the information from the image file.
     * @param activity the activity used to set the scanners
     */
    public static void storeAllInfo(Activity activity) {
        final String[] questionsAndAnswersArray = {
                "bacquestionsandanswers.txt", "bmaquestionsandanswers.txt",
                "entrequestionsandanswers.txt", "financequestionsandanswers.txt",
                "hospitalityquestionsandanswers.txt", "marketingquestionsandanswers.txt",
                "pflquestionsandanswers.txt"};
        final String[] answerExplanationsArray = {
                "bacanswerexplanations.txt", "bmaanswerexplanations.txt",
                "entreanswerexplanations.txt", "financeanswerexplanations.txt",
                "hospitalityanswerexplanations.txt", "marketinganswerexplanations.txt",
                "pflanswerexplanations.txt"};

        arrayAllTests = new AllTestsOneType[questionsAndAnswersArray.length];
        allTestTypesMissed = new TestTypeMissed[questionsAndAnswersArray.length];
        for(int testTypeIndex = 0; testTypeIndex < arrayAllTests.length; testTypeIndex++) {
            arrayAllTests[testTypeIndex] = new AllTestsOneType();
            //sets the scanners
            setScanners(activity, questionsAndAnswersArray[testTypeIndex],
                    answerExplanationsArray[testTypeIndex]);
            //this allows the program to handle scanning multiple tests in the file
            while(scannerQAndA.hasNext()) {
                storeQAndA(testTypeIndex);
                storeAnswerKey(testTypeIndex);
            }

            allTestTypesMissed[testTypeIndex] = new TestTypeMissed();
        }

        //the user has not even started, so they have no missed questions
        hasWrongQuestions = new boolean[questionsAndAnswersArray.length];
        Arrays.fill(hasWrongQuestions, false);

        setAllImageInformation(activity);
    }

    /**
     * Stores the information from the questions and answers
     * files into the proper locations within arrayAllTests.
     * Afterwards, returns the testNumberIn.
     * @param testTypeIndex the index for the type of test
     */
    private static void storeQAndA(int testTypeIndex) {
        //all questions within this test use the same testNumberIn
        String testNumberIn = scannerQAndA.nextLine();
        arrayAllTests[testTypeIndex].addTest(testNumberIn);

        //all questions within this test use the same testTypeIn
        String testTypeIn = scannerQAndA.nextLine();

        //shows a list of all the options for typeExpected in order
        String[] typeExpected = new String[] {"testNum", "testType",
                "question", "choiceA", "choiceB", "choiceC", "choiceD"};

        //start with question 1 and ends with the testLength
        for(int i = 1; i <= testLength; i++) {
            //sets the testNumber, testType, and then all the other values after that
            for(int j = 0; j < typeExpected.length; j++) {
                if(j == 0)
                    setValueFileUtilities(testTypeIndex, testNumberIn, i, typeExpected[j],
                            testNumberIn);
                else if(j == 1)
                    setValueFileUtilities(testTypeIndex, testNumberIn, i, typeExpected[j],
                            testTypeIn);
                else {
                    setValueFileUtilities(testTypeIndex, testNumberIn, i, typeExpected[j],
                            scannerQAndA.nextLine());
                }
            }
            setStatus(testTypeIndex, testNumberIn, i, scannerQAndA.nextLine());
        }

    }

    /**
     * Stores the information from the answer explanations
     * files into the proper locations within arrayAllTests.
     * @param testTypeIndex the index for the type of test
     */
    private static void storeAnswerKey(int testTypeIndex) {
        //all questions within this test use the same testNumberIn
        String testNumberIn = scannerAnswerKey.nextLine();
        //get the testType
        scannerAnswerKey.nextLine();

        //there are 4 lines per answer explanation
        String[] lines = new String[4];

        //start with question 1 and ends with question 100
        for(int questionNumber = 1; questionNumber <= testLength; questionNumber++) {
            //zeroth line is the correct answer
            lines[0] = scannerAnswerKey.nextLine();
            setValueFileUtilities(testTypeIndex, testNumberIn, questionNumber,
                    "correctAnswer", lines[0]);

            //first line is the answer explanation
            lines[1] = scannerAnswerKey.nextLine();
            //second line is the first source description
            lines[2] = scannerAnswerKey.nextLine();

            //save just the instructional area of the first source description
            setValueFileUtilities(testTypeIndex, testNumberIn, questionNumber,
                    "instructionalArea", lines[2].substring(8, 10));
            //third line is the second source description
            lines[3] = scannerAnswerKey.nextLine();
            //the answer explanation should keep the new lines between
            // each value in lines[]
            String explanationLine = lines[0] + "\n" + lines[1] +
                    "\n" + lines[2] + "\n" + lines[3];
            setValueFileUtilities(testTypeIndex, testNumberIn, questionNumber,
                    "explanation", explanationLine);
        }
    }

    /**
     * Sets the scanners to the given file names. Takes an
     * Activity to acts as the parameter of the DataInputStream.
     * @param activity the input activity
     * @param fileNameQAndA the file name for the questions and answers file
     * @param fileNameAnswerKey the file name for the correct answers file
     */
    private static void setScanners(Activity activity, String fileNameQAndA,
                                   String fileNameAnswerKey) {
        try {
            DataInputStream textFileStream1 = new DataInputStream(activity.
                    getAssets().open(fileNameQAndA));
            DataInputStream textFileStream2 = new DataInputStream(activity.
                    getAssets().open(fileNameAnswerKey));
            scannerQAndA = new Scanner(textFileStream1);
            scannerAnswerKey = new Scanner(textFileStream2);
        } catch(IOException e) {
            Log.e("input file", "INPUT file not found.");
            System.exit(1);
        }
    }

    /**
     * Sets a single value in the Question instance given
     * the information for which value to set and what to
     * set it to.
     * @param testTypeIndex the index for the type of test
     * @param key the test number
     * @param questionNumber the question number
     * @param typeExpected the expected type of the value to change
     * @param changeInto to value to change into
     */
    public static void setValueFileUtilities(int testTypeIndex,
                                             String key, int questionNumber,
                                             String typeExpected, String changeInto) {
        arrayAllTests[testTypeIndex].getTest(key).
                setValueTest(questionNumber, typeExpected, changeInto);
    }

    /**
     * Gets a single value in the Question instance given
     * the information for which value to get.
     * @param testTypeIndex the index for the type of test
     * @param key the test number
     * @param questionNumber the question number
     * @param typeExpected the expected type of the value to get
     * @return the value at the specified location
     */
    private static String getValueFileUtilities(int testTypeIndex,
                                               String key, int questionNumber,
                                               String typeExpected) {
        return arrayAllTests[testTypeIndex].getTest(key).
                getValueTest(questionNumber, typeExpected);
    }

    /**
     * Gets a String[] of all the values in the Question
     * instance given the information for which Question
     * instance to get.
     * @param testTypeIndex the index for the type of test
     * @param key the test number
     * @param questionNumber the question number
     * @return all the values from the specified Question instance
     */
    public static String[] getAllInfoFromQuestion(int testTypeIndex,
                                                  String key, int questionNumber) {
        //shows a list of all the options for typeExpected in order
        String[] typeExpectedArray = new String[] {"testNum", "testType",
                "question", "choiceA", "choiceB", "choiceC", "choiceD",
                "correctAnswer", "explanation", "instructionalArea", "status"};

        String[] allInfo = new String[typeExpectedArray.length];
        for(int i = 0; i < typeExpectedArray.length; i++)
            allInfo[i] = getValueFileUtilities(testTypeIndex, key, questionNumber,
                    typeExpectedArray[i]);

        return allInfo;
    }

    /**
     * Sets the status in the Question instance given the
     * information for which Question instance to set and
     * the value to change the status into. Also, if the
     * status is "Incorrect", create a new QuestionMissed
     * instance to add to allTestTypesMissed at index
     * testTypeIndex with the key and the questionNumber
     * in the constructor parameter.
     * @param testTypeIndex the index for the type of test
     * @param key the test number
     * @param questionNumber the question number
     * @param statusIn the value to be changed into
     */
    public static void setStatus(int testTypeIndex, String key, int questionNumber,
                                 String statusIn) {
        setValueFileUtilities(testTypeIndex, key, questionNumber, "status",
                statusIn);
        if("Incorrect".equals(statusIn))
            allTestTypesMissed[testTypeIndex].addQuestionMissed(key, questionNumber);
    }

    /**
     * Gets the status in the Question instance given the
     * information for which Question instance to get.
     * @param testTypeIndex the index for the type of test
     * @param key the test number
     * @param questionNumber the question number
     * @return the status at that specified location
     */
    public static String getStatus(int testTypeIndex, String key, int questionNumber) {
        return getValueFileUtilities(testTypeIndex, key, questionNumber, "status");
    }

    /**
     * Gets the testNum of the correct AllTestsOneType instance.
     * @param testTypeIndex the index for the type of test
     * @return the testNum within that AllTestsOneType
     */
    public static ArrayList<String> getTestNum(int testTypeIndex) {
        return arrayAllTests[testTypeIndex].getTestNum();
    }

    /**
     * Sets all the information from imageList into the appropriate
     * locations within imageInfoArrayList.
     * @param activity the input activity
     */
    private static void setAllImageInformation(Activity activity) {
        //set the scanner for reading the imageList
        Scanner scannerImageList = null;
        try {
            DataInputStream textFileStream = new DataInputStream(activity.
                    getAssets().open(imageList));
            scannerImageList = new Scanner(textFileStream);
        } catch(IOException e) {
            Log.e("input", "INPUT file \"" + imageList + "\" not found.");
            System.exit(1);
        }

        imageInfoArrayList = new ArrayList<>();
        while(scannerImageList.hasNext()) {
            //creates an instance of ImageInformation and reads and adds the next
            // 4 lines to the instance before adding the instance of the ArrayList
            ImageInformation imageInformationInstance = new ImageInformation();
            for(int i = 0; i < 4; i++) {
                imageInformationInstance.setValueImage(scannerImageList.nextLine());
            }
            imageInfoArrayList.add(imageInformationInstance);
        }
    }

    /**
     * Getter for imageInfoArrayList.
     * @return imageInfoArrayList
     */
    public static ArrayList<ImageInformation> getAllImageInformation() {
        return imageInfoArrayList;
    }

    /**
     * The value of hasWrongQuestions at index testTypeIndex
     * is changed to true.
     * @param testTypeIndex the index within hasWrongQuestion
     */
    public static void setHasWrongQuestionsTrue(int testTypeIndex) {
        hasWrongQuestions[testTypeIndex] = true;
    }

    /**
     * Returns the value of hasWrongQuestions at index
     * testTypeIndex.
     * @param testTypeIndex the index within hasWrongQuestion
     * @return hasWrongQuestions
     */
    public static boolean getHasWrongQuestions(int testTypeIndex) {
        return hasWrongQuestions[testTypeIndex];
    }

    /**
     * This method exists only for the sake of testing.
     * It resets all the status within all the
     * questions and answers files.
     * TODO - this method is unused, untested, and potentially wrong
     * @param activity the Activity in
     * @param context the Context in
     */
    public static void resetAllStatus(Activity activity, Context context) {
        for(int i = 0; i < questionsAndAnswersArray.length; i++) {
            Scanner scanner = getSingleScanner(activity, questionsAndAnswersArray[i]);
            PrintWriter printWriter = getSinglePrintWriter(context, questionsAndAnswersArray[i]);

            String previousLine = scanner.nextLine();
            while(scanner.hasNext()) {
                String lineIn = scanner.nextLine();
                if("D. ".equals(previousLine.substring(0, 3)) &&
                        ("Correct".equals(lineIn) || "Incorrect".equals(lineIn)))
                    printWriter.println("No Status");
                else {
                    printWriter.println(lineIn);
                    previousLine = lineIn;
                }
            }

            printWriter.close();
        }
    }

    /**
     * Reads in a file and saves all of its contents into an ArrayList, but
     * replace any lines with information about the status with the up to date
     * information about the status using getStatus(). Afterwards, print
     * all that information into the output file (UNSUCCESSFUL). To print into
     * the output file, cannot have the output file in assets or resources
     * directories.
     *
     * TODO - this method is not used, incomplete, and potentially wrong
     * @param activity the input activity
     * @param context the input context
     * @param testTypeIndex the index within hasWrongQuestion
     */
    public static void copyStatus(Activity activity, Context context, int testTypeIndex) {
        Scanner scanner = getSingleScanner(activity, questionsAndAnswersArray[testTypeIndex]);

        ArrayList<String> entireInFile = new ArrayList<>();
        String testNum = null;
        int questionNumber = -1;
        while(scanner.hasNext()) {
            String lineIn = scanner.nextLine();

            //check if this is the testNum
            if(lineIn.length() > 5 && "Test ".equals(lineIn.substring(0, 5)))
                testNum = lineIn;
            else if(getQuestionNumber(lineIn) != 0)
                questionNumber = getQuestionNumber(lineIn);
            //check if the previous line was answer choice D and if this line
            // is any of the possible status choices
            else if(entireInFile.size() > 1 &&
                    entireInFile.get(entireInFile.size() - 2).length() > 3 &&
                    ("No Status".equals(lineIn) || "Correct".equals(lineIn) ||
                    "InCorrect".equals(lineIn))) {

                //TODO - should set the value of the entireInFile to the new status

                if(getStatus(testTypeIndex, testNum, questionNumber).equals("Incorrect")) {
                    Log.i("info", "" + testNum + ": " + questionNumber);
                    Log.i("status change", "status is incorrect");
                }

                entireInFile.add(getStatus(testTypeIndex, testNum, questionNumber));
            }
            else {
                entireInFile.add(lineIn);
            }
        }
        scanner.close();

        PrintWriter printWriter = getSinglePrintWriter(context,
                "statusinformation.txt");
        for(String lineToPrint : entireInFile) {
            printWriter.println(lineToPrint + "  ");
            Log.i("printWriter", "printing");
        }

        printWriter.close();
    }

    /**
     * Sets a single Scanner given the activity and the
     * fileName. Returns it.
     * TODO - this method is not used
     * @param activity activity the input activity
     * @param fileName the file name
     * @return the Scanner
     */
    private static Scanner getSingleScanner(Activity activity, String fileName) {
        Scanner scanner = null;
        try {
            DataInputStream textFileStream1 = new DataInputStream(activity.
                    getAssets().open(fileName));
            scanner = new Scanner(textFileStream1);
        } catch(IOException e) {
            Log.e("input file", "INPUT file not found.");
            System.exit(1);
        }
        return scanner;
    }

    /**
     * Sets a single PrintWriter given the fileName.
     * Returns it.
     * TODO - this method is not used and potentially wrong
     * @param fileName the file name
     * @return the PrintWriter
     */
    private static PrintWriter getSinglePrintWriter(Context context, String fileName) {
        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(context.openFileOutput(fileName, MODE_PRIVATE));
        } catch(Exception e) {
            Log.e("output file", "OUTPUT file not found.");
            System.exit(2);
        }
        return printWriter;
    }

    /**
     * Checks if this is a question number. If so, returns
     * the question number. If not, return 0, since 0 is
     * an impossible value.
     * @param lineIn the line to check
     * @return the question number
     */
    private static int getQuestionNumber(String lineIn) {
        //too short to be possible
        if(lineIn.length() < 4) return 0;

        //figure out where the end of the part of the String that is the
        //question number ends. index starts with a nonsense value
        int index = -1;
        for(int i = 2; i >= 0; i--) {
            if(lineIn.charAt(i) >= '0' && lineIn.charAt(i) <= '9') {
                index = i;
                break;
            }
        }
        //if there was no index that could yield an int
        if(index == -1) return 0;
        //all characters up to and including the index should be
        //an integer of [0, 9]
        for(int i = 0; i <= index; i++) {
            if(lineIn.charAt(i) < '0' || lineIn.charAt(i) > '9') return 0;
        }

        if(lineIn.substring(index+1, index+3).equals(". "))
            return Integer.parseInt(lineIn.substring(0, index+1));

        return 0;
    }

    /**
     * Returns the value of allTestTypesMissed at the index of
     * testTypeIndex.
     * @param testTypeIndex the index within allTestTypesMissed
     * @return the value stored at that index within allTestTypesMissed
     */
    public static TestTypeMissed getTestTypeMissed(int testTypeIndex) {
        return allTestTypesMissed[testTypeIndex];
    }
}
