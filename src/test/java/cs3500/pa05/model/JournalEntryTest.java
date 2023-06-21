package cs3500.pa05.model;

import static org.junit.jupiter.api.Assertions.*;

import cs3500.pa05.model.json.TaskJson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JournalEntryTest {

  JournalEntry task;
  JournalEntry event;
  JournalEntry linkedEvent;

  @BeforeEach
  void setUp() {
    task = new Task("Drink water", "Stay hydrated", Weekday.FRIDAY, false);
    event = new JEvent("Visit grandma", "Bring grandma fruit",
        Weekday.SUNDAY, "10:00", "2hrs");
    linkedEvent = new JEvent("Zoom event", "www.fakelink.com ",
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
    //assertEquals("www.fakelink.com", linkedEvent.getLink());
  }
}