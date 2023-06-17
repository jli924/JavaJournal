package cs3500.pa05.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa05.model.Event;
import cs3500.pa05.model.Weekday;

public record EventJson(@JsonProperty("name") String name,
                        @JsonProperty("description") String description,
                        @JsonProperty("day") Weekday day, @JsonProperty("start-time")
                        String startTIme, @JsonProperty("duration") String duration) {

  public Event toEvent() {
    if (description.isEmpty()) {
      return new Event(name, day, startTIme, duration);
    } else {
      return new Event(name, description, day, startTIme, duration);
    }
  }
}
