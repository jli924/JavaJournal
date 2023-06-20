package cs3500.pa05.model.json;
import java.io.File;

/**
 * Represents a java record
 *
 * @param days
 * @param bujoFile
 * @param notesAndQuotes
 * @param weekTitle
 * @param password
 */
public record JournalJson(DayJson[] days, File bujoFile, String notesAndQuotes,
                          String weekTitle, String password) {
}
