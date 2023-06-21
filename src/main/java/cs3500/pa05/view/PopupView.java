package cs3500.pa05.view;

import cs3500.pa05.controller.JavaJournalControllerImpl;
import cs3500.pa05.controller.SaveProcessor;
import cs3500.pa05.model.JEvent;
import cs3500.pa05.model.JavaJournal;
import cs3500.pa05.model.JournalEntry;
import cs3500.pa05.model.Task;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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
   * The stage for the maxEntries popup
   *
   * @param fields the text-fields to show
   * @param save the button to show
   * @return the stage
   */
  public Stage maxEntriesScene(TextField[] fields, Button save) {
    GridPane pane = new GridPane();
    Scene maxEntriesScene = new Scene(pane, 400, 450);
    Stage maxEntriesStage = new Stage();
    ImageView icon = addIcon(
        "https://www.iconsdb.com/icons/preview/pink/notepad-xxl.png", 64, 64);
    Label[] labels = {new Label("Max Events: "), new Label("Max Tasks: ")};
    Label newMaxes = new Label("New Maxes");
    newMaxes.setStyle("-fx-font-size: 21;");

    // adding controls to a gridpane
    for (int row = 1; row < 3; row++) {
      pane.add(labels[row - 1], 0, row);
      pane.add(fields[row - 1], 1, row);
    }
    pane.add(icon, 1, 0);
    pane.add(newMaxes, 0, 0);
    pane.add(save, 1, 4);

    // setting alignment for style
    GridPane.setHalignment(save, HPos.RIGHT);
    GridPane.setHalignment(icon, HPos.RIGHT);

    // borders and spacing
    pane.setPadding(new Insets(50));
    pane.setHgap(50);
    pane.setVgap(50);
    maxEntriesStage.setResizable(false);

    // setting the scene
    maxEntriesStage.setScene(maxEntriesScene);
    maxEntriesStage.setTitle("New Max Entries");
    return maxEntriesStage;
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

  public void editScene(TextField[] fields) {
    for (TextField f: fields) {
      f.setEditable(true);
    }
  }

  public Stage splashScreen() {
    HBox hBox = new HBox();
    Scene splash = new Scene(hBox, 1074, 714);
    Stage splashScreen = new Stage();
    ImageView icon = addIcon("appIcon.png", 100, 100);
    hBox.getChildren().add(icon);
    hBox.setAlignment(Pos.CENTER);
    splashScreen.setScene(splash);
    splashScreen.setTitle("Java Journal");
    return splashScreen;
  }

  public void eventMiniView(Label label, JournalEntry entry,
                            JavaJournalControllerImpl controller, GridPane mainGrid) {
    JEvent e = (JEvent) entry;
    GridPane pane = new GridPane();
    Scene s = new Scene(pane, 400, 600);
    Label title = new Label("Mini Viewer");
    title.setStyle("-fx-font-size: 16");
    pane.add(title, 0, 0);
    TextField name = new TextField(e.getName());
    name.setEditable(false);
    TextField description = new TextField(e.getDescription());
    description.setEditable(false);
    TextField weekday = new TextField(e.getWeekday().toString());
    weekday.setEditable(false);
    TextField startTime = new TextField(e.getStartTime());
    startTime.setEditable(false);
    TextField duration = new TextField(e.getDuration());
    duration.setEditable(false);
    pane.add(name, 1, 1);
    pane.add(description, 1, 2);
    pane.add(weekday, 1, 3);
    pane.add(startTime, 1, 4);
    pane.add(duration, 1, 5);
    pane.add(new Label("Event Name: "), 0, 1);
    pane.add(new Label("Description: "), 0, 2);
    pane.add(new Label("Weekday: "), 0, 3);
    pane.add(new Label("Start Time: "), 0, 4);
    pane.add(new Label("Duration: "), 0, 5);
    Button edit = addPrettyButton("Edit", 80, 30, "pink");
    edit.setOnAction(event -> editScene(
        new TextField[] {name, description, weekday, startTime, duration}));
    Button save = addPrettyButton("Save", 80, 30, "pink");
    pane.add(edit, 0, 6);
    pane.add(save, 1, 6);
    pane.setPadding(new Insets(50));
    pane.setHgap(50);
    pane.setVgap(50);
    Stage stage = new Stage();
    save.setOnAction(new SaveProcessor(e,
        new TextField[] {name, description, weekday,
            startTime, duration}, this, stage, label, controller, mainGrid));
    stage.setScene(s);
    stage.setTitle("Mini Viewer");
    stage.show();
  }

  // need to find better placement for buttons
  public void taskMiniView(Label label, Task t, Stage stage, Button completeTask,
                           JavaJournalControllerImpl controller, GridPane mainGrid) {
    GridPane pane = new GridPane();
    Scene s = new Scene(pane, 400, 600);
    ImageView imageView = addIcon
        ("https://www.iconsdb.com/icons/preview/pink/clipboard-2-xxl.png",
            48, 48);
    Label title = new Label("Mini Viewer");
    title.setStyle("-fx-font-size: 16");
    title.setWrapText(true);
    boolean complete = t.isComplete();
    String result = complete ? "Yes" : "No";
    pane.add(title, 0, 0);
    TextField name = new TextField(t.getName());
    name.setEditable(false);
    TextField description = new TextField(t.getDescription());
    description.setEditable(false);
    TextField weekday = new TextField(t.getWeekday().toString());
    weekday.setEditable(false);
    pane.add(new Label("Task Name: "), 0, 1);
    pane.add(name, 1, 1);
    pane.add(new Label("Description: " + t.getDescription()), 0, 2);
    pane.add(description, 1, 2);
    pane.add(new Label("Weekday: "), 0, 3);
    pane.add(weekday, 1, 3);
    pane.add(new Label("Complete?: "), 0, 4);
    pane.add(new Label(result), 1, 4);
    pane.add(imageView, 1, 0);
    GridPane.setHalignment(imageView, HPos.RIGHT);
    pane.add(completeTask, 1, 5);
    GridPane.setHalignment(completeTask, HPos.RIGHT);
    pane.setPadding(new Insets(50));
    pane.setHgap(50);
    pane.setVgap(50);
    Button edit = addPrettyButton("Edit", 80, 30, "pink");
    edit.setOnAction(event -> editScene(
        new TextField[] {name, description, weekday}));
    Button save = addPrettyButton("Save", 80, 30, "pink");
    pane.add(edit, 0, 6);
    pane.add(save, 1, 6);
    save.setOnAction(new SaveProcessor(t,
        new TextField[] {name, description, weekday,},
        this, stage, label, controller, mainGrid));
    stage.setScene(s);
    stage.setTitle("Mini Viewer");
    stage.show();
  }

  /**
   * Represents a saveToFile or OpenFile screen where user must input filename field
   *
   * new Scene to Save to File
   * @param header The main description
   * @param description the wanted text input
   * @param stageTitle the title of the stage
   * @param field the test field for user input
   * @param save the save button
   * @param iconUrl the iconURL for the image
   * @param fontSize the desired fontsize
   * @return the Stage to be shown to the user
   */
  public Stage newSaveOrOpenScene(String header, String description, String stageTitle,
                                  TextField field, Button save, String iconUrl, int fontSize) {
    GridPane pane = new GridPane();
    Scene popupScene = new Scene(pane, 400, 300);
    ImageView icon = addIcon(iconUrl, 64, 64);
    Stage SaveOrOpenScene = new Stage();
    Label miniLabel = new Label(description);
    Label mainLabel = new Label(header);
    mainLabel.setStyle("-fx-font-size: " + fontSize + ";");

    pane.add(mainLabel, 0, 0);
    pane.add(miniLabel, 0, 1);
    pane.add(icon, 1, 0);
    pane.add(field, 1, 1);
    pane.add(save, 1, 2);

    GridPane.setHalignment(save, HPos.RIGHT);
    GridPane.setHalignment(icon, HPos.RIGHT);

    pane.setPadding(new Insets(50));
    pane.setHgap(50);
    pane.setVgap(50);
    SaveOrOpenScene.setResizable(false);

    SaveOrOpenScene.setScene(popupScene);
    SaveOrOpenScene.setTitle(stageTitle);
    return SaveOrOpenScene;
  }

}