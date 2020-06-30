/*
 * @Main.java 1.0 28/04/2020
 *
 * Copyright (c) 2020 Aberystwyth University
 * All rights reserved
 */

package uk.ac.aber.cs221.gp16.java;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * the main class
 *
 * @author sas90
 * @author mat80
 * @version 1.0
 */

public class Main extends Application {

    // ////////////// //
    // Class methods. //
    // ////////////// //

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * loads and displays JavaFX GUI
     *
     * @param stage
     * @throws Exception
     */

    @Override
    public void start(Stage stage)
    throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../resources/fxml/DictionaryView.fxml"));
        stage.setTitle("Welsh Vocabulary Tutor");
        stage.setScene(new Scene(root));
        stage.show();
    }
}
