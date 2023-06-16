package cs3500.pa05.model;

import cs3500.pa05.controller.JavaJournalController;
import java.util.ArrayList;
import java.util.List;

public class JavaJournal {
  private Day[] days = new Day[7];

  public JavaJournal(Day[] days) {
    days = days;
  }

  public JavaJournal() {
    for (int i = 0; i < 7; i++) {
      days[i] = new Day(Weekday.values()[i], new ArrayList<>(),
          new ArrayList<>(), 10, 10);
    }
  }

  /**
   * Finds the percentage of tasks completed for a given week
   *
   * @return the percentage of tasks completed for a given week
   */
  public double percentComplete() {
    int totalTasks = 0;
    int taskCompleted = 0;
    for (Day day: days) {
      List<Task> tasks = day.getTasks();
      totalTasks += tasks.size();
      for (Task task: tasks) {
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
  public List<Task> getTasks () {
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
  public List<String> getTaskNames () {
    List<String> outputTasks = new ArrayList<>();
    List<Task> allTasks = getTasks();
    for (Task task : allTasks) {
      String str = task.getName();
      outputTasks.add(str);
    }
    return outputTasks;
  }

  /**
   *
   *
   * @param e
   */
  public void addEvent(Event e) {
    for (Day day: days) {
      if (day.equals(e.getWeekday())) {
        day.addEvent(e);
        break;
      }
    }
  }

  /**
   *
   *
   * @param t
   */
  public void addTask(Task t) {
    for (Day day: days) {
      if (day.equals(t.getWeekday())) {
        day.addTask(t);
        break;
      }
    }
  }

}
