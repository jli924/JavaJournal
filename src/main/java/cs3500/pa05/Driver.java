package cs3500.pa05;

import cs3500.pa05.controller.JavaJournalController;
import cs3500.pa05.controller.JavaJournalControllerImpl;
import cs3500.pa05.model.Day;
import cs3500.pa05.model.Event;
import cs3500.pa05.model.JavaJournal;
import cs3500.pa05.model.Task;
import cs3500.pa05.model.Weekday;
import cs3500.pa05.model.json.JsonUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import javafx.application.Application;
import javafx.stage.Stage;
import cs3500.pa05.view.JavaJournalView;
import cs3500.pa05.view.JavaJournalViewImpl;
import javafx.scene.image.Image;


public class Driver extends Application {

  /**
   * Starts the GUI for a game of Whack-A-Mole.
   *
   * @param stage the JavaFX stage to add elements to
   */
  @Override
  public void start(Stage stage) {
    // instantiate a simple JavaJournal cs3500.view
    JavaJournalController journalController = new JavaJournalControllerImpl();
    JavaJournalView javaJournalView = new JavaJournalViewImpl(journalController);
    try {
      // load and place the cs3500.view's scene onto the stage
      stage.getIcons().add(new Image(
          Objects.requireNonNull(getClass()
              .getClassLoader().getResource("appIcon.png")).openStream()));
      stage.setScene(javaJournalView.load());
      stage.setTitle("Java Journal");
      stage.show();
      journalController.run();
    } catch (IllegalStateException | IOException exc) {
      System.err.println("Unable to load GUI.");
    }
  }

  public static void main(String[] args) {
    launch();
  }
}

  //Stuff for testing file methods

//  Task task1 = new Task("task 1", "sampleDescription1",
//      Weekday.SUNDAY, false);
//  Task task2 = new Task("task 2", "sampleDescription2",
//      Weekday.SUNDAY, false);
//  Event event1 = new Event("event1", "sampleEvent1",
//      Weekday.MONDAY, "11:00am", "2hrs");
//  Event event2 = new Event("event2", "sampleEvent2",
//      Weekday.MONDAY, "12:00am", "3hrs");
//  List<Task> taskList = Arrays.asList(task1, task2);
//  List<Event> eventList = Arrays.asList(event1, event2);
//  JavaJournal journal = new JavaJournal();
//    journal.days[0] = new Day(Weekday.SUNDAY, taskList, eventList, 2, 2);
//        journal.days[1] = new Day(Weekday.MONDAY, taskList, eventList, 2, 2);
//        System.out.println("day 0: " +
//        JsonUtils.serializeRecord(journal.days[0].toDayJson()).toPrettyString());
//        journal.writeToFile();
//    journal.days[0] = new Day(Weekday.SUNDAY, new ArrayList<>(),
//        new ArrayList<>(), 10, 10);
//    journal.days[1] = new Day(Weekday.MONDAY, new ArrayList<>(),
//        new ArrayList<>(), 10, 10);
//    journal.initializeDays();
//    System.out.println(journal.days[0]);