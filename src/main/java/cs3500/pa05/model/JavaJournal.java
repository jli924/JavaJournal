package cs3500.pa05.model;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa05.controller.JavaJournalController;
import cs3500.pa05.model.json.DayJson;
import cs3500.pa05.model.json.JsonUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JavaJournal {
  public Day[] days = new Day[7];
  private File bujoFile;

  public JavaJournal(Day[] days, File file) {
    this.days = days;
    bujoFile = file;
  }

  public JavaJournal() {
    for (int i = 0; i < 7; i++) {
      days[i] = new Day(Weekday.values()[i], new ArrayList<>(),
          new ArrayList<>(), 10, 10);
    }
    bujoFile = new File("testFile.bujo");
  }

  /**
   * Finds the percentage of tasks completed for a given week
   *
   * @return the percentage of tasks completed for a given week
   */
  public double percentComplete() {
    int totalTasks = 0;
    int taskCompleted = 0;
    for (Day day : days) {
      List<Task> tasks = day.getTasks();
      totalTasks += tasks.size();
      for (Task task : tasks) {
        if (task.isComplete()) {
          taskCompleted += 1;
        }
      }
    }
    return (double) taskCompleted / totalTasks;
  }

  /**
   * Gets the total list of Tasks
   *
   * @return the appended list of all tasks for a given week
   */
  public List<Task> getTasks() {
    List<Task> outputTasks = new ArrayList<>();
    for (Day day : days) {
      List<Task> tasks = day.getTasks();
      outputTasks.addAll(tasks);
    }
    return outputTasks;
  }

  /**
   * Gets the total list of names of Tasks
   *
   * @return the appended list of all names of tasks for a given week
   */
  public List<String> getTaskNames() {
    List<String> outputTasks = new ArrayList<>();
    List<Task> allTasks = getTasks();
    for (Task task : allTasks) {
      String str = task.getName();
      outputTasks.add(str);
    }
    return outputTasks;
  }

  /**
   * @param e
   */
  public void addEvent(Event e) {
    for (Day day : days) {
      if (day.equals(e.getWeekday())) {
        day.addEvent(e);
        break;
      }
    }
  }

  /**
   * @param t
   */
  public void addTask(Task t) {
    for (Day day : days) {
      if (day.equals(t.getWeekday())) {
        day.addTask(t);
        break;
      }
    }
  }

  public void initializeDays() {
    // should read the bujo file data and initialize the data from
    // the bujo file in the days field
  }

  public void writeToFile() {
    try {
      FileWriter writer = new FileWriter("testFile.bujo");
      for (Day day : days) {
        writer.write(JsonUtils.serializeRecord(day.toDayJson()).toPrettyString()
            + System.lineSeparator() + System.lineSeparator());
      }
      writer.close();
    } catch (Exception e) {
      System.err.println("Cannot write to .bujoFile");
    }
  }

  public void write(String message) {
    try {
      FileWriter writer = new FileWriter(bujoFile);
      writer.write(message);
    } catch (Exception e) {
      System.err.println("Cannot write to .bujoFile");
    }
  }

}
