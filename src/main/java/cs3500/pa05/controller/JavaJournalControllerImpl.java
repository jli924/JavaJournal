package cs3500.pa05.controller;

import cs3500.pa05.model.Event;
import cs3500.pa05.model.JavaJournal;
import cs3500.pa05.model.Task;
import cs3500.pa05.model.Weekday;
import cs3500.pa05.view.PopupView;
import java.io.File;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * The implementation of the JavaJournalController interface
 */
public class JavaJournalControllerImpl implements JavaJournalController {

  @FXML
  Button addEvent;

  @FXML
  Button addTask;

  JavaJournal journal;

  @FXML
  Circle profilePicture;
  PopupView popupView = new PopupView();

  /**
   * Constructor
   */
  public JavaJournalControllerImpl() {
    journal = new JavaJournal();
  }

  /**
   * To run the application
   */
  public void run() {
    initButtons();
    profilePicture.setOnMouseClicked(event -> {
      selectProfilePicture();
    });
  }

  @Override
  public void saveToFile(File file) {

  }

  @Override
  public void openFile(File file) {

  }

  /**
   * Set functionality for add task and add event button
   */
  public void initButtons() {
    addEvent.setOnAction(event -> {
      eventHandler();
    });
    addTask.setOnAction(event -> {
      taskHandler();
    });
  }

  /**
   * Handles the event popup
   */
  private void eventHandler() {
    TextField name = new TextField();
    TextField description = new TextField();
    TextField weekday = new TextField();
    TextField startTime = new TextField();
    TextField duration = new TextField();
    TextField[] fields = {name, description,
        weekday, startTime, duration};
    Button save = popupView.addPrettyButton("Save", 50, 40, "pink");
    Stage eventStage = popupView.eventScene(fields, save);

    // add functionality while the dialog runs
    save.setOnAction(event -> {
      if (description.getText().isEmpty()) {
        try {
          Event userEvent = new Event(name.getText(),
              Weekday.valueOf(weekday.getText().toUpperCase()),
              startTime.getText(), duration.getText());
          journal.addEvent(userEvent);
          eventStage.close();
        } catch (Exception ignored){
          popupView.invalidInputAlert("Invalid event",
              "Please ensure you entered a valid name, day, start time, "
              + "and duration. Descriptions are optional");
        }
      } else {
        try {
          Event userEvent = new Event(name.getText(), description.getText(),
              Weekday.valueOf(weekday.getText().toUpperCase()),
              startTime.getText(), duration.getText());
          journal.addEvent(userEvent);
          eventStage.close();
        } catch (Exception ignored){
          popupView.invalidInputAlert("Invalid event",
              "Please ensure you entered a valid name, day, start time, "
              + "and duration. Descriptions are optional");
        }
      }
    });
    eventStage.show();
  }

  /**
   * Handles the task popup
   */
  private void taskHandler() {
    TextField name = new TextField();
    TextField description = new TextField();
    TextField weekday = new TextField();
    TextField[] fields = {name, description, weekday};
    Button save = popupView.addPrettyButton("Save", 50, 40, "pink");
    Stage taskStage = popupView.taskScene(fields, save);

    // add functionality while the dialog runs
    save.setOnAction(event -> {
      if (description.getText().isEmpty()) {
        try {
          Task userTask = new Task(name.getText(),
              Weekday.valueOf(weekday.getText().toUpperCase()), false);
          journal.addTask(userTask);
          taskStage.close();
        } catch (Exception ignored){
          popupView.invalidInputAlert("Invalid task",
              "Please ensure you entered a valid name, day, start time, "
              + "and duration. Descriptions are optional");
        }
      } else {
        try {
          Task userTask = new Task(name.getText(), description.getText(),
              Weekday.valueOf(weekday.getText().toUpperCase()), false);
          journal.addTask(userTask);
          taskStage.close();
        } catch (Exception ignored){
          popupView.invalidInputAlert("Invalid task",
              "Please ensure you entered a valid name and day. "
              + "Descriptions are optional");
        }
      }
    });

    // showing the scene
    taskStage.show();
  }

  @FXML
  private void selectProfilePicture() {
    Stage stage = new Stage();
    FileChooser chooser = new FileChooser();
    try {
      File tmp = chooser.showOpenDialog(stage);
      Image img = new Image(tmp.getAbsolutePath());
      ImageView image1 = new ImageView();
      image1.setPreserveRatio(true);
      image1.setImage(img);
      profilePicture.setFill(pattern(img, profilePicture.getRadius()));
    } catch (Exception ignored){}
  }

  private ImagePattern pattern(Image img, double radius) {
    double hRad = radius;   // horizontal "radius"
    double vRad = radius;   // vertical "radius"
    if (img.getWidth() != img.getHeight()) {
      double ratio = img.getWidth() / img.getHeight();
      if (ratio > 1) {
        // Width is longer, left anchor is outside
        hRad = radius * ratio;
      } else {
        // Height is longer, top anchor is outside
        vRad = radius / ratio;
      }
    }
    return new ImagePattern(img, -hRad, -vRad, 2 * hRad, 2 * vRad, false);
  }
}

