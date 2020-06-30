/*
 * @HelpController.java 1.3 28/04/2020
 *
 * Copyright (c) 2020 Aberystwyth University
 * All rights reserved
 */

package uk.ac.aber.cs221.gp16.java.controllers.dictionary;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import uk.ac.aber.cs221.gp16.java.controllers.CommonController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * loads the help fxml file and provides it with functionality
 *
 * @author jab166
 * @author mat80
 * @version 1.0
 */

public class HelpController extends CommonController
implements Initializable {

    @FXML
    private Button goToDictionary;
    @FXML
    private Label helpInfo;
    @FXML
    private TreeView<String> helpView;

    /**
     * initialises the help menu using a tree data structure to organise the information
     *
     * @param url
     * @param resourceBundle
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        helpInfo.setText("WELSH VOCABULARY TUTOR \n\n" +
                "This help page provides details on how each section works. " +
                "Click on the\ncategories to the left to display more information.");
        TreeItem<String> main = new TreeItem<>("Welsh Vocabulary Tutor");
        TreeItem<String> dictionary = new TreeItem<>("Dictionary");
        TreeItem<String> addToPracticeList = new TreeItem<>("Adding to Practice list");
        TreeItem<String> addToDictionary = new TreeItem<>("Adding to Dictionary");
        TreeItem<String> search = new TreeItem<>("Looking for words");
        TreeItem<String> practice = new TreeItem<>("Practice");
        TreeItem<String> flashcards = new TreeItem<>("Flashcards");
        TreeItem<String> tests = new TreeItem<>("Test");
        TreeItem<String> compareTest = new TreeItem<>("Compare test");
        TreeItem<String> multiChoiceTest = new TreeItem<>("Multiple choice test");
        TreeItem<String> typingTest = new TreeItem<>("Typing test");
        main.getChildren().add(dictionary);
        main.getChildren().add(practice);
        dictionary.getChildren().add(addToPracticeList);
        dictionary.getChildren().add(addToDictionary);
        dictionary.getChildren().add(search);
        practice.getChildren().add(flashcards);
        practice.getChildren().add(tests);
        tests.getChildren().add(compareTest);
        tests.getChildren().add(multiChoiceTest);
        tests.getChildren().add(typingTest);

        helpView.setRoot(main);
        main.setExpanded(true);
        dictionary.setExpanded(true);
        practice.setExpanded(true);
        tests.setExpanded(true);
        helpView.getSelectionModel().selectedItemProperty().addListener((observable, helpInfo, newValue) -> displayHelp(newValue));
    }

    /**
     * displays help menu
     *
     * @param text text giving information on each option
     */

    private void displayHelp(Object text){
        switch (String.valueOf(text)) {
            case "TreeItem [ value: Dictionary ]":
                helpInfo.setText("DICTIONARY \n\n" +
                        "This section includes details on the dictionary page. Click on the subsections for\nmore detailed information.");
                break;
            case "TreeItem [ value: Adding to Practice list ]":
                helpInfo.setText("ADDING TO PRACTICE LIST\n\n" +
                        "Select any word or phrase in the dictionary to save it in the practice list. \n" +
                        "The word or phrase will be highlighted to show that it is currently in the\npractice list.");
                break;
            case "TreeItem [ value: Adding to Dictionary ]":
                helpInfo.setText("ADDING TO DICTIONARY \n\n" +
                        "Click the 'ADD TO DICTIONARY LIST' button to add a new word to the dictionary. \n" +
                        "Enter the English and Welsh translation and select the correct word type from the drop \ndown menu. " +
                        "Adding a new word will automatically add it to the practice list.\n ");
                break;
            case "TreeItem [ value: Looking for words ]":
                helpInfo.setText("LOOKING FOR WORDS  \n\n" +
                        "Use the search bar located at the bottom of the dictionary list to search for a word.\n" +
                        "You can search in both English and Welsh by selecting the 'English' or 'Welsh' \ncolumn in the table. ");
                break;
            case "TreeItem [ value: Practice ]":
                helpInfo.setText("PRACTICE MENU \n\n" +
                        "The practice menu contains a list of all the words you have selected in the dictionary.\n"+
                        "In this menu you can practice your selected words using flashcards and different tests. \n" +
                        "Select a word in the practice list to remove it.\nClick on the subsections to the left for more information."
                     );
                break;
            case "TreeItem [ value: Flashcards ]":
                helpInfo.setText("FLASHCARDS\n\n" +
                        "The flashcards will be displayed in either English or Welsh depending on your choice.\n"+
                        "The flashcards will display words from the practice list in your selected language.\n" +
                        "To see the translation, click on the displayed word.\n"+
                        "You can go back and fourth to show different words from the practice list.");
                break;
            case "TreeItem [ value: Test ]":
                helpInfo.setText("TESTS \n\n" +
                        "After selecting the 'TEST YOURSELF' button, a test will start and go randomly through \nevery word in the practice list. " +
                        "At least four words are required to launch the tests.\n" +
                        "You cannot exit the tests until they are completed.");
                break;
            case "TreeItem [ value: Welsh Vocabulary Tutor ]":
                helpInfo.setText("WELSH VOCABULARY TUTOR \n\n" +
                        "This help page provides details on how each section works. " +
                        "Click on the\ncategories to the left to display more information");
                break;
            case "TreeItem [ value: Compare test ]":
                helpInfo.setText("COMPARE TEST\n\n" +
                        "In this test, you are given four words in English or Welsh. You have to match \nthe words to the correct translation. "+
                        "You will be given feedback \non your answers before continuing the next test.");
                break;
            case "TreeItem [ value: Multiple choice test ]":
                helpInfo.setText("MULTIPLE CHOICE TEST\n\n" +
                        "In this test, you are given a word in either English or Welsh, and six possible \ntranslations where only one is correct. " +
                        "You will be given feedback on your\nanswer before continuing the next test.");
                break;
            case "TreeItem [ value: Typing test ]":
                helpInfo.setText("TYPING TEST \n\n" +
                        "In this test, you will be given a word in either English or Welsh. It's your job the type\nout what you think is the correct translation of that word is. " +
                        "You will be given feedback \non your answer before continuing the next test.");
                break;
            default:
                helpInfo.setText("");
                break;
        }
    }

    /**
     * goes to the dictionary window
     *
     * @throws IOException file might not exist
     */

    @FXML
    private void goToDictionary()
    throws IOException {
        String filePath = "../../../resources/fxml/DictionaryView.fxml";
        changeScene(filePath, goToDictionary);
    }

}
