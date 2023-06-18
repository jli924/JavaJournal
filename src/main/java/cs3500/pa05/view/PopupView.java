package cs3500.pa05.view;

import cs3500.pa05.model.Day;
import cs3500.pa05.model.Event;
import cs3500.pa05.model.JournalEntry;
import cs3500.pa05.model.Task;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * The GUIs for popups
 */
public class PopupView {
  /**
   * Add an icon with an image of your choice!
   *
   * @param url the image url
   * @param width icon width
   * @param height icon height
   * @return the icon
   */
  public ImageView addIcon(String url, int width, int height) {
    Image image = new Image(url);
    ImageView imageView = new ImageView(image);
    imageView.setFitWidth(width);
    imageView.setFitHeight(height);
    return imageView;
  }

  /**
   * Add a pretty button with a color of your choice!
   *
   * @param text text on the button
   * @param width button width
   * @param height button height
   * @param color the color you choose
   * @return the button
   */
  public Button addPrettyButton(String text, int width, int height, String color) {
    Button button = new Button(text);
    button.setPrefWidth(width);
    button.setPrefHeight(height);
    button.setStyle("-fx-background-color: " + color);
    return button;
  }

  /**
   * Show an alert
   *
   * @param header the header for the alert
   * @param message the message to show on the alert
   */
  public void invalidInputAlert(String header, String message) {
    Alert invalidInput = new Alert(Alert.AlertType.ERROR);
    invalidInput.setResizable(false);
    invalidInput.setHeaderText(header);
    ImageView errorIcon = addIcon(
        "https://image.pngaaa.com/987/3732987-middle.png",
        45, 28);
    invalidInput.setGraphic(errorIcon);
    invalidInput.setContentText(message);
    invalidInput.show();
  }

  /**
   * The stage for the event popup
   *
   * @param fields the text-fields to show
   * @param save the button to show
   * @return the stage
   */
  public Stage eventScene(TextField[] fields, Button save) {
    GridPane pane = new GridPane();
    Scene eventScene = new Scene(pane, 400, 600);
    ImageView icon = addIcon(
        "https://www.iconsdb.com/icons/preview/pink/calendar-3-xxl.png", 64, 64);
    Stage eventStage = new Stage();
    Label[] labels = {new Label("Event Name: "), new Label("Description: "),
        new Label("Weekday: "),
        new Label("Start Time: "), new Label("Duration: ")};
    Label newEvent = new Label("New Event");
    newEvent.setStyle("-fx-font-size: 20;");

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
    return eventStage;
  }

  /**
   * The stage for the task popup
   *
   * @param fields the text-fields to show
   * @param save the button to show
   * @return the stage
   */
  public Stage taskScene(TextField[] fields, Button save) {
    GridPane pane = new GridPane();
    Scene eventScene = new Scene(pane, 400, 450);
    Stage taskStage = new Stage();
    // an icon for the popup!
    ImageView icon = addIcon(
        "https://www.iconsdb.com/icons/preview/pink/notepad-xxl.png", 64, 64);
    Label[] labels = {new Label("Task Name: "), new Label("Description: "),
        new Label("Weekday: ")};
    // adding a label and giving it a style
    Label newTask = new Label("New Task");
    newTask.setStyle("-fx-font-size: 21;");
    // adding a button and giving it a style
//    save = addPrettyButton("Save", 50, 40, "pink");

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
    taskStage.setResizable(false);

    // setting the scene
    taskStage.setScene(eventScene);
    taskStage.setTitle("New Task");
    return taskStage;
  }

  /**
   * The stage for the new week popup
   *
   * @param field the text-field to show
   * @param save the button to show
   * @return the stage
   */
  public Stage newTimeScene(String header, String description, String stageTitle,
                            TextField field, Button save, String iconUrl, int fontSize) {
    GridPane pane = new GridPane();
    Scene weekScene = new Scene(pane, 400, 300);
    ImageView icon = addIcon(iconUrl, 64, 64);
    Stage weekStage = new Stage();
    Label weekOf = new Label(description);
    Label newWeek = new Label(header);
    newWeek.setStyle("-fx-font-size: " + fontSize + ";");

    pane.add(newWeek, 0, 0);
    pane.add(weekOf, 0, 1);
    pane.add(icon, 1, 0);
    pane.add(field, 1, 1);
    pane.add(save, 1, 2);

    GridPane.setHalignment(save, HPos.RIGHT);
    GridPane.setHalignment(icon, HPos.RIGHT);

    pane.setPadding(new Insets(50));
    pane.setHgap(50);
    pane.setVgap(50);
    weekStage.setResizable(false);

    weekStage.setScene(weekScene);
    weekStage.setTitle(stageTitle);
    return weekStage;
  }


}