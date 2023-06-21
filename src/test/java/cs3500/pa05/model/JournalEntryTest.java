package cs3500.pa05.model;

import static org.junit.jupiter.api.Assertions.*;

import cs3500.pa05.model.json.TaskJson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JournalEntryTest {

  JournalEntry task;
  JournalEntry event;
  JournalEntry linkedEvent;
  JournalEntry linkedEvent2;
  JournalEntry linkedEvent3;
  JournalEntry linkedEvent4;
  @BeforeEach
  void setUp() {
    task = new Task("Drink water", "Stay hydrated", Weekday.FRIDAY, false);
    event = new JEvent("Visit grandma", "Bring grandma fruit",
        Weekday.SUNDAY, "10:00", "2hrs");
    linkedEvent = new JEvent("Zoom event1", "www.fakelink.com",
        Weekday.MONDAY, "9:00", "2hrs");
    linkedEvent2 = new JEvent("Zoom event2", "https.fakelink.com",
        Weekday.MONDAY, "9:00", "2hrs");
    linkedEvent3 = new JEvent("Zoom event3", "my link www.fakelink.com join soon!",
        Weekday.MONDAY, "9:00", "2hrs");
    linkedEvent4 = new JEvent("Zoom event4", "use this https.fakelink.com link",
        Weekday.MONDAY, "9:00", "2hrs");
  }

  @Test
  void getName() {
    assertEquals("Drink water", task.getName());
    assertEquals("Visit grandma", event.getName());
  }

  @Test
  void getDescription() {
    assertEquals("Stay hydrated", task.getDescription());
    assertEquals("Bring grandma fruit", event.getDescription());
  }

  @Test
  void getWeekday() {
    assertEquals(Weekday.FRIDAY, task.getWeekday());
    assertEquals(Weekday.SUNDAY, event.getWeekday());
  }

  @Test
  void containsLink() {
    assertTrue(linkedEvent.containsLink());
  }

  @Test
  void getLink() {
    System.out.println(linkedEvent3.getLink());
    assertEquals("www.fakelink.com", linkedEvent.getLink());
    assertEquals("https.fakelink.com", linkedEvent2.getLink());
    assertEquals("www.fakelink.com", linkedEvent3.getLink());
    assertEquals("https.fakelink.com", linkedEvent4.getLink());
  }
}