package edu.northeastern.tracey.model;

/**
 * Cycling Class - Child Class - from WorkoutActivity that represents a model.Cycling activity.
 */
public class Cycling extends WorkoutActivity {

  public Cycling(String activity, int calories, int steps, int distance, int duration,
                 String startTime, String endTime) {
    super(activity, 0, calories, steps, distance, duration, startTime, endTime);
  }

  public Cycling(String activity, int locID, int calories, int steps, int distance, int duration,
                 String startTime, String endTime) {
    super(activity, locID, calories, steps, distance, duration, startTime, endTime);
  }
}
