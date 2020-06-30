/*
 * @DictionaryController.java 1.0 28/04/2020
 *
 * Copyright (c) 2020 Aberystwyth University
 * All rights reserved
 */


package uk.ac.aber.cs221.gp16.java.controllers.dictionary;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import uk.ac.aber.cs221.gp16.java.controllers.CommonController;
import uk.ac.aber.cs221.gp16.java.service.Lang;
import uk.ac.aber.cs221.gp16.java.service.Word;

import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;

/**
 * loads the dictionary fxml file and provides it with functionality
 *
 * @author mat80
 * @author sas90
 * @author ilg2
 * @version 1.0
 * @see uk.ac.aber.cs221.gp16.java.dao.UserDao
 */

@SuppressWarnings("ALL")
public class DictionaryController extends CommonController
implements Initializable {

    // //////////////// //
    // Class variables. //
    // //////////////// //

    private Lang lang = Lang.EN;
    @FXML
    private TableView<Word> tableViewDictionary;
    @FXML
    private TableColumn<Word, String> englishColumn;
    @FXML
    private TableColumn<Word, String> welshColumn;
    @FXML
    private Button goToPractice;
    @FXML
    private Button addToDictionary;
    @FXML
    private Button goToHelp;
    @FXML
    private TextField search;

    // ////////////// //
    // Class methods. //
    // ////////////// //

    /**
     * initializes the dictionary window
     * <p>
     * this allows the user to view, sort and select words for the practise list. The user can also search for words.
     * There are also buttons which link to the help, practise and add word windows.
     * <p>
     * the default comparator for sorting is overwritten for the english and welsh columns, this is so that they ignore
     * the extra formatting, for example, an english verb is displayed as "to" run. The english column sort ignores this
     *
     * @param url
     * @param resourceBundle
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        englishColumn.setCellValueFactory(new PropertyValueFactory<>("displayEnglish"));
        welshColumn.setCellValueFactory(new PropertyValueFactory<>("displayWelsh"));
        getUserDao().setLang(lang);
        tableViewDictionary.setItems(getUserDao().getAsObservableDictionary());
        tableViewDictionary.setRowFactory(tv -> getRow());
        setSorting();
    }

    /*
     * A method that favorites a word if it is selected and highlights it in the dictionary
     */

    private TableRow<Word> getRow() {
        {
            TableRow<Word> row = new TableRow<>() {
                //Updates highlights the row selected
                @Override
                protected void updateItem(Word word, boolean empty) {
                    super.updateItem(word, empty);
                    if (empty || word == null) {
                        getStyleClass().add("not-favorite");
                    } else {
                        if (!word.isFavorite()) {
                            getStyleClass().add("not-favorite");
                        } else {
                            getStyleClass().add("favorite");
                        }
                    }
                    tableViewDictionary.refresh();
                }
            };
            //Favorites the selected word
            if (tableViewDictionary.getSelectionModel().getSelectedItem() != null) {
                Word word = tableViewDictionary.getSelectionModel().getSelectedItem();
                getUserDao().favorite(word);
            }
            return row;
        }
    }

    private void setSorting() {
        Comparator<String> englishComparator =
                Comparator.comparing((String v) -> (v.toLowerCase().replace("to ", "")));
        Comparator<String> welshComparator =
                Comparator.comparing((String v) -> v.toLowerCase().split(" \\(")[0]);
        welshColumn.setComparator(welshComparator);
        englishColumn.setComparator(englishComparator);
    }

    /**
     * A method that changes to the scene where you can add a word to the dictionary
     *
     * @throws IOException the file might be missing
     */

    @FXML
    private void addToDictionary()
    throws IOException {
        String filePath = "../../../resources/fxml/AddWordPopUp.fxml";
        String title = "Add word";
        showPopUp(filePath, title);
        tableViewDictionary.setItems(getUserDao().getAsObservableDictionary());
    }


    /**
     * A method that changes the scene to the help scene
     *
     * @throws IOException the file might be missing
     */

    @FXML
    private void goToHelp()
    throws IOException {
        String filePath = "../../../resources/fxml/HelpView.fxml";
        changeScene(filePath, goToHelp);
    }

    /**
     * A method that changes the scene to the practice scene
     *
     * @throws IOException the file might be missing
     */

    @FXML
    private void goToPractice()
    throws IOException {
        String filePath = "../../../resources/fxml/PracticeView.fxml";
        changeScene(filePath, goToPractice);
    }

    /**
     * A method that calls the DAO objects search method, with a partial or full word. What is returned is a list of
     * words that match the substring.
     */

    @FXML
    private void searchDictionary() {
        getUserDao().setLang(lang);
        ObservableList<Word> searchedWords = FXCollections.observableArrayList();
        String searchWord = search.getText();
        searchedWords.setAll(getUserDao().search(searchWord));
        tableViewDictionary.setItems(searchedWords);
        tableViewDictionary.refresh();
    }


    /**
     * this method captures a sort event, and changes the language of the backend dictionary to match, it also changes
     * the language of the front-end dictionary representation
     */

    @FXML
    private void onSort() {
        try {
            TableColumn sortColumn = tableViewDictionary.getSortOrder().get(0);
            if (sortColumn.getId().contains("englishColumn")) {
                lang = Lang.EN;
                getUserDao().setLang(lang);
            } else if (sortColumn.getId().contains("welshColumn")) {
                lang = Lang.CY;
                getUserDao().setLang(lang);
            }
        } catch (RuntimeException ignored) {
        }


    }

}
