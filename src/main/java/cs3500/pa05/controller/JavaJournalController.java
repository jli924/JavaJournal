package cs3500.pa05.controller;

import cs3500.pa05.model.JavaJournal;
import java.io.File;
import javafx.scene.Scene;
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
   * To a splash screen
   * @return the scene of the screen
   */
  Scene showSplashScreen();

  /**
   * To close the splash screen after 2 seconds
   * @param splash the splash screen
   * @param journal the journal
   * @param scene the scene for the journal
   */
  void closeSplashScreen(Stage splash, Stage journal, Scene scene);

}
