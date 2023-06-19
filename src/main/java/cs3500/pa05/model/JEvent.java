package cs3500.pa05.model;

import cs3500.pa05.model.json.EventJson;

/**
 * To represent an event
 */
public class JEvent extends JournalEntry {
  String startTime;
  String duration;

  /**
   * Constructor with description
   *
   * @param name
   * @param description
   * @param weekday
   * @param startTime
   * @param duration
   */
  public JEvent(String name, String description, Weekday weekday,
                String startTime, String duration) {
    super(name, description, weekday);
    this.startTime = startTime;
    this.duration = duration;
  }

  /**
   * Constructor without description
   *
   * @param name
   * @param weekday
   * @param startTime
   * @param duration
   */
  public JEvent(String name, Weekday weekday,
                String startTime, String duration) {
    super(name, weekday);
    this.startTime = startTime;
    this.duration = duration;
    description = "";
  }

  /**
   * Builds the string equivalent of the Event
   *
   * @return the string equivalent of this journalEntry
   */
  public String journalToString() {
    return null;
  }

  public EventJson toEventJson() {
    return new EventJson(name, description, weekday, startTime, duration);
  }

  public String getStartTime() { return startTime;}

  public String getDuration() { return duration;}

  public void setStartTime(String startTime) {
    this.startTime = startTime;
  }

  public void setDuration(String duration) {
    this.duration = duration;
  }
}