/*
 * @CommonController.java 1.0 28/04/2020
 *
 * Copyright (c) 2020 Aberystwyth University
 * All rights reserved
 */

package uk.ac.aber.cs221.gp16.java.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import uk.ac.aber.cs221.gp16.java.dao.Dao;
import uk.ac.aber.cs221.gp16.java.dao.UserDao;
import uk.ac.aber.cs221.gp16.java.service.Lang;

import java.io.IOException;

/**
 * provides methods for all controllers.
 *
 * @author mat80
 * @version 1.0
 */
public abstract class CommonController {

    // ////////// //
    // Constants. //
    // ////////// //

    static private Dao userDao = new UserDao();
    static private Lang flashcardLanguage;

    // ////////////// //
    // Class methods. //
    // ////////////// //

    /**
     * A method that changes the scene
     *
     * @param filePath is the path to to the new file
     * @param button        is the button used to overwrite the current scene
     * @throws IOException if the filepath is wrong
     */

    @FXML
    public void changeScene(String filePath, Button button) 
    throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(filePath));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) button.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Creates a pop up window with the given fxml file
     *
     * @param filePath path to fxml file
     * @param title    title of the window
     * @throws IOException file might not exist
     */

    public void showPopUp(String filePath, String title) 
    throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(filePath));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(scene);
        stage.showAndWait();
    }

    /**
     * Getter for userDao
     *
     * @return userDao data access object
     */

    public static Dao getUserDao() {
        return userDao;
    }

    /**
     * Setter for useDao
     *
     * @param userDao data access object
     */

    public static void setUserDao(Dao userDao) {
        CommonController.userDao = userDao;
    }

    /**
     * Getter for flashcard language
     *
     * @return flashcard language
     */

    public static Lang getFlashcardLanguage() {
        return flashcardLanguage;
    }

    /**
     * Setter for flashcard language
     *
     * @param flashcardLanguage flashcard language
     */

    public static void setFlashcardLanguage(Lang flashcardLanguage) {
        CommonController.flashcardLanguage = flashcardLanguage;
    }
}
