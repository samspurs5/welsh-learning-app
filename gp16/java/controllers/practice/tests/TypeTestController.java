/*
 * @TypeTestController.java 1.0 28/04/2020
 *
 * Copyright (c) 2020 Aberystwyth University
 * All rights reserved
 */
package uk.ac.aber.cs221.gp16.java.controllers.practice.tests;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import uk.ac.aber.cs221.gp16.java.service.Lang;
import uk.ac.aber.cs221.gp16.java.service.RandomGenerator;
import uk.ac.aber.cs221.gp16.java.service.Word;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Loads the type test fxml file and provides it with functionality
 *
 * @author mat80
 * @author sas90
 * @version 1.0
 */

public class TypeTestController extends CommonTestController
implements Initializable {

    // //////////////// //
    // Class variables. //
    // //////////////// //

    @FXML
    private Button continueButton;
    @FXML
    private Label questionLabel;
    @FXML
    private Label solution;
    @FXML
    private TextField answer;
    @FXML
    private Label score;

    private final RandomGenerator randomGenerator = new RandomGenerator();
    private Word questionWord = getUserDao().getRandomElementFromPractice();
    private Lang language = Lang.EN;
    int mouseClicks = 0;

    // ////////////// //
    // Class methods. //
    // ////////////// //

    /**
     * Initializes the type test with words from the practice list, randomly deciding whether the question is displayed
     * in welsh or english
     *
     * @param url
     * @param resourceBundle
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setup();
        String word = "";
        if (language == Lang.EN) {
            word = questionWord.getWelsh();
        } else if (language == Lang.CY) {
            word = questionWord.getEnglish();
        }

        questionLabel.setText("What is '" + word + "' in " + language);
        score.setText(getTotalScore() + "/" + getTotalPossible());
    }

    /**
     * Randomizes the language of tested word and determines logic for test sequence
     */

    private void setup() {
        solution.setVisible(false);
        //There is a 50% chance of the word to be displayed in welsh
        if (randomGenerator.isSuccessful(1, 2)) {
            language = Lang.CY;
        }

        questionWord = getNewTestWord(questionWord);
        addWordsTestedList(questionWord);
        setTotalPossible(getTotalPossible() + 1);
        setTestsTaken(getTestsTaken() + 1);

    }


    /**
     * Continues to the next test
     *
     * @throws IOException file might not exist
     */

    @FXML
    private void continueTest()
    throws IOException {
        if (mouseClicks == 0) {
            solution.setVisible(true);

            checkAndUpdateAnswer();
            score.setText(getTotalScore() + "/" + getTotalPossible());
            continueButton.setText("CONTINUE");
        } else {
            String filePath = "../" + getRandomTestPath();
            changeScene(filePath, continueButton);
        }
        mouseClicks++;
    }

    /**
     * Checks if the answer is correct
     *
     * @return if the answer is correct or not
     */

    private void checkAndUpdateAnswer() {
        String questionWordAnswer = "";
        if (language == Lang.EN) {
            questionWordAnswer = questionWord.getEnglish();
        } else if (language == Lang.CY) {
            questionWordAnswer = questionWord.getWelsh();
        }

        if (checkAnswer(questionWordAnswer, answer.getText())) {
            answer.getStyleClass().add("correct-choiceBox");
            answer.getStyleClass().add("correct");
        } else {
            answer.getStyleClass().add("error-choiceBox");
            answer.getStyleClass().add("error");
        }

        solution.setText(questionWordAnswer);
        solution.getStyleClass().add("correct");
    }

    /**
     * Returns question word
     * @return
     */

    public Word getQuestionWord() {
        return questionWord;
    }

    /**
     * Sets question word
     * @param questionWord
     */

    public void setQuestionWord(Word questionWord) {
        this.questionWord = questionWord;
    }

    /**
     * Gets language
     * @return
     */

    public Lang getLanguage() {
        return language;
    }

    /**
     * Sets language
     * @param language
     */

    public void setLanguage(Lang language) {
        this.language = language;
    }

    /**
     * Returns number of mouse clicks
     * @return
     */

    public int getMouseClicks() {
        return mouseClicks;
    }

    /**
     * Sets number of mouse clicks
     * @param mouseClicks
     */

    public void setMouseClicks(int mouseClicks) {
        this.mouseClicks = mouseClicks;
    }
}

