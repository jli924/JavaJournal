package cs3500.pa05.controller;

import cs3500.pa05.model.JavaJournal;
import java.io.File;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Represents a JavaJournalController
 */
public interface JavaJournalController {

  /**
   * To run the journal
   */
  void run();

  /**
   * To save the journal to a file
   * @param file the file to save to
   */
  void saveToFile(File file);

  /**
   * To open the journal from a file
   * @param file the file to open from
   */
  JavaJournal openFile(File file);

  /**
   * To show the splash screen
   * @return the scene of the screen
   */
  Scene showSplashScreen();

  /**
   * To show the password screen
   * @param field the field for the screen
   * @return the stage for the screen
   */
  Stage showPasswordScreen(TextField field);

  /**
   * To close the splash screen after 2 seconds
   * @param splash the splash screen
   * @param journal the journal screen
   * @param scene the scene for the journal
   * @param password the password screen
   * @param passwordField the field for the password screen
   */
  void closeSplashScreen(Stage splash, Stage journal, Scene scene, Stage password,
                         TextField passwordField);

}
