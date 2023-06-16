package cs3500.pa05.controller;

import java.io.File;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public interface JavaJournalController {

  void run();

  public void saveToFile(File file);

  public void openFile(File file);
}
