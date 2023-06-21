package cs3500.pa05.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import cs3500.pa05.Driver;
import cs3500.pa05.model.Day;
import cs3500.pa05.model.JEvent;
import cs3500.pa05.model.JavaJournal;
import cs3500.pa05.model.JournalEntry;
import cs3500.pa05.model.Task;
import cs3500.pa05.model.Weekday;
import cs3500.pa05.model.json.DayJson;
import cs3500.pa05.model.json.JournalJson;
import cs3500.pa05.model.json.JsonUtils;
import cs3500.pa05.view.JavaJournalView;
import cs3500.pa05.view.JavaJournalViewImpl;
import cs3500.pa05.view.PopupView;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
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

  @FXML
  TextArea notesAndQuotes;

  PopupView popupView = new PopupView();

  Stage stage;

  /**
   * Constructor
   */
  public JavaJournalControllerImpl() {
    journal = new JavaJournal();
  }

  /**
   * Constructor for loading a journal
   *
   * @param journal the journal to load
   * @param stage the stage to load
   */
  public JavaJournalControllerImpl(JavaJournal journal, Stage stage) {
    this.journal = journal;
    this.stage = stage;
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
   * The scene for the splash screen
   *
   * @return the scene
   */
  public Scene showSplashScreen() {
    Stage splash = popupView.splashScreen();
    return splash.getScene();
  }

  /**
   * To close the splash screen after 2 seconds
   *
   * @param splash  the splash screen
   * @param journal the journal
   * @param scene   the scene for the journal
   */
  public void closeSplashScreen(Stage splash, Stage journal, Scene scene) {
    initJournal(journal, scene);
    PauseTransition pauseTransition = new PauseTransition(Duration.seconds(1));
    pauseTransition.setOnFinished(event -> splash.close());
    pauseTransition.play();
    PauseTransition pauseTransition1 = new PauseTransition(Duration.seconds(1));
    pauseTransition1.setOnFinished(event -> journal.show());
    pauseTransition1.play();
  }

  /**
   * To initialize the journal's scene
   *
   * @param journal the stage for the journal
   * @param scene   the journal's scene
   */
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

  /**
   * To run the application
   */
  public void run() {
    if (!journal.isJournalFileEmpty()) {
      journal.initializeDays();
    }
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
    //AA
    openFile.setOnAction(event -> openFileHandler());
    saveToFile.setOnAction(event -> saveToFileHandler());

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
      try {
        JEvent userJEvent = new JEvent(name.getText(), description.getText(),
            Weekday.valueOfString(weekday.getText().toUpperCase()),
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
      } catch (Exception e) {
        popupView.invalidInputAlert("Invalid event",
            e.getMessage());
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
      try {
        Task userTask = new Task(name.getText(), description.getText(),
            Weekday.valueOfString(weekday.getText().toUpperCase()), false);
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
      } catch (Exception e) {
        popupView.invalidInputAlert("Invalid task",
            e.getMessage());
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


  /**
   * Initializes existing tasks on the gridpane
   */
  private void initTasksandEvents() {
    int colIdx = 0;
    int rowIdx = 1;
    for (Day day : journal.getDays()) {
      for (Task t : day.getTasks()) {
        Label initEntry = new Label(t.getName());
        initEntry.setPadding(new Insets(5));
        initEntry.setOnMouseClicked(event -> {
          miniViewer(initEntry, t);
        });
        mainGrid.add(initEntry, colIdx, rowIdx);
        rowIdx += 1;
      }
      for (JEvent e : day.getEvents()) {
        Label initEntry = new Label(e.getName());
        initEntry.setPadding(new Insets(5));
        initEntry.setOnMouseClicked(event -> {
          miniViewer(initEntry, e);
        });
        mainGrid.add(initEntry, colIdx, rowIdx);
        rowIdx += 1;
      }
      colIdx += 1;
      rowIdx = 1;
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

  /**
   * Saves the converted journal to string and outputs to the file
   *
   * @param file the file to save to
   */
  public void saveToFile(File file) {
    journal.addNotes(notesAndQuotes.getText());
    journal.setWeekTitle(weekLabel.getText());
    journal.writeToFile(file);
  }


  @Override
  public JavaJournal openFile(File file) {
    this.journal = new JavaJournal(file);
    return this.journal;
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
    saveToFile.getScene().getAccelerators().put(
        new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_ANY), () -> saveToFile.fire());
    openFile.getScene().getAccelerators().put(
        new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_ANY), () -> openFile.fire());
  }


  /**
   * Handles the new save to file event
   */
  private void saveToFileHandler() {
    /*
    Stage stage = new Stage();
    FileChooser chooser = new FileChooser();

    File tmp = chooser.showOpenDialog(stage);
    String path = tmp.getAbsolutePath();
    File file = new File(path);
    saveToFile(file);
     */
    TextField field = new TextField();
    Button save = popupView.addPrettyButton("Save", 50, 30, "pink");
    Stage saveStage = popupView.newSaveOrOpenScene("Save to file:", "Filename: ",
        "New Save", field, save,
        "https://www.iconsdb.com/icons/preview/pink/save-as-xxl.png", 19);


    save.setOnAction(event -> {
      String filename = field.getText();
      File file = new File(filename + ".bujo");
      saveToFile(file);
      saveStage.close();
    });
    saveStage.show();
  }

  /**
   * Handles the new save to file event
   */
  private void openFileHandler() {
   /*
    Stage stage = new Stage();
    FileChooser chooser = new FileChooser();

    File tmp = chooser.showOpenDialog(stage);
    String path = tmp.getAbsolutePath();
    File file = new File(path);
    saveToFile(file);
    //AA starting a new file
    this.stage.close();
    JavaJournal newJournal = openFile(file);
    Stage newStage = new Stage();
    JavaJournalController journalController = new JavaJournalControllerImpl(newJournal, newStage);
    JavaJournalView javaJournalView = new JavaJournalViewImpl(journalController);
    newStage.setScene(journalController.showSplashScreen());
    newStage.show();
    Stage journal = new Stage();
    journalController.closeSplashScreen(newStage, journal, javaJournalView.load());
    */
    TextField field = new TextField();
    Button open = popupView.addPrettyButton("Open", 50, 30, "pink");
    Stage openStage = popupView.newSaveOrOpenScene("Open File:",
        "Filename (w/out '.bujo'): ",
        "Open File", field, open,
        "https://www.iconsdb.com/icons/preview/pink/data-transfer-download-xxl.png",
        19);
    open.setOnAction(event -> {
      String filename = field.getText();
      File file = new File(filename + ".bujo");
      JavaJournal newJournal = openFile(file);
      openStage.close();
      //AA starting a new file
      stage.close();
      Stage newStage = new Stage();
      JavaJournalController journalController = new JavaJournalControllerImpl(newJournal, newStage);
      JavaJournalView javaJournalView = new JavaJournalViewImpl(journalController);
      newStage.setScene(journalController.showSplashScreen());
      newStage.show();
      Stage journal = new Stage();
      journalController.closeSplashScreen(newStage, journal, javaJournalView.load());

    });
    openStage.show();
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
        "https://www.iconsdb.com/icons/preview/pink/key-xxl.png", 15);
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
      popupView.eventMiniView(label, entry, this, mainGrid);
    } catch (Exception e) {
      try {
        Button completeTask = popupView.addPrettyButton("Complete",
            80, 30, "pink");
        Task t = (Task) entry;
        Stage stage = new Stage();
        completeTask.setOnAction(event -> {
          t.completeTask();
          stage.close();
          update();
        });
        popupView.taskMiniView(label, t, stage, completeTask, this, mainGrid);
      } catch (Exception ignored) {
        // the user clicked an invalid coordinate, we don't need to log this exception
      }
    }
  }
}

