package cs3500.pa05.model;

import java.util.List;

/**
 * To represent a day
 */
public class Day {
  private Weekday day;
  private List<Task> tasks;
  private List<Event> events;
  private int maxEvents;
  private int maxTasks;

  /**
   * Instantiates a weekday
   *
   * @param day the day of the week
   * @param tasks the tasks for that day
   * @param events the events for that day
   * @param maxEvents the max events for the day
   * @param maxTasks the max tasks for the day
   */
  public Day(Weekday day, List<Task> tasks, List<Event> events,
             int maxEvents, int maxTasks) {
    this.day = day;
    this.tasks = tasks;
    this.events = events;
    this.maxEvents = maxEvents;
    this.maxTasks = maxTasks;
  }

  /**
   * Returns the tasks for a day
   *
   * @return the list of tasks
   */
  public List<Task> getTasks() {
    return tasks;
  }


  public void addEvent(Event e) {
    events.add(e);
  }

  public void addTask(Task t) {
    tasks.add(t);
  }
}
