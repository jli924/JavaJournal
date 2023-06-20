package cs3500.pa05.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa05.model.Day;
import cs3500.pa05.model.JEvent;
import cs3500.pa05.model.Task;
import cs3500.pa05.model.Weekday;
import java.util.ArrayList;
import java.util.List;

public record DayJson(@JsonProperty("day") Weekday day, @JsonProperty("tasks") List<TaskJson> tasks,
                      @JsonProperty("events") List<EventJson> events, @JsonProperty("maxEvents")
                      int maxEvents, @JsonProperty("maxTasks") int maxTasks) {

  public Day toDay() {
    List<Task> taskList = new ArrayList<>();
    List<JEvent> JEventList = new ArrayList<>();
    tasks.forEach((task) -> taskList.add(task.toTask()));
    events.forEach((event) -> JEventList.add(event.toEvent()));
    return new Day(day, taskList,
        JEventList, maxEvents, maxTasks);
  }
}
