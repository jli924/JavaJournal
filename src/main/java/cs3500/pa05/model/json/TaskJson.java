package cs3500.pa05.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa05.model.Event;
import cs3500.pa05.model.Task;
import cs3500.pa05.model.Weekday;
import java.util.List;

public record TaskJson(@JsonProperty("name") String name,
                       @JsonProperty("description") String description,
                       @JsonProperty("day") Weekday day, @JsonProperty("complete")
                       boolean complete) {

  public Task toTask() {
    if (description.isEmpty()) {
      return new Task(name, day, complete);
    } else {
      return new Task(name, description, day, complete);
    }
  }
}
