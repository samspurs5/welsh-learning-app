/*
 * @AddWordController.java 1.0 28/04/2020
 *
 * Copyright (c) 2020 Aberystwyth University
 * All rights reserved
 */
package uk.ac.aber.cs221.gp16.java.controllers.dictionary;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import uk.ac.aber.cs221.gp16.java.controllers.CommonController;
import uk.ac.aber.cs221.gp16.java.service.Type;
import uk.ac.aber.cs221.gp16.java.service.Word;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * loads the add word fxml file and provides it with functionality
 *
 * @author mat80
 * @author sas90
 * @version 1.0
 */

public class AddWordController extends CommonController
implements Initializable {

    // //////////////// //
    // Class variables. //
    // //////////////// //

    @FXML
    private Button cancel;
    @FXML
    private Button confirm;
    @FXML
    private TextField englishTranslation;
    @FXML
    private TextField welshTranslation;
    @FXML
    private ChoiceBox<String> typeOfWord;

    // ////////////// //
    // Class methods. //
    // ////////////// //

    /**
     * Initializes the add items pop up window by adding the possible word types
     * <p>
     * This controller allows a user to create a new word
     *
     * @param url
     * @param resourceBundle
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> typeOfWords = FXCollections.observableArrayList();
        typeOfWords.add("NOUN (MASCULINE)");
        typeOfWords.add("VERB");
        typeOfWords.add("NOUN (FEMININE)");
        typeOfWords.add("ADVERB");
        typeOfWords.add("ADJECTIVE");
        typeOfWord.setItems(typeOfWords);

    }

    /**
     * closes the window, cancelling word creation
     */

    @FXML
    private void closeWindow() {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }

    /**
     * confirms the new word, updating the backend data structures, practise list and dictionary
     */

    @FXML
    private void confirmAction() {
        Stage stage = (Stage) confirm.getScene().getWindow();
        String english = englishTranslation.getText();
        String welsh = welshTranslation.getText();
        String type = typeOfWord.getValue();
        if (!(type == null)) {
            Word word = new Word(english, welsh, toType(type));
            if (!(word.getEnglish().equals("") || word.getWelsh().equals(""))) {
                getUserDao().updateDictionary(word);
                stage.close();
            }
        }
    }

    /**
     * converts the strings for nouns and verbs into their respective enums
     *
     * @param type type as a string
     * @return type as an enum
     */

    private Type toType(String type) {
        if (type.equalsIgnoreCase("NOUN (MASCULINE)")) {
            return Type.nm;
        }
        if (type.equalsIgnoreCase("NOUN (FEMININE)")) {
            return Type.nf;
        }
        if (type.equalsIgnoreCase("VERB")) {
            return Type.verb;
        }
        return Type.other;
    }

    /**
     * gets
     * @return
     */
    public Button getCancel() {
        return cancel;
    }

    public void setCancel(Button cancel) {
        this.cancel = cancel;
    }

    public Button getConfirm() {
        return confirm;
    }

    public void setConfirm(Button confirm) {
        this.confirm = confirm;
    }

    public TextField getEnglishTranslation() {
        return englishTranslation;
    }

    public void setEnglishTranslation(TextField englishTranslation) {
        this.englishTranslation = englishTranslation;
    }

    public TextField getWelshTranslation() {
        return welshTranslation;
    }

    public void setWelshTranslation(TextField welshTranslation) {
        this.welshTranslation = welshTranslation;
    }

    public ChoiceBox<String> getTypeOfWord() {
        return typeOfWord;
    }

    public void setTypeOfWord(ChoiceBox<String> typeOfWord) {
        this.typeOfWord = typeOfWord;
    }
}