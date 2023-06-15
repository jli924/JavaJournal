package cs3500.pa05.model;

public abstract class JournalEntry implements IJournalEntry {
  String name;
  String description;
  Weekday weekday;

  JournalEntry(String name, String description, Weekday weekday) {
    this.name = name;
    this.description = description;
    this.weekday = weekday;
  }

  JournalEntry(String name, Weekday weekday) {
    this.name = name;
    this.weekday = weekday;
  }

  public String getName() {
    return name;
  }

}
