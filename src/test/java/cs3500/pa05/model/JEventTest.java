package cs3500.pa05.model;


import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa05.model.json.EventJson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the JEvent class
 */
class JEventTest {

  JEvent event;
  EventJson eventJson;
  String[] newValues;

  @BeforeEach
  void setUp() {
    event = new JEvent("Visit grandma", "Bring grandma fruit",
        Weekday.SUNDAY, "10:00", "2hrs");
    eventJson = new EventJson("Visit grandma", "Bring grandma fruit",
        Weekday.SUNDAY, "10:00", "2hrs");
    newValues = new String[] {"Call mom", "Tell her about my new cat", "Friday", "9:00", "1hr"};
  }

  @Test
  void toEventJson() {
    assertEquals(eventJson.name(), event.toEventJson().name());
  }

  @Test
  void getStartTime() {
    assertEquals("10:00", event.getStartTime());
  }

  @Test
  void getDuration() {
    assertEquals("2hrs", event.getDuration());
  }

  @Test
  void mutate() {
    event.mutate(newValues);
    assertEquals("9:00", event.getStartTime());
    assertEquals("1hr", event.getDuration());
  }
}