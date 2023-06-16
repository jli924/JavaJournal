package cs3500.pa05.view;

import cs3500.pa05.controller.JavaJournalController;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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

  public void eventScene() {
    GridPane pane = new GridPane();
    Scene eventScene = new Scene(pane, 400, 400);
    Image image = new Image("https://www.iconsdb.com/icons/preview/pink/calendar-3-xxl.png");
    ImageView imageView = new ImageView(image);
    imageView.setFitHeight(64);
    imageView.setFitWidth(64);
    TextField name = new TextField("event name");
    TextField description = new TextField("description");
    TextField weekday = new TextField("weekday");
    TextField startTime = new TextField("start time");
    TextField duration = new TextField("duration");
    Stage eventStage = new Stage();

    Label[] labels = {new Label("name:"), new Label("description:"),
        new Label("weekday:"),
        new Label("startTime:"), new Label("duration:")};

    pane.getChildren().add(name);
    pane.getChildren().add(description);
    pane.getChildren().add(weekday);
    pane.getChildren().add(startTime);
    pane.getChildren().add(duration);
    for (int row = 1; row < 5; row++) {
      for (int col = 1; col < 3; col++) {
        pane.add(labels[row - 1], col, row);
      }
    }
    eventStage.setScene(eventScene);
    pane.setPadding(new Insets(50));
    pane.setHgap(50);
    pane.setVgap(50);
    eventStage.setResizable(false);
    eventStage.show();
  }
}