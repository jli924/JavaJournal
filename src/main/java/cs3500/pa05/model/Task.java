package cs3500.pa05.model;

import cs3500.pa05.model.json.TaskJson;

public class Task extends JournalEntry {
  Boolean complete;

  /**
   * Instantiates a task with a description
   *
   * @param name the name of the task
   * @param description the description of the task
   * @param day the weekday of the task
   * @param complete the boolean representing completion
   */
  public Task(String name, String description, Weekday day, Boolean complete) {
    super(name, description, day);
    this.complete = complete;
  }

  /**
   * Instantiates a task without a description
   *
   * @param name the name of the task
   * @param weekday the weekday of the day
   * @param complete if the task is complete
   */
  public Task(String name, Weekday weekday, Boolean complete) {
    super(name, weekday);
    description = "";
    this.complete = complete;
  }

  /**
   * Whether a task is complete
   *
   * @return whether the task is complete
   */
  public boolean isComplete() {
    return complete;
  }

  /**
   * Sets a task completion field to true;
   */
  public void completeTask() {
    this.complete = true;
  }

  public TaskJson toTaskJson() {
    return new TaskJson(name, description, weekday, complete);
  }

  public void mutate(String[] newValues) {
    this.name = newValues[0];
    this.description = newValues[1];
    this.weekday = Weekday.valueOf(newValues[2].toUpperCase());
    this.complete = false;
  }
}
