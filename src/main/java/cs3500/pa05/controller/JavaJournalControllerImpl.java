package cs3500.pa05.controller;

import cs3500.pa05.model.Day;
import cs3500.pa05.model.JEvent;
import cs3500.pa05.model.JavaJournal;
import cs3500.pa05.model.JournalEntry;
import cs3500.pa05.model.Task;
import cs3500.pa05.model.Weekday;
import cs3500.pa05.view.PopupView;
import java.io.File;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * The implementation of the JavaJournalController interface
 */
public class JavaJournalControllerImpl implements JavaJournalController {

  @FXML
  Button saveToFile;

  @FXML
  Button openFile;

  @FXML
  Button addEvent;

  @FXML
  Button addMenuEvent;

  @FXML
  Button addTask;
  @FXML
  Button addMenuTask;
  @FXML
  ListView taskQueue;
  List<Task> alreadyAdded = new ArrayList<>();

  @FXML
  Button newWeek;
  @FXML
  TextField weekLabel;

  @FXML
  Button newMonth;
  @FXML
  Label month;

  @FXML
  Button newYear;
  @FXML
  Label year;

  @FXML
  Button password;

  @FXML
  TextArea weeklyOverview;

  JavaJournal journal;

  @FXML
  GridPane mainGrid;

  @FXML
  Circle profilePicture;

  PopupView popupView = new PopupView();

  /**
   * Constructor
   */
  public JavaJournalControllerImpl() {
    journal = new JavaJournal();
  }

  public JavaJournalControllerImpl(JavaJournal journal) {
    this.journal = journal;
  }

  public Scene showSplashScreen() {
    Stage splash = popupView.splashScreen();
    return splash.getScene();
  }

  public void closeSplashScreen(Stage splash, Stage journal, Scene scene) {
    initJournal(journal, scene);
    PauseTransition pauseTransition = new PauseTransition(Duration.seconds(1));
    pauseTransition.setOnFinished(event -> splash.close());
    pauseTransition.play();
    PauseTransition pauseTransition1 = new PauseTransition(Duration.seconds(1));
    pauseTransition1.setOnFinished(event -> journal.show());
    pauseTransition1.play();
  }

  public void initJournal(Stage journal, Scene scene) {
    try {
      // load and place the cs3500.view's scene onto the stage
      journal.getIcons().add(new Image(
          Objects.requireNonNull(getClass()
              .getClassLoader().getResource("appIcon.png")).openStream()));
      journal.setTitle("Java Journal");
      journal.setScene(scene);
      run();
    } catch (IllegalStateException | IOException exc) {
      System.err.println("Unable to load GUI.");
    }
  }

  public static int findFirstEmptyRow(GridPane gridPane, int columnIndex) {
    int numRows = gridPane.getRowCount();
    for (int row = 0; row < numRows; row++) {
      Node node = getNodeFromGridPane(gridPane, columnIndex, row);
      if (node == null || (node instanceof Label && ((Label) node).getText().isEmpty())) {
        return row;
      }
    }
    return -1; // no empty row found
  }

  private static Node getNodeFromGridPane(GridPane gridPane, int colIndex, int rowIndex) {
    for (Node node : gridPane.getChildren()) {
      Integer colIdx = GridPane.getColumnIndex(node);
      Integer rowIdx = GridPane.getRowIndex(node);
      colIdx = (colIdx == null) ? 0 : colIdx;
      rowIdx = (rowIdx == null) ? 0 : rowIdx;
      if (colIdx == colIndex && rowIdx == rowIndex) {
        return node;
      }
    }
    return null;
  }

  /**
   * To run the application
   */
  public void run() {
    showSplashScreen();
    initCommands();
    initButtons();
    profilePicture.setFill(new ImagePattern
        (new Image("https://i.pinimg.com/474x/ed/54/3b/ed543b461c96fb73519edf7ac8718f39.jpg")));
    profilePicture.setOnMouseClicked(event -> {
      selectProfilePicture();
    });
  }


  /**
   * Set functionality for add task and add event button
   */
  public void initButtons() {
    addEvent.setOnAction(event -> eventHandler());
    addTask.setOnAction(event -> taskHandler());
    addMenuEvent.setOnAction(event -> eventHandler());
    addMenuTask.setOnAction(event -> taskHandler());
    newWeek.setOnAction(event -> newWeekHandler());
    newMonth.setOnAction(event -> newMonthHandler());
    newYear.setOnAction(event -> newYearHandler());
    password.setOnAction(event -> passwordHandler());
    initTasksandEvents();
    setDays();
    update();
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
          JEvent userJEvent = new JEvent(name.getText(),
              Weekday.valueOf(weekday.getText().toUpperCase()),
              startTime.getText(), duration.getText());
          journal.addEvent(userJEvent);
          Label newEvent = new Label(userJEvent.getName());
          newEvent.setPadding(new Insets(5));
          int row = findFirstEmptyRow(mainGrid, userJEvent.getWeekday().ordinal());
          int col = userJEvent.getWeekday().ordinal();
          mainGrid.add(newEvent, col, row);
          newEvent.setOnMouseClicked(event1 -> {
            miniViewer(newEvent, userJEvent);
          });
          eventStage.close();
          update();
        } catch (Exception ignored) {
          popupView.invalidInputAlert("Invalid event",
              "Please ensure you entered a valid name, day, start time, "
                  + "and duration. Descriptions are optional");
        }
      } else {
        try {
          JEvent userJEvent = new JEvent(name.getText(), description.getText(),
              Weekday.valueOf(weekday.getText().toUpperCase()),
              startTime.getText(), duration.getText());
          journal.addEvent(userJEvent);
          Label newEvent = new Label(userJEvent.getName());
          newEvent.setPadding(new Insets(5));
          int row = findFirstEmptyRow(mainGrid, userJEvent.getWeekday().ordinal());
          int col = userJEvent.getWeekday().ordinal();
          mainGrid.add(newEvent, col, row);
          newEvent.setOnMouseClicked(event1 -> {
            miniViewer(newEvent, userJEvent);
          });
          eventStage.close();
          update();
        } catch (Exception ignored) {
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
          Label newTask = new Label(userTask.getName());
          newTask.setPadding(new Insets(5));
          int row = findFirstEmptyRow(mainGrid, userTask.getWeekday().ordinal());
          int col = userTask.getWeekday().ordinal();
          mainGrid.add(newTask, col, row);
          GridPane.setColumnIndex(newTask, col);
          newTask.setOnMouseClicked(event1 -> {
            miniViewer(newTask, userTask);
          });
          taskStage.close();
          update();
        } catch (Exception ignored) {
          popupView.invalidInputAlert("Invalid task",
              "Please ensure you entered a valid name, day, start time, "
                  + "and duration. Descriptions are optional");
        }
      } else {
        try {
          Task userTask = new Task(name.getText(), description.getText(),
              Weekday.valueOf(weekday.getText().toUpperCase()), false);
          journal.addTask(userTask);
          Label newTask = new Label(userTask.getName());
          newTask.setPadding(new Insets(5));
          int row = findFirstEmptyRow(mainGrid, userTask.getWeekday().ordinal());
          int col = userTask.getWeekday().ordinal();
          mainGrid.add(newTask, col, row);
          newTask.setOnMouseClicked(event1 -> {
            miniViewer(newTask, userTask);
          });
          taskStage.close();
          update();
        } catch (Exception ignored) {
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
    } catch (Exception ignored) {
    }
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

  public void initTasksandEvents() {
    int colIdx = 0;
    int rowIdx = 1;
    for (Day day : journal.getDays()) {
      for (Task t : day.getTasks()) {
        Label initEntry = new Label(t.getName());
        initEntry.setOnMouseClicked(event -> {
          miniViewer(initEntry, t);
        });
        mainGrid.add(initEntry, colIdx, rowIdx);
        rowIdx += 1;
      }
      for (JEvent e : day.getEvents()) {
        Label initEntry = new Label(e.getName());
        initEntry.setOnMouseClicked(event -> {
          miniViewer(initEntry, e);
        });
        mainGrid.add(initEntry, colIdx, rowIdx);
        rowIdx += 1;
      }
      colIdx += 1;
    }
  }

  private void setDays() {
    LocalDate date = LocalDate.now();
    DayOfWeek day = date.getDayOfWeek();
    int weekday = day.getValue();
    for (int i = 0; i < 7; i++) {
      Label dayLabel = new Label(Weekday.values()[i].toString());
      dayLabel.setAlignment(Pos.CENTER);
      dayLabel.setPrefHeight(32);
      dayLabel.setPrefWidth(101);
      if (weekday == 7) {
        if (i == 0) {
          dayLabel.setStyle("-fx-background-color: pink");
        }
      } else if (i == weekday) {
        dayLabel.setStyle("-fx-background-color: pink");
      }
      mainGrid.add(dayLabel, i, 0);
    }
  }

  @Override
  public void saveToFile(File file) {

  }

  @Override
  public void openFile(File file) {

  }

  public void initCommands() {
    addMenuEvent.getScene().getAccelerators().put(
        new KeyCodeCombination(KeyCode.E, KeyCombination.CONTROL_ANY), () -> addMenuEvent.fire());
    addMenuTask.getScene().getAccelerators().put(
        new KeyCodeCombination(KeyCode.T, KeyCombination.CONTROL_ANY), () -> addMenuTask.fire());
    newWeek.getScene().getAccelerators().put(
        new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_ANY), () -> newWeek.fire());
    newMonth.getScene().getAccelerators().put(
        new KeyCodeCombination(KeyCode.M, KeyCombination.CONTROL_ANY), () -> newMonth.fire());
    newYear.getScene().getAccelerators().put(
        new KeyCodeCombination(KeyCode.Y, KeyCombination.CONTROL_ANY), () -> newYear.fire());
    password.getScene().getAccelerators().put(
        new KeyCodeCombination(KeyCode.P, KeyCombination.CONTROL_ANY), () -> password.fire());
  }

  /**
   * Handles the new week popup
   */
  private void newWeekHandler() {
    TextField field = new TextField();
    Button save = popupView.addPrettyButton("Save", 50, 30, "pink");
    Stage weekStage = popupView.newTimeScene("New Week", "Week of: ",
        "New Week", field, save,
        "https://www.iconsdb.com/icons/preview/pink/calendar-3-xxl.png", 19);
    save.setOnAction(event -> {
      weekLabel.setText(field.getText());
      weekStage.close();
    });
    weekStage.show();
  }

  /**
   * Handles the new month popup
   */
  private void newMonthHandler() {
    TextField field = new TextField();
    Button save = popupView.addPrettyButton("Save", 50, 30, "pink");
    Stage monthStage = popupView.newTimeScene("New Month", "Month: ",
        "New Month", field, save,
        "https://www.iconsdb.com/icons/preview/pink/calendar-3-xxl.png", 18);
    save.setOnAction(event -> {
      month.setText(field.getText());
      monthStage.close();
    });
    monthStage.show();
  }

  /**
   * Handles the new year popup
   */
  private void newYearHandler() {
    TextField field = new TextField();
    Button save = popupView.addPrettyButton("Save", 50, 30, "pink");
    Stage yearStage = popupView.newTimeScene("New Year", "Year: ",
        "New Year", field, save,
        "https://www.iconsdb.com/icons/preview/pink/calendar-3-xxl.png", 19);
    save.setOnAction(event -> {
      year.setText(field.getText());
      yearStage.close();
    });
    yearStage.show();
  }

  /**
   * Handles the set password popup
   */
  private void passwordHandler() {
    TextField field = new TextField();
    Button save = popupView.addPrettyButton("Save", 50, 30, "pink");
    Stage passwordStage = popupView.newTimeScene("Set Password", "Password: ",
        "Set Password", field, save,
        "https://www.iconsdb.com/icons/preview/pink/calendar-3-xxl.png", 15);
    save.setOnAction(event -> {
      journal.setPassword(field.getText());
      passwordStage.close();
    });
    passwordStage.show();
  }


  private void update() {
    weeklyOverview.setEditable(false);
    weeklyOverview.setText("Total tasks: " + journal.getTasks().size()
        + System.lineSeparator()
        + System.lineSeparator()
        + "Total events: " + journal.totalEvents().size()
        + System.lineSeparator()
        + System.lineSeparator()
        + "Tasks completed: "
        + journal.percentComplete() + "%");
    for (Task task : journal.getTasks()) {
      if (!alreadyAdded.contains(task)) {
        alreadyAdded.add(task);
        taskQueue.getItems().add(task.getName());
      }
    }
  }

  public void miniViewer(Label label, JournalEntry entry) {
    try {
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
      Button edit = popupView.addPrettyButton("Edit", 80, 30, "pink");
      edit.setOnAction(event -> {
        popupView.editScene(new TextField[] {name, description, weekday, startTime, duration});
      });
      Button save = popupView.addPrettyButton("Save", 80, 30, "pink");
      pane.add(edit, 0, 6);
      pane.add(save, 1, 6);
      pane.setPadding(new Insets(50));
      pane.setHgap(50);
      pane.setVgap(50);
      Stage stage = new Stage();
      save.setOnAction(new saveProcessor(e,
          new TextField[] {name, description, weekday,
              startTime, duration}, popupView, stage, label, this, mainGrid));
      stage.setScene(s);
      stage.setTitle("Mini Viewer");
      stage.show();
    } catch (Exception e) {
      try {
        Task t = (Task) entry;
        GridPane pane = new GridPane();
        Scene s = new Scene(pane, 400, 500);
        ImageView imageView = popupView.addIcon
            ("https://www.iconsdb.com/icons/preview/pink/clipboard-2-xxl.png",
                48, 48);
        Label title = new Label("Mini View");
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
        Button completeTask =
            popupView.addPrettyButton("Complete", 80, 30, "pink");
        pane.add(completeTask, 1, 5);
        GridPane.setHalignment(completeTask, HPos.RIGHT);
        pane.setPadding(new Insets(50));
        pane.setHgap(50);
        pane.setVgap(50);
        Stage stage = new Stage();
        completeTask.setOnAction(event -> {
          t.completeTask();
          stage.close();
          update();
        });
        stage.setScene(s);
        stage.setTitle("Mini Viewer");
        stage.show();
      } catch (Exception ignored) {
        // the user clicked an invalid coordinate, we don't need to log this exception
      }
    }
  }
}

