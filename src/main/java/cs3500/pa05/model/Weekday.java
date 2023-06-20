package cs3500.pa05.model;

/**
 * Represents a weekday (Sunday-Saturday)
 */
public enum Weekday {
  SUNDAY,
  MONDAY,
  TUESDAY,
  WEDNESDAY,
  THURSDAY,
  FRIDAY,
  SATURDAY;


  /**
   * Performs the same functionality as valuOf() but with a user-friendly error message
   *
   * @param weekday the string to convert to a weekday
   * @return the given string as a weekday
   */
  public static Weekday valueOfString(String weekday) {
    try {
      return Weekday.valueOf(weekday);
    } catch (Exception ignored){
      throw new IllegalArgumentException("Invalid weekday, " +
          "please ensure your weekday is spelled correctly");
    }
  }
}
