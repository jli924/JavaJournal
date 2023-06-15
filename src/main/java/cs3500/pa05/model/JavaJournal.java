package cs3500.pa05.model;

import java.util.ArrayList;
import java.util.List;

public class JavaJournal {
  Day[] days = new Day[7];

  /**
   * Finds the percentage of tasks completed for a given week
   * @return
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

}
