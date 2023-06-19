package cs3500.pa05.controller;

import java.io.File;
import javafx.scene.Scene;
import javafx.stage.Stage;

public interface JavaJournalController {

  void run();

  public void saveToFile(File file);

  public void openFile(File file);

  Scene showSplashScreen();

  void closeSplashScreen(Stage stage, Stage journal, Scene scene);

}
