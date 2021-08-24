package edu.northeastern.tracey.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Walking Class Child Class - from WorkoutActivity that represents a model.Walking activity.
 */
public class Walking extends WorkoutActivity {

  public Walking(String activity, int calories, int steps, int distance, int duration,
                 String startTime, String endTime) {
    super(activity, 0, calories, steps, distance, duration, startTime, endTime);
  }

  public Walking(String activity, int locID, int calories, int steps, int distance, int duration,
                 String startTime, String endTime) {
    super(activity, locID, calories, steps, distance, duration, startTime, endTime);
  }

}
