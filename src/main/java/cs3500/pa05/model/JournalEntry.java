package cs3500.pa05.model;

import java.util.Objects;

/**
 * Represents a journal entry
 */
public abstract class JournalEntry {
  String name;
  String description;
  Weekday weekday;

  public void setName(String name) {
    this.name = name;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setWeekday(Weekday weekday) {
    this.weekday = weekday;
  }

  /**
   * Constructor
   *
   * @param name name of the entry
   * @param description entry description
   * @param weekday weekday of the entry
   */
  JournalEntry(String name, String description, Weekday weekday) {
    Objects.requireNonNull(weekday);
    if (!name.isEmpty()) {
      this.name = name;
      this.description = description;
    } else {
      throw new IllegalArgumentException("a journal entry must have a name");
    }
    this.weekday = weekday;
  }

  /**
   * Constructor without description
   *
   * @param name name of the entry
   * @param weekday weekday of the entry
   */
  JournalEntry(String name, Weekday weekday) {
    Objects.requireNonNull(weekday);
    if (!name.isEmpty()) {
      this.name = name;
    } else {
      throw new IllegalArgumentException("a journal entry must have a name");
    }
    this.weekday = weekday;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public Weekday getWeekday() {
    return weekday;
  }
}
