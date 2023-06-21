package cs3500.pa05.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa05.model.json.DayJson;
import cs3500.pa05.model.json.JournalJson;
import cs3500.pa05.model.json.JsonUtils;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class JavaJournal {
  private Day[] days = new Day[7];
  private File bujoFile;
  private String notesAndQuotes;
  private String weekTitle;
  private String password;

  public JavaJournal(Day[] days, File file) {
    this.days = days;
    bujoFile = new File("testFile.bujo");
  }

  public JavaJournal(File file) {
    this.bujoFile = file;
    initializeDays();
  }

  public JavaJournal() {
    for (int i = 0; i < 7; i++) {
      days[i] = new Day(Weekday.values()[i], new ArrayList<>(),
          new ArrayList<>(), 10, 10);
    }
    //bujoFile = new File("testFile2.bujo");
  }



  /**
   * Finds the percentage of tasks completed for a given week
   *
   * @return the percentage of tasks completed for a given week
   */
  public double percentComplete() {
    double totalTasks = getTasks().size();
    double tasksCompleted = 0;
    for (Day day : days) {
      List<Task> tasks = day.getTasks();
      for (Task task : tasks) {
        if (task.isComplete()) {
          tasksCompleted += 1;
        }
      }
    }
    if (totalTasks == 0) {
      return 0;
    } else {
      return tasksCompleted / totalTasks * 100;
    }
  }

  /**
   * Gets the total list of Tasks
   *
   * @return the appended list of all tasks for a given week
   */
  public List<Task> getTasks() {
    List<Task> outputTasks = new ArrayList<>();
    for (Day day : days) {
      List<Task> tasks = day.getTasks();
      outputTasks.addAll(tasks);
    }
    return outputTasks;
  }

  /**
   * Total events of this journal
   *
   * @return total events of this journal
   */
  public List<JEvent> getJEvents() {
    List<JEvent> JEvents = new ArrayList<>();
    for (Day day : days) {
      JEvents.addAll(day.getEvents());
    }
    return JEvents;
  }

  /**
   * @param e
   */
  public void addEvent(JEvent e) {
    for (Day day : days) {
      if (day.getDay().equals(e.getWeekday())) {
        day.addEvent(e);
        break;
      }
    }
  }

  /**
   * @param t
   */
  public void addTask(Task t) {
    for (Day day : days) {
      if (day.getDay().equals(t.getWeekday())) {
        day.addTask(t);
        break;
      }
    }
  }

  /**
   *
   *
   */
  public void initializeDays() {
    // should read the bujo file data and initialize the data from
    // the bujo file in the fields
    ObjectMapper mapper = new ObjectMapper();
    try {
      JsonNode journalFile = mapper.readTree(bujoFile);
      JournalJson journalJson = mapper.convertValue(journalFile, JournalJson.class);

      Day[] daysFromFile = new Day[7];
      for (int i = 0; i < 7; i++) {
        DayJson dayJson = journalJson.days()[i];
        daysFromFile[i] = dayJson.toDay();
      }
      this.days = daysFromFile;
      this.notesAndQuotes = journalJson.notesAndQuotes();
      this.weekTitle = journalJson.weekTitle();
      this.password = journalJson.password();
    } catch (Exception e) {
      System.err.println(e);
    }
  }

  /**
   *
   *
   * @return
   */
  public Day[] getDays() {
    return days;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  /**
   *
   */
  public void writeToFile(File file) {
    this.bujoFile = file;
    JsonNode journalNode = JsonUtils.serializeRecord(this.toJournalJson());
    try {
      FileWriter writer = new FileWriter(bujoFile);
      writer.write(journalNode.toPrettyString());
      writer.close();
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  /**
   *
   *
   * @return
   */
  private JournalJson toJournalJson() {
    DayJson[] dayJsons = new DayJson[7];
    for (int i = 0; i < 7; i++) {
      Day day = days[i];
      DayJson cur = day.toDayJson();
      dayJsons[i] = cur;
    }
    return new JournalJson(dayJsons, this.bujoFile, this.notesAndQuotes, this.weekTitle,
        this.password);
  }

  /**
   *
   * @param notesAndQuotes
   */
  public void addNotes(String notesAndQuotes) {
    this.notesAndQuotes = notesAndQuotes;
  }

  public void setWeekTitle(String weekTitle) {
    this.weekTitle = weekTitle;
  }

  public boolean isJournalFileEmpty() {
    return this.bujoFile == null;
  }

  public void setMaxTasks(int newMax) {
    for (Day day : days) {
      day.changeMaxTasks(newMax);
    }
  }

  public void setMaxEvent(int newMax) {
    for (Day day : days) {
      day.changeMaxEvents(newMax);
    }
  }

  public String getNotesAndQuotes() {
    return notesAndQuotes;
  }

  public String getWeekTitle() {
    return weekTitle;
  }
}
