package cs3500.pa05.controller;

import cs3500.pa05.model.JEvent;
import cs3500.pa05.model.JavaJournal;
import cs3500.pa05.model.Weekday;
import cs3500.pa05.view.PopupView;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

// jfc this was so convoluted for no reason
public class SaveProcessor implements EventHandler {
  JEvent entry;
  TextField[] newValues;
  PopupView popupView;
  Stage stage;
  Label label;
  JavaJournalControllerImpl controller;
  GridPane pane;

  public SaveProcessor(JEvent entry, TextField[] newValues, PopupView popupView, Stage stage,
                       Label label, JavaJournalControllerImpl controller, GridPane pane) {
    this.entry = entry;
    this.newValues = newValues;
    this.popupView = popupView;
    this.stage = stage;
    this.label = label;
    this.controller = controller;
    this.pane = pane;
  }

  @Override
  public void handle(Event event) {
    System.out.println("entry before mods: " + entry.getName());
    // we have to move the entry back to the right list
    try {
      System.out.println("reached modification");
      System.out.println("new name is: " + newValues[0]);
      entry.mutate(new String[] {newValues[0].getText(), newValues[1].getText(),
          newValues[2].getText().toUpperCase(), newValues[3].getText(), newValues[4].getText()});
      label.setText(entry.getName());
      label.setOnMouseClicked(event2 -> {
        controller.miniViewer(label, entry);
      });
      pane.add(pane.getChildren().remove(pane.getChildren().indexOf(label)),
          entry.getWeekday().ordinal(),
          JavaJournalControllerImpl.findFirstEmptyRow(pane, entry.getWeekday().ordinal()));
    } catch (Exception e2) {
      popupView.invalidInputAlert("Invalid input",
          "Please ensure all inputs are valid, descriptions are optional");
    }
    // } else {
//      try {
//        if (newValues.length == 3) {
//          entry = new Task(newValues[0].getText(),
//              newValues[1].getText(), Weekday.valueOf(newValues[2].getText()), false);
//        } else {
//          entry = new Task(newValues[0].getText(),
//              Weekday.valueOf(newValues[2].getText()), false);
//        }
//      } catch (Exception e) {
//        popupView.invalidInputAlert("Invalid input",
//            "Please ensure all inputs are valid, descriptions are optional");
//      }
//    }
    stage.close();
    System.out.println("entry after mods: " + entry.getName());
  }
}
