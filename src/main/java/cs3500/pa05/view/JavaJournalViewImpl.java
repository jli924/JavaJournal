package cs3500.pa05.view;

import cs3500.pa05.controller.JavaJournalController;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class JavaJournalViewImpl implements JavaJournalView {

  FXMLLoader loader;

  public JavaJournalViewImpl(JavaJournalController controller) {
    // initialization and location setting omitted for brevity
    this.loader = new FXMLLoader();
    this.loader.setLocation(getClass().getClassLoader().getResource("journal.fxml"));
    this.loader.setController(controller);
  }

  /**
   * Loads a scene from a Whack-a-Mole GUI layout.
   *
   * @return the layout
   */
  @Override
  public Scene load() {
    // load the layout
    try {
      return this.loader.load();
    } catch (IOException exc) {
      throw new IllegalStateException("Unable to load layout.");
    }
  }


  public void setScene(String scene) {
      this.loader.setLocation(getClass().getClassLoader().getResource(scene));
  }

  public void setController(JavaJournalController controller) {
    this.loader.setController(controller);
  }
}