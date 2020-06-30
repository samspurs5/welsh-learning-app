/*
 * @MultipleChoiceTestController.java 1.0 28/04/2020
 *
 * Copyright (c) 2020 Aberystwyth University
 * All rights reserved
 */

package uk.ac.aber.cs221.gp16.java.controllers.practice.tests;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import uk.ac.aber.cs221.gp16.java.service.Lang;
import uk.ac.aber.cs221.gp16.java.service.RandomGenerator;
import uk.ac.aber.cs221.gp16.java.service.Word;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * loads the multiple choice fxml file and provides it with functionality
 *
 * @author mat80
 * @author sas90
 * @version 1.0
 */

public class MultipleChoiceTestController extends CommonTestController
implements Initializable {
    
    // //////////////// //
    // Class variables. //
    // //////////////// //
    
    @FXML
    private Button continueButton;
    @FXML
    private RadioButton answerRadioButtonOne;
    @FXML
    private RadioButton answerRadioButtonTwo;
    @FXML
    private RadioButton answerRadioButtonThree;
    @FXML
    private RadioButton answerRadioButtonFour;
    @FXML
    private RadioButton answerRadioButtonFive;
    @FXML
    private RadioButton answerRadioButtonSix;
    @FXML
    private Label questionLabel;
    @FXML
    private Label score;

    private RadioButton[] radioButtons;

    private Word questionWord = getUserDao().getRandomElementFromPractice();

    private final RandomGenerator randomGenerator = new RandomGenerator();
    private Lang language = Lang.EN;
    private int mouseClicks = 0;
    private String answer;
    
    // //////////////// //
    // Class methods. //
    // //////////////// //

    /**
     * initializes the multiple choice window with words from the practice list
     *
     * @param url
     * @param resourceBundle
     */

    public void initialize(URL url, ResourceBundle resourceBundle) {

        setup();

        String[] answers = getAnswers();
        //shuffle randomly
        answers = (String[]) randomGenerator.shuffleStrings(answers);
        //sets the radio buttons to the answers
        for (int integer = 0; integer < radioButtons.length; integer++) {
            radioButtons[integer].setText(answers[integer]);
        }

        questionLabel.setText("What is '" + answer + "' in " + language);
        score.setText(getTotalScore() + "/" + getTotalPossible());
    }

    private String[] getAnswers() {
        /*gets random words from the dictionary, then sets the first element to the answer, then shuffles the array
        the language the words are in depends on random chance*/
        String[] answers = new String[radioButtons.length];
        if (language == Lang.EN) {
            for (int integer = 0; integer < answers.length; integer++) {
                Word randomWord = getUserDao().getRandomElementFromDictionary();
                answers[integer] = randomWord.getEnglish();
            }
            answers[0] = questionWord.getEnglish();
            answer = questionWord.getWelsh();
        } else if (language == Lang.CY) {
            for (int integer = 0; integer < answers.length; integer++) {
                Word randomWord = getUserDao().getRandomElementFromDictionary();
                answers[integer] = randomWord.getWelsh();
            }
            answers[0] = questionWord.getWelsh();
            answer = questionWord.getEnglish();
        }
        return answers;
    }

    private void setup() {
        //sets the language for the test
        if (randomGenerator.isSuccessful(1, 2)) {
            language = Lang.CY;
        }
        radioButtons = new RadioButton[]{answerRadioButtonOne, answerRadioButtonTwo, answerRadioButtonThree,
                answerRadioButtonFour, answerRadioButtonFive, answerRadioButtonSix};
        questionWord = getNewTestWord(questionWord);
        addWordsTestedList(questionWord);
        setTotalPossible(getTotalPossible() + 1);
        setTestsTaken(getTestsTaken() + 1);
    }

    @FXML
    private void updateAnswer1() {
        updateAnswer(answerRadioButtonOne);
    }

    @FXML
    private void updateAnswer2() {
        updateAnswer(answerRadioButtonTwo);
    }

    @FXML
    private void updateAnswer3() {
        updateAnswer(answerRadioButtonThree);
    }

    @FXML
    private void updateAnswer4() {
        updateAnswer(answerRadioButtonFour);
    }

    @FXML
    private void updateAnswer5() {
        updateAnswer(answerRadioButtonFive);
    }

    @FXML
    private void updateAnswer6() {
        updateAnswer(answerRadioButtonSix);
    }

    /**
     * updates the answer depending on what the user clicks on
     *
     * @param answerSelected
     */

    private void updateAnswer(RadioButton answerSelected) {
        for (RadioButton radioButton : radioButtons) {
            radioButton.setSelected(false);
        }
        answerSelected.setSelected(true);
    }

    /**
     * continues to the next test
     *
     * @throws IOException file might not exist
     */

    @FXML
    private void continueTest() throws IOException {
        if (mouseClicks == 0) {
            if (language == Lang.EN) {
                checkAnswer(questionWord.getEnglish(), getSelectedAnswer());
            } else {
                checkAnswer(questionWord.getWelsh(), getSelectedAnswer());
            }
            showCorrectAnswer();
            score.setText(getTotalScore() + "/" + getTotalPossible());
            continueButton.setText("CONTINUE");
        } else {
            String filePath = "../" + getRandomTestPath();
            changeScene(filePath, continueButton);
        }
        mouseClicks++;
    }

    /**
     * Shows the correct and incorrect answers accordingly.
     */

    private void showCorrectAnswer() {
        String answer;
        if (language == Lang.EN) {
            answer = questionWord.getEnglish();
        } else {
            answer = questionWord.getWelsh();
        }
        //gives the user feedback, sets the correct answer to green
        for (RadioButton radioButton : radioButtons) {
            if (radioButton.getText().contentEquals(answer)) {
                radioButton.getStyleClass().add("correct");
            } else {
                radioButton.getStyleClass().add("error");
            }
        }
    }

    /**
     * Returns the button when selected, otherwise an empty string otherwise.
     *
     * @return radio button when select, or empty string otherwise.
     */

    private String getSelectedAnswer() {
        //searches through the radio buttons and then checks if they've been selected, returning that button
        for (RadioButton radioButton : radioButtons) {
            if (radioButton.isSelected()) {
                return radioButton.getText();
            }
        }
        return "";
    }


}
