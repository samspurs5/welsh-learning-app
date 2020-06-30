/*
 * @PracticeController.java 1.0 28/04/2020
 *
 * Copyright (c) 2020 Aberystwyth University
 * All rights reserved
 */

package uk.ac.aber.cs221.gp16.java.controllers.practice;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import uk.ac.aber.cs221.gp16.java.controllers.practice.tests.CommonTestController;
import uk.ac.aber.cs221.gp16.java.service.Word;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;

/**
 * loads the practice list fxml file and provides it with functionality
 *
 * @author mat80
 * @author sas90
 * @version 1.0
 */

public class PracticeController extends CommonTestController
implements Initializable {

    // //////////////// //
    // Class variables. //
    // //////////////// //

    @FXML
    private Button goToFlashcards;
    @FXML
    private Button goToTest;
    @FXML
    private Button goToDictionary;
    @FXML
    private TableView<Word> tableViewDictionary;
    @FXML
    private TableColumn<Word, String> englishColumn;
    @FXML
    private TableColumn<Word, String> welshColumn;

    // ////////////// //
    // Class methods. //
    // ////////////// //

    /**
     * Initializes the practice list window with words from the practice list
     * 
     * This controller allows the user to remove words from the practice list, by setting the row factory to delete
     * words by double click
     *
     * @param url
     * @param resourceBundle
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        update();
        englishColumn.setCellValueFactory(new PropertyValueFactory<>("displayEnglish"));
        welshColumn.setCellValueFactory(new PropertyValueFactory<>("displayWelsh"));
        tableViewDictionary.setItems(getUserDao().getAsObservablePracticeList());
        englishColumn.setSortable(false);
        welshColumn.setSortable(false);
        tableViewDictionary.setRowFactory(tv -> deleteRowSelector());
    }

    /**
     * Removes word from practice list when clicked
     *
     * @return
     */

    private TableRow<Word> deleteRowSelector() {
        {
            TableRow<Word> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (tableViewDictionary.getSelectionModel().getSelectedItem() != null) {
                    Word word = tableViewDictionary.getSelectionModel().getSelectedItem();
                    getUserDao().delete(word);
                    tableViewDictionary.setItems(getUserDao().getAsObservablePracticeList());
                }
            });
            return row;
        }
    }

    /**
     * Button which links to the dictionary page
     *
     * @throws IOException file might not exist
     */

    @FXML
    private void goToDictionary()
    throws IOException {
        String filePath = "../../../resources/fxml/DictionaryView.fxml";
        changeScene(filePath, goToDictionary);
    }

    /**
     * Button which links to the flashcard page
     *
     * @throws IOException file might not exist
     */

    @FXML
    private void goToFlashcards()
    throws IOException {
        String filePath = "../../../resources/fxml/FlashcardChoose.fxml";
        changeScene(filePath, goToFlashcards);
    }

    /**
     * Button which links to the test page (if the practice list has more than 4 words)
     *
     * @throws IOException file might not exist
     */

    @FXML
    private void goToTest()
    throws IOException {
        update();

        setTestsTaken(0);
        setTotalPossible(0);
        setTotalScore(0);
        setTotalTests(getUserDao().getPracticeSize());

        setWordsTestedList(new HashSet<>());
        String[] testOrder = new String[3];
        testOrder[0] = "../../../resources/fxml/CompareTest.fxml";
        testOrder[1] = "../../../resources/fxml/TypeTest.fxml";
        testOrder[2] = "../../../resources/fxml/MultipleChoiceTest.fxml";
        setTestOrder(testOrder);

        String filePath = getRandomTestPath();
        changeScene(filePath, goToTest);
    }

    /**
     * Checks if theres more than 4 words in the practice list
     */

    @FXML
    private boolean checkWords() {
        //User should not be able to do tests unless there are at least 4 words in the practice list
        return (getUserDao().getPracticeSize() < 4);
    }

    /**
     * Updates the "test yourself" button depending on total words in practice list
     */

    @FXML
    private void update(){
        if (checkWords()) {
            goToTest.setDisable(true);
            }
    }
}
