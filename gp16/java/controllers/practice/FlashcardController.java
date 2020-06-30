/*
 * @FlashcardController.java 1.0 28/04/2020
 *
 * Copyright (c) 2020 Aberystwyth University
 * All rights reserved
 */
package uk.ac.aber.cs221.gp16.java.controllers.practice;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import uk.ac.aber.cs221.gp16.java.controllers.CommonController;
import uk.ac.aber.cs221.gp16.java.service.Lang;
import uk.ac.aber.cs221.gp16.java.service.RandomGenerator;
import uk.ac.aber.cs221.gp16.java.service.Word;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Loads the flashcard fxml file and provides it with words
 * in a random order from an inputted practice list Array list
 *
 * @author mat80
 * @author sas90
 * @version 1.0
 */

public class FlashcardController extends CommonController
implements Initializable {

    // //////////////// //
    // Class variables. //
    // //////////////// //

    private int timesFlipped = 0;
    @FXML
    private Button flashcard;
    @FXML
    private Button previousButton;
    @FXML
    private Button goToPractice;
    @FXML
    private Button nextButton;

    final RandomGenerator randomGenerator = new RandomGenerator();
    private ArrayList<Word> words = new ArrayList<>();

    private int index = 0;

    // ////////////// //
    // Class methods. //
    // ////////////// //

    /**
     * Initializes the flashcards with values from the practise list
     * and keeps counter timeFlipped to determine language displayed
     * 
     * @param url
     * @param resourceBundle
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        words = getUserDao().getAsListPracticeList();
        words = randomGenerator.shuffleWord(words);

        if (getFlashcardLanguage() == Lang.EN) {
            timesFlipped = 0;
        } else if (getFlashcardLanguage() == Lang.CY) {

            timesFlipped = 1;
        }

        updateDisplay();
    }
    
    /**
     * Updates the flashcard to display next word couple
     * or responds with an error if none exist
     */

    private void updateDisplay() {
        if (words.size() > 0) {
            showText(words.get(index % words.size()));
        } else {
            if (timesFlipped % 2 == 0) {
                flashcard.setText("Add some words!");
            } else if (timesFlipped % 2 == 1) {
                flashcard.setText("Ychwanegu rhai geiriau");
            }
        }
    }
    
    /**
     * Sets the new text to be displayed on the flashcard
     * 
     * @param word
     */

    private void showText(Word word) {
        if (timesFlipped % 2 == 0) {
            flashcard.setText(word.getEnglish());
        } else if (timesFlipped % 2 == 1) {
            flashcard.setText(word.getWelsh());
        }
    }

    /**
     * Flips a flashcard, making it show either welsh or english
     */

    @FXML
    private void flipFlashcard() {
        timesFlipped++;
        updateDisplay();
    }

    /**
     * Increases the index to show the next flashcard
     */

    @FXML
    private void showNextFlashcard() {
        //Go to the next word in the practice list
        if (words.size() > 0) {
            index++;
            if (getFlashcardLanguage() == Lang.EN) {
                timesFlipped = 0;
            } else if (getFlashcardLanguage() == Lang.CY) {
                timesFlipped = 1;
            }
        }
        updateDisplay();
    }

    /**
     * Decreases the index to show he previous flashcard
     */

    @FXML
    private void showPreviousFlashcard() {
        //Go to the previous word in the practice list
        if (words.size() > 0 && index > 0) {
            index--;

            if (getFlashcardLanguage() == Lang.EN) {
                timesFlipped = 0;
            } else if (getFlashcardLanguage() == Lang.CY) {
                timesFlipped = 1;
            }
        }
        updateDisplay();
    }

    /**
     * Goes to the  practice list windows
     *
     * @throws IOException the file might not exist
     */

    @FXML
    private void goToPractice()
    throws IOException {
        String filePath = "../../../resources/fxml/PracticeView.fxml";
        changeScene(filePath, goToPractice);
    }
}
