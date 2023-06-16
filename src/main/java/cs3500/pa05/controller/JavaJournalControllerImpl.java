package cs3500.pa05.controller;

import cs3500.pa05.model.Event;
import cs3500.pa05.model.JavaJournal;
import cs3500.pa05.model.Task;
import cs3500.pa05.model.Weekday;
import java.io.File;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
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
  TableColumn<TableView<String>, TableCell> sunday;

  public JavaJournalControllerImpl() {
    journal = new JavaJournal();
  }

  public void run() {
    initButtons();
  }

  @Override
  public void saveToFile(File file) {

  }

  @Override
  public void openFile(File file) {

  }

  public void initButtons() {
    addEvent.setOnAction(event -> {
      eventScene();
    });
    addTask.setOnAction(event -> {
      taskScene();
    });
  }

  private ImageView addIcon(String url, int width, int height) {
    Image image = new Image(url);
    ImageView imageView = new ImageView(image);
    imageView.setFitWidth(width);
    imageView.setFitHeight(height);
    return imageView;
  }

  private Button addPrettyButton(String text, int width, int height, String color) {
    Button button = new Button(text);
    button.setPrefWidth(width);
    button.setPrefHeight(height);
    button.setStyle("-fx-background-color: " + color);
    return button;
  }

  private void eventScene() {
    GridPane pane = new GridPane();
    Scene eventScene = new Scene(pane, 400, 600);
    ImageView icon = addIcon(
        "https://www.iconsdb.com/icons/preview/pink/calendar-3-xxl.png", 64, 64);
    Stage eventStage = new Stage();
    TextField name = new TextField();
    TextField description = new TextField();
    TextField weekday = new TextField();
    TextField startTime = new TextField();
    TextField duration = new TextField();

    TextField[] fields = {name, description,
        weekday, startTime, duration};
    Label[] labels = {new Label("Event Name: "), new Label("Description: "),
        new Label("Weekday: "),
        new Label("Start Time: "), new Label("Duration: ")};
    Label newEvent = new Label("New Event");
    newEvent.setStyle("-fx-font-size: 20;");
    Button save = addPrettyButton("Save", 50, 40, "pink");

    for (int row = 1; row < 6; row++) {
      pane.add(labels[row - 1], 0, row);
      pane.add(fields[row - 1], 1, row);
    }
    pane.add(icon, 1, 0);
    pane.add(save, 1, 6);
    pane.add(newEvent, 0, 0);

    GridPane.setHalignment(save, HPos.RIGHT);
    GridPane.setHalignment(icon, HPos.RIGHT);

    pane.setPadding(new Insets(50));
    pane.setHgap(50);
    pane.setVgap(50);
    eventStage.setResizable(false);

    eventStage.setScene(eventScene);
    eventStage.setTitle("New Event");

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
          invalidInputAlert("Please ensure you entered a valid name, day, start time, "
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
          invalidInputAlert("Please ensure you entered a valid name, day, start time, "
              + "and duration. Descriptions are optional");
        }
      }
    });
    eventStage.show();
  }

  private void taskScene() {
    GridPane pane = new GridPane();
    Scene eventScene = new Scene(pane, 400, 450);
    Stage eventStage = new Stage();
    // an icon for the popup!
    ImageView icon = addIcon(
        "https://www.iconsdb.com/icons/preview/pink/notepad-xxl.png", 64, 64);
    TextField name = new TextField();
    TextField description = new TextField();
    TextField weekday = new TextField();
    TextField[] fields = {name, description, weekday};
    Label[] labels = {new Label("Task Name: "), new Label("Description: "),
        new Label("Weekday: ")};
    // adding a label and giving it a style
    Label newTask = new Label("New Task");
    newTask.setStyle("-fx-font-size: 21;");
    // adding a button and giving it a style
    Button save = addPrettyButton("Save", 50, 40, "pink");

    // adding controls to a gridpane
    for (int row = 1; row < 4; row++) {
      pane.add(labels[row - 1], 0, row);
      pane.add(fields[row - 1], 1, row);
    }
    pane.add(icon, 1, 0);
    pane.add(newTask, 0, 0);
    pane.add(save, 1, 4);

    // setting alignment for style
    GridPane.setHalignment(save, HPos.RIGHT);
    GridPane.setHalignment(icon, HPos.RIGHT);

    // borders and spacing
    pane.setPadding(new Insets(50));
    pane.setHgap(50);
    pane.setVgap(50);
    eventStage.setResizable(false);

    // setting the scene
    eventStage.setScene(eventScene);
    eventStage.setTitle("New Task");

    // add functionality while the dialog runs
    save.setOnAction(event -> {
      if (description.getText().isEmpty()) {
        try {
          Task userTask = new Task(name.getText(),
              Weekday.valueOf(weekday.getText().toUpperCase()), false);
          journal.addTask(userTask);
          eventStage.close();
        } catch (Exception ignored){
          invalidInputAlert("Please ensure you entered a valid name, day, start time, "
              + "and duration. Descriptions are optional");
        }
      } else {
        try {
          Task userTask = new Task(name.getText(), description.getText(),
              Weekday.valueOf(weekday.getText().toUpperCase()), false);
          journal.addTask(userTask);
          eventStage.close();
        } catch (Exception ignored){
          invalidInputAlert("Please ensure you entered a valid name and day. "
              + "Descriptions are optional");
        }
      }
    });

    // showing the scene
    eventStage.show();
  }

  private void invalidInputAlert(String message) {
    Alert invalidInput = new Alert(Alert.AlertType.ERROR);
    invalidInput.setResizable(false);
    invalidInput.setHeaderText("Invalid event");
    ImageView errorIcon = addIcon(
        "https://image.pngaaa.com/987/3732987-middle.png",
        45, 28);
    invalidInput.setGraphic(errorIcon);
    invalidInput.setContentText(message);
    invalidInput.show();
  }
}

