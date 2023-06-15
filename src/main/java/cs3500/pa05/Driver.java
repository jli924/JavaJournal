package cs3500.pa05;

import cs3500.pa05.controller.JavaJournalController;
import cs3500.pa05.controller.JavaJournalControllerImpl;
import javafx.application.Application;
import javafx.stage.Stage;
import cs3500.pa05.view.JavaJournalView;
import cs3500.pa05.view.JavaJournalViewImpl;


public class Driver extends Application {

  /**
   * Starts the GUI for a game of Whack-A-Mole.
   *
   * @param stage the JavaFX stage to add elements to
   */
  @Override
  public void start(Stage stage) {
    // instantiate a simple JavaJournal cs3500.view
    JavaJournalController journalController = new JavaJournalControllerImpl();
    JavaJournalView javaJournalView = new JavaJournalViewImpl(journalController);
    try {
      // load and place the cs3500.view's scene onto the stage
      stage.setScene(javaJournalView.load());
      stage.setTitle("Java-Journal");
      stage.show();
      journalController.run();
    } catch (IllegalStateException exc) {
      System.err.println("Unable to load GUI.");
    }
  }

  public static void main(String[] args) {
    launch();
  }

}
