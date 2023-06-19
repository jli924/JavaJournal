package cs3500.pa05.controller;

import java.io.File;
import javafx.stage.Stage;

public interface JavaJournalController {

  void run();

  public void saveToFile(File file);

  public void openFile(File file);
}
