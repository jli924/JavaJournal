package cs3500.pa05.model;

import java.util.Objects;

/**
 * Represents a journal entry
 */
public abstract class JournalEntry {
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
    Objects.requireNonNull(weekday);
    if (!name.isEmpty()) {
      this.name = name;
      this.description = description;
    } else {
      throw new IllegalArgumentException("A journal entry must have a name");
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
      throw new IllegalArgumentException("A journal entry must have a name");
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

  public abstract void mutate(String[] newValues);

  public boolean containsLink() {
    return (description.contains("www.") || description.contains("http"));
  }

  public String getLink() {
    String s;
    String linkStart;
    try {
      linkStart = description.substring(description.indexOf("www."), description.indexOf("www."));
      s = linkStart.substring(0, linkStart.indexOf(" "));
    } catch (Exception ignored) {
      linkStart = description.substring(description.indexOf("www."), description.indexOf("http"));
      s = linkStart.substring(0, linkStart.indexOf(" "));
    }
    return s;
  }

}
