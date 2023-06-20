package cs3500.pa05;

import cs3500.pa05.controller.JavaJournalController;
import cs3500.pa05.controller.JavaJournalControllerImpl;
import cs3500.pa05.model.JavaJournal;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import cs3500.pa05.view.JavaJournalView;
import cs3500.pa05.view.JavaJournalViewImpl;
import javafx.scene.image.Image;
import javafx.util.Duration;


public class Driver extends Application {

  /**
   * Starts the GUI for the Java Journal
   *
   * @param stage the JavaFX stage to add elements to
   */
  @Override
  public void start(Stage stage) {
    // instantiate a simple JavaJournal cs3500.view
    JavaJournal javaJournal = new JavaJournal();
    JavaJournalController journalController = new JavaJournalControllerImpl(javaJournal, stage);
    JavaJournalView javaJournalView = new JavaJournalViewImpl(journalController);
    stage.setScene(journalController.showSplashScreen());
    stage.show();
    Stage journal = new Stage();
    journalController.closeSplashScreen(stage, journal, javaJournalView.load());
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