package cs3500.pa05.model;

/**
 * Represents a journal entry
 */
public abstract class JournalEntry implements IJournalEntry {
  String name;
  String description;
  Weekday weekday;

  /**
   * Constructor
   *
   * @param name name of the entry
   * @param description entry description
   * @param weekday weekday of the entry
   */
  JournalEntry(String name, String description, Weekday weekday) {
    this.name = name;
    this.description = description;
    this.weekday = weekday;
  }

  /**
   * Constructor without description
   *
   * @param name name of the entry
   * @param weekday weekday of the entry
   */
  JournalEntry(String name, Weekday weekday) {
    this.name = name;
    this.weekday = weekday;
  }

  public String getName() {
    return name;
  }


  public Weekday getWeekday() {
    return weekday;
  }
}
