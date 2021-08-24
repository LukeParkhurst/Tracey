package edu.northeastern.tracey.model;

/**
 * Transportation Class Child Class - from WorkoutActivity that represents a model.Transport
 * activity.
 */
public class Transportation extends WorkoutActivity {

  public Transportation(String activity, int calories, int steps, int distance, int duration,
      String startTime, String endTime) {
    super(activity, 0, calories, steps, distance, duration, startTime, endTime);
  }

  public Transportation(String activity, int locID, int calories, int steps, int distance, int duration,
                        String startTime, String endTime) {
    super(activity, locID, calories, steps, distance, duration, startTime, endTime);
  }

}
