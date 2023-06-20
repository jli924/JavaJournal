package cs3500.pa05.model;

import cs3500.pa05.model.json.DayJson;
import cs3500.pa05.model.json.EventJson;
import cs3500.pa05.model.json.TaskJson;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * To represent a day
 */
public class Day {
  private Weekday day;
  private List<Task> tasks;
  private List<JEvent> JEvents;
  private int maxEvents;
  private int maxTasks;

  /**
   * Instantiates a weekday
   *
   * @param day       the day of the week
   * @param tasks     the tasks for that day
   * @param JEvents    the events for that day
   * @param maxEvents the max events for the day
   * @param maxTasks  the max tasks for the day
   */
  @JsonCreator
  public Day(Weekday day, List<Task> tasks, List<JEvent> JEvents,
             int maxEvents, int maxTasks) {
    this.day = day;
    this.tasks = tasks;
    this.JEvents = JEvents;
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

  /**
   * Adds an event to the day
   *
   * @param JEvent the event
   */
  public void addEvent(JEvent JEvent) {
    JEvents.add(JEvent);
  }

  /**
   * Adds a task to the day
   *
   * @param task the event
   */
  public void addTask(Task task) {
    tasks.add(task);
  }

  /**
   *
   *
   * @return
   */
  public DayJson toDayJson() {
    List<TaskJson> taskList = new ArrayList<>();
    List<EventJson> eventList = new ArrayList<>();
    tasks.forEach((task) -> taskList.add(task.toTaskJson()));
    JEvents.forEach((event) -> eventList.add(event.toEventJson()));
    return new DayJson(day, taskList, eventList, maxEvents, maxTasks);
  }

  public List<JEvent> getEvents() {
    return JEvents;
  }

  public JEvent findEvent(String event) {
    for (JEvent e : JEvents) {
      if (e.getName().equals(event)) {
        return e;
      }
    }
    return null;
  }

  public Task findTask(String task) {
    for (Task t : tasks) {
      if (t.getName().equals(task)) {
        return t;
      }
    }
    return null;
  }

  public Weekday getDay() {return day;}

  public int getMaxEvents() {
    return this.maxEvents;
  }

  public int getMaxTasks() {
    return this.maxTasks;
  }
}
