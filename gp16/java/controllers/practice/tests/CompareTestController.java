/*
 * @CompareTestController.java 1.0 28/04/2020
 *
 * Copyright (c) 2020 Aberystwyth University
 * All rights reserved
 */
package uk.ac.aber.cs221.gp16.java.controllers.practice.tests;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import uk.ac.aber.cs221.gp16.java.service.Lang;
import uk.ac.aber.cs221.gp16.java.service.RandomGenerator;
import uk.ac.aber.cs221.gp16.java.service.Word;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * loads compare test fxml file and provides it with functionality
 *
 * @author mat80
 * @author sas90
 * @version 1.0
 */
public class CompareTestController extends CommonTestController
        implements Initializable {

    // //////////////// //
    // Class variables. //
    // //////////////// //

    @FXML
    private Button continueButton;

    @FXML
    private Label word_A;
    @FXML
    private Label word_B;
    @FXML
    private Label word_C;
    @FXML
    private Label word_D;

    @FXML
    private Label answerSlot_A;
    @FXML
    private Label answerSlot_B;
    @FXML
    private Label answerSlot_C;
    @FXML
    private Label answerSlot_D;

    @FXML
    private Label solutionA;
    @FXML
    private Label solutionB;
    @FXML
    private Label solutionC;
    @FXML
    private Label solutionD;

    @FXML
    private Label score;

    @FXML
    private ChoiceBox<String> choiceBoxA;
    @FXML
    private ChoiceBox<String> choiceBoxB;
    @FXML
    private ChoiceBox<String> choiceBoxC;
    @FXML
    private ChoiceBox<String> choiceBoxD;


    final ArrayList<Word> words = new ArrayList<>();
    ChoiceBox[] choiceBoxes;
    private Label[] solutions;
    private Label[] answers;
    private Label[] labels;
    final RandomGenerator randomGenerator = new RandomGenerator();
    int mouseClicks = 0;
    Lang language = Lang.EN;

    // ////////////// //
    // Class methods. //
    // ////////////// //

    /**
     * gets the compare test scene and initializes it with words from the practise list
     * <p>
     * this test makes the user match the welsh with the english equivalent
     *
     * @param url
     * @param resourceBundle
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setup();

        String[] answerStrings = getAnswers();
        answerStrings = (String[]) randomGenerator.shuffleStrings(answerStrings);
        for (int index = 0; index < labels.length; index++) {
            answers[index].setText(answerStrings[index]);
        }

        ObservableList<String> choiceBoxOptions = FXCollections.observableArrayList();
        for (Label answer : answers) {
            choiceBoxOptions.add(answer.getText());
        }
        for (int index = 0; index < choiceBoxOptions.size(); index++) {
            choiceBoxes[index].setItems(choiceBoxOptions);
        }
        score.setText(getTotalScore() + "/" + getTotalPossible());
    }


    private String[] getAnswers() {
        String[] welsh = new String[labels.length];
        String[] english = new String[labels.length];
        String[] answerStrings = new String[labels.length];
        for (int index = 0; index < labels.length; index++) {
            Word word = getUserDao().getRandomElementFromPractice();
            //Make sure all the words are different
            while (words.contains(word)) {
                word = getNewTestWord(word);
            }
            words.add(word);
            addWordsTestedList(word);

            welsh[index] = words.get(index).getWelsh();
            english[index] = words.get(index).getEnglish();
            if (language == Lang.EN) {
                labels[index].setText(english[index]);
                answerStrings[index] = (welsh[index]);
                solutions[index].setText(welsh[index]);
            } else {
                labels[index].setText(welsh[index]);
                answerStrings[index] = (english[index]);
                solutions[index].setText(english[index]);
            }
        }
        return answerStrings;
    }


    private void setup() {
        labels = new Label[]{word_A, word_B, word_C, word_D};
        answers = new Label[]{answerSlot_A, answerSlot_B, answerSlot_C, answerSlot_D};
        solutions = new Label[]{solutionA, solutionB, solutionC, solutionD};
        for (Label solution : solutions) {
            solution.setVisible(false);
        }
        if (randomGenerator.isSuccessful(1, 2)) {
            language = Lang.CY;
        }
        setTotalPossible(getTotalPossible() + 4);
        setTestsTaken(getTestsTaken() + 1);
        choiceBoxes = new ChoiceBox[]{choiceBoxA, choiceBoxB, choiceBoxC, choiceBoxD};
    }

    /**
     * Continues to the next test
     *
     * @throws IOException file might not exist
     */

    @FXML
    private void continueTest()
            throws IOException {
        //Should be checking answer, giving feedback and then continuing.
        if (mouseClicks == 0) {
            for (Label solution : solutions) {
                solution.setVisible(true);
                solution.getStyleClass().add("correct");
            }
            checkAnswers();
            score.setText(getTotalScore() + "/" + getTotalPossible());
            continueButton.setText("CONTINUE");
        } else {
            String filePath = "../" + getRandomTestPath();
            changeScene(filePath, continueButton);
        }
        mouseClicks++;
    }

    /**
     * Compares user input information to correct information, updating the front end to inform user of the result
     */

    private void checkAnswers() {
        if (language == Lang.CY) {
            for (int index = 0; index < words.size(); index++) {
                if (choiceBoxes[index].getValue() != null) {
                    if (checkAnswer(words.get(index).getEnglish(), (String) choiceBoxes[index].getValue())) {
                        choiceBoxes[index].getStyleClass().add("correct-choiceBox");
                    } else {
                        choiceBoxes[index].getStyleClass().add("error-choiceBox");
                    }
                } else {
                    choiceBoxes[index].getStyleClass().add("error-choiceBox");
                }
            }
        } else {
            for (int index = 0; index < words.size(); index++) {
                if (choiceBoxes[index].getValue() != null) {
                    if (checkAnswer(words.get(index).getWelsh(), (String) choiceBoxes[index].getValue())) {
                        choiceBoxes[index].getStyleClass().add("correct-choiceBox");
                    } else {
                        choiceBoxes[index].getStyleClass().add("error-choiceBox");
                    }
                } else {
                    choiceBoxes[index].getStyleClass().add("error-choiceBox");
                }
            }
        }
    }

    /**
     * Returns RandomGenerator object
     *
     * @return randomGenerator
     */

    public RandomGenerator getRandomGenerator() {
        return randomGenerator;
    }

    /**
     * Returns the number of mouse clicks
     *
     * @return mouseClicks
     */
    public int getMouseClicks() {
        return mouseClicks;
    }

    /**
     * Sets the number of mouse clicks
     *
     * @param mouseClicks
     */

    public void setMouseClicks(int mouseClicks) {
        this.mouseClicks = mouseClicks;
    }

    /**
     * Returns ArrayList<Word> values
     *
     * @return words
     */

    public ArrayList<Word> getWords() {
        return words;
    }

    /**
     * Returns the value for Lang object
     *
     * @return language
     */

    public Lang getLanguage() {
        return language;
    }

    /**
     * Sets the value for Lang object
     *
     * @param language
     */

    public void setLanguage(Lang language) {
        this.language = language;
    }
}
