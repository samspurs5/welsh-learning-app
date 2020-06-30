package uk.ac.aber.cs221.gp16.java.controllers.practice;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import uk.ac.aber.cs221.gp16.java.controllers.CommonController;
import uk.ac.aber.cs221.gp16.java.service.Lang;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * A class that decides which language the flashcards should be set in
 * @author mat80
 * @version 1.0
 */

public class FlashcardChooseController extends CommonController {

    // //////////////// //
    // Class variables. //
    // //////////////// //

    @FXML
    private Button englishVersion;
    @FXML
    private Button welshVersion;

    // ////////////// //
    // Class methods. //
    // ////////////// //

    /**
     * A method that loads the flashcards in English
     * @throws IOException if filePath does not exist
     */

   @FXML
    private void setEnglishVersion()
    throws IOException {
       setFlashcardLanguage(Lang.EN);
        String filePath = "../../../resources/fxml/Flashcard.fxml";
        changeScene(filePath, englishVersion);
   }

    /**
     * A method that loads the flashcards in Welsh
     * @throws IOException
     */

   @FXML
    private void setWelshVersion()
    throws IOException {
       setFlashcardLanguage(Lang.CY);
       String filePath = "../../../resources/fxml/Flashcard.fxml";
       changeScene(filePath, welshVersion);
   }
}
