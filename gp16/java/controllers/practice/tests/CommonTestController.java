package uk.ac.aber.cs221.gp16.java.controllers.practice.tests;

import uk.ac.aber.cs221.gp16.java.controllers.CommonController;
import uk.ac.aber.cs221.gp16.java.service.RandomGenerator;
import uk.ac.aber.cs221.gp16.java.service.Word;

import java.util.HashSet;

/**
 * An abstract class that handles logic for all testControllers
 * @author mat80
 * @version 1.0
 */

public abstract class CommonTestController extends CommonController {
    
    // //////////////// //
    // Class variables. //
    // //////////////// //

    static private int totalScore;
    static private int totalPossible;
    static private int totalTests;
    static private String[] testOrder = new String[3]; // 3 is number of total tests available
    static private int testsTaken;
    static private HashSet<Word> wordsTestedList = new HashSet<>();
    
    // //////////////// //
    // Class methods. //
    // //////////////// //

    /**
     * Checks if the two string passed is equal
     *
     * @param solution is the correct answer
     * @param answer is the answer attempted
     * @return true if solution and answer equals.
     */

    public boolean checkAnswer(String solution, String answer) {
        boolean isCorrect = false;
        if (solution.contentEquals(answer)) {
            totalScore += 1;
            isCorrect = true;
        }
        return isCorrect;
    }

    /**
     * Checks if a word has already been used in a test and updates it if it has.
     * @param questionWord is the word being updated.
     * @return questionWord
     */

    public Word getNewTestWord(Word questionWord) {
        if (getWordsTestedList().size() < getUserDao().getPracticeSize()) {
            //If word == null, give it a value
            if (questionWord == null) {
                return getUserDao().getRandomElementFromPractice();
            }
            if (getWordsTestedList().contains(questionWord)) {
                for (Word word : getUserDao().getAsListPracticeList()) {
                    if (!getWordsTestedList().contains(word)) {
                        return word;
                    }
                }
            }
        } else {
            return getUserDao().getRandomElementFromPractice();
        }
        return questionWord;
    }

    /**
     * This method makes sure the tests are randomized
     * @return a filePath to a test
     */

    public String getRandomTestPath() {
        RandomGenerator randomGenerator = new RandomGenerator();
        if (testsTaken % 3 == 0) {
            testOrder = (String[]) randomGenerator.shuffleStrings(testOrder);
        }

        String filePath = "";
        if (wordsTestedList.size() < getUserDao().getPracticeSize() || testsTaken < 3) {
            filePath = testOrder[testsTaken % 3];
        } else {
            filePath = "../../../resources/fxml/PracticeView.fxml";

        }
        return filePath;
    }

    /**
     * Returns a list of all the words tested
     * @return a list of all words tested
     */

    public static HashSet<Word> getWordsTestedList() {
        return wordsTestedList;
    }

    /**
     * Updates the tested word list
     * @param wordsTestedList is the list
     */

    public static void setWordsTestedList(HashSet<Word> wordsTestedList) {
        CommonTestController.wordsTestedList = wordsTestedList;
    }

    /**
     * Returns the total score
     * @return total score
     */

    public static int getTotalScore() {
        return totalScore;
    }

    /**
     * Sets the total score
     * @param totalScore is the score
     */

    public static void setTotalScore(int totalScore) {
        CommonTestController.totalScore = totalScore;
    }

    /**
     * Returns total possible score a user can achieve
     * @return total possible score
     */

    public static int getTotalPossible() {
        return totalPossible;
    }

    /**
     * Sets the total possible score
     * @param totalPossible is the total possible score
     */

    public static void setTotalPossible(int totalPossible) {
        CommonTestController.totalPossible = totalPossible;
    }

    /**
     * Returns the total tests
     * @return total tests
     */

    public static int getTotalTests() {
        return totalTests;
    }

    /**
     * Sets the total tests
     * @param totalTests is the total tests
     */

    public static void setTotalTests(int totalTests) {
        CommonTestController.totalTests = totalTests;
    }

    /**
     * Returns a list with the current test order
     * @return a list of the test order
     */

    public static String[] getTestOrder() {
        return testOrder;
    }

    /**
     * Sets the test order
     * @param testOrder is the order of the tests
     */

    public static void setTestOrder(String[] testOrder) {
        CommonTestController.testOrder = testOrder;
    }

    /**
     * Returns the amount of tests taken
     * @return the amount of tests taken
     */

    public static int getTestsTaken() {
        return testsTaken;
    }

    /**
     * Sets the total tests taken
     * @param testsTaken is the tests taken
     */

    public static void setTestsTaken(int testsTaken) {
        CommonTestController.testsTaken = testsTaken;
    }

    /**
     * Adds a word the list of words tested
     * @param word is the word being added
     */

    public static void addWordsTestedList(Word word) {
        wordsTestedList.add(word);
    }
}
