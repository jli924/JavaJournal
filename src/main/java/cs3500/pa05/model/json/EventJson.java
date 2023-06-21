package cs3500.pa05.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa05.model.JEvent;
import cs3500.pa05.model.Weekday;

public record EventJson(@JsonProperty("name") String name,
                        @JsonProperty("description") String description,
                        @JsonProperty("day") Weekday day, @JsonProperty("start-time")
                        String startTime, @JsonProperty("duration") String duration) {

  public JEvent toEvent() {
    if (description.isEmpty()) {
      return new JEvent(name, day, startTime, duration);
    } else {
      return new JEvent(name, description, day, startTime, duration);
    }
  }
}
