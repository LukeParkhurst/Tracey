package edu.northeastern.tracey.model;

/**
 * Running Class Child Class - from WorkoutActivity that represents a model.Running activity.
 */
public class Running extends WorkoutActivity {

  public Running(String activity, int calories, int steps, int distance, int duration,
                 String startTime, String endTime) {
    super(activity, 0, calories, steps, distance, duration, startTime, endTime);
  }

  public Running(String activity, int locID, int calories, int steps, int distance, int duration,
                 String startTime, String endTime) {
    super(activity, locID, calories, steps, distance, duration, startTime, endTime);
  }

}
