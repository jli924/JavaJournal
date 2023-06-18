package cs3500.pa05.controller;

import cs3500.pa05.model.Day;
import cs3500.pa05.model.Event;
import cs3500.pa05.model.JavaJournal;
import cs3500.pa05.model.Task;
import cs3500.pa05.model.Weekday;
import cs3500.pa05.view.PopupView;
import java.io.File;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.control.ToggleButton;
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

  private static int findFirstEmptyRow(GridPane gridPane, int columnIndex) {
    int numRows = gridPane.getRowCount();
    for (int row = 0; row < numRows; row++) {
      Node node = getNodeFromGridPane(gridPane, columnIndex, row);
      if (node == null) {
        return row;
      }
    }
    return -1; // Indicates no empty row found
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

  private static int toIndex(Integer value) {
    return value == null ? 0 : value;
  }

  /**
   * To run the application
   */
  public void run() {
    initButtons();
    profilePicture.setFill(new ImagePattern
        (new Image("https://i.pinimg.com/474x/ed/54/3b/ed543b461c96fb73519edf7ac8718f39.jpg")));
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
    mainGrid.setOnMouseClicked(event -> {
      Node clickedNode = event.getPickResult().getIntersectedNode();
      Integer columnIndex = null;
      for (Node node : mainGrid.getChildren()) {
        columnIndex = GridPane.getColumnIndex(node);
        if (columnIndex != null && node.equals(clickedNode)) {
          break;
        }
      }
      String text = clickedNode.toString().substring(
          clickedNode.toString().indexOf("text=") + 5, clickedNode.toString().indexOf(","));
      String entry = (text.substring(1, text.length() - 1));
      miniViewer(journal.getDays()[columnIndex], entry);
    });
    initTasksandEvents();
    setDays();
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
          Label newEvent = new Label(userEvent.getName());
          newEvent.setPadding(new Insets(5));
          mainGrid.add(newEvent,
              userEvent.getWeekday().ordinal(),
              findFirstEmptyRow(mainGrid, userEvent.getWeekday().ordinal()));
          eventStage.close();
        } catch (Exception ignored) {
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
          Label newEvent = new Label(userEvent.getName());
          newEvent.setPadding(new Insets(5));
          mainGrid.add(newEvent,
              userEvent.getWeekday().ordinal(),
              findFirstEmptyRow(mainGrid, userEvent.getWeekday().ordinal()));
          eventStage.close();
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
          mainGrid.add(newTask,
              userTask.getWeekday().ordinal(),
              findFirstEmptyRow(mainGrid, userTask.getWeekday().ordinal()));
          taskStage.close();
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
          mainGrid.add(newTask,
              userTask.getWeekday().ordinal(),
              findFirstEmptyRow(mainGrid, userTask.getWeekday().ordinal()));
          taskStage.close();
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

//  private void showLabel(String label) {
//    try {
//      journal.
//    }
//  }

  public void initTasksandEvents() {
    List<List<String>> titles = new ArrayList<>();
    for (Day day : journal.getDays()) {
      List<String> eventsAndTasks = new ArrayList<>();
      day.getTasks().forEach((task) -> eventsAndTasks.add(task.getName()));
      day.getEvents().forEach((event) -> eventsAndTasks.add(event.getName()));
      titles.add(eventsAndTasks);
    }
    int colIdx = 0;
    int rowIdx = 1;
    for (List<String> list : titles) {
      for (String entry : list) {
        mainGrid.add(new Label(entry), colIdx, rowIdx);
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

  private void miniViewer(Day day, String entry) {
    try {
      Event e = day.findEvent(entry);
      GridPane pane = new GridPane();
      Scene s = new Scene(pane, 250, 500);
      Label title = new Label("Mini Viewer");
      title.setStyle("-fx-font-size: 16");
      pane.add(title, 0, 0);
      pane.add(new Label("Event Name: " + e.getName()), 0, 1);
      pane.add(new Label("Description: " + e.getDescription()), 0, 2);
      pane.add(new Label("Weekday: " + e.getWeekday()), 0, 3);
      pane.add(new Label("Start Time: " + e.getStartTime()), 0, 4);
      pane.add(new Label("Duration: " + e.getDuration()), 0, 5);
      pane.setPadding(new Insets(50));
      pane.setHgap(50);
      pane.setVgap(50);
      Stage stage = new Stage();
      stage.setScene(s);
      stage.setTitle("Mini Viewer");
      stage.show();
    } catch (Exception e) {
      try {
        Task t = day.findTask(entry);
        GridPane pane = new GridPane();
        Scene s = new Scene(pane, 300, 500);
        ImageView imageView = popupView.addIcon
            ("https://www.iconsdb.com/icons/preview/pink/clipboard-2-xxl.png",
                48, 48);
        Label title = new Label("Mini Viewer");
        title.setStyle("-fx-font-size: 16");
        boolean complete = t.isComplete();
        String result = complete ? "Yes" : "No";
        pane.add(title, 0, 0);
        pane.add(new Label("Task Name: " + t.getName()), 0, 1);
        pane.add(new Label("Description: " + t.getDescription()), 0, 2);
        pane.add(new Label("Weekday: " + t.getWeekday()), 0, 3);
        pane.add(new Label("Complete?: " + result), 0, 4);
        //pane.add(imageView, 0, 0);
        GridPane.setHalignment(imageView, HPos.RIGHT);
        Button completeTask =
            popupView.addPrettyButton("Complete", 80, 30, "pink");

        pane.add(completeTask, 0, 5);
        GridPane.setHalignment(completeTask, HPos.RIGHT);
        pane.setPadding(new Insets(50));
        pane.setHgap(50);
        pane.setVgap(50);
        Stage stage = new Stage();
        stage.setScene(s);
        stage.setTitle("Mini Viewer");
        completeTask.setOnAction(event -> {
          t.completeTask();
          stage.close();
        });
        stage.show();
      } catch (Exception ignored) {
        // the user clicked an invalid coordinate, we don't do anything
      }
    }
  }
}

