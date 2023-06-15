package cs3500.pa05.model;

/**
 * To represent an event
 */
public class Event extends JournalEntry {
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
  public Event(String name, String description, Weekday weekday,
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
  public Event(String name, Weekday weekday,
               String startTime, String duration) {
    super(name, weekday);
    this.startTime = startTime;
    this.duration = duration;
  }

  /**
   * Builds the string equivalent of the Event
   *
   * @return the string equivalent of this journalEntry
   */
  public String journalToString() {
    return null;
  }

}
