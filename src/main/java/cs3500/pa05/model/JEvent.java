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
    if (startTime.isEmpty() || duration.isEmpty()) {
      throw new IllegalArgumentException("an event must have a start time and duration");
    } else {
      this.startTime = startTime;
      this.duration = duration;
    }
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
    if (startTime.isEmpty() || duration.isEmpty()) {
      throw new IllegalArgumentException("an event must have a start time and duration");
    } else {
      this.startTime = startTime;
      this.duration = duration;
    }
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

  public void mutate(String[] newValues) {
    this.name = newValues[0];
    this.description = newValues[1];
    this.weekday = Weekday.valueOf(newValues[2].toUpperCase());
    this.startTime = newValues[3];
    this.duration = newValues[4];
  }
}
