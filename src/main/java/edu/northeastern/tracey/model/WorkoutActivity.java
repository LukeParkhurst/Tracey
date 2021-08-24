package edu.northeastern.tracey.model;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * WorkoutActivity Class represents a Workout Activity and stores a list of activities.
 */
@Document
public class WorkoutActivity {

  @Field
  private String activity;
  @Field
  private int locID;
  @Field
  private int calories;
  @Field
  private int steps;
  @Field
  private int distance;
  @Field
  private int duration;
  @Field
  private String startTime;
  @Field
  private String endTime;

  /**
   * 1st Constructor: Initialize a Workout Activity object with calories, steps, distance, duration,
   * start and end time.
   *
   * @param calories  - calories expended
   * @param steps     - steps taken
   * @param distance  - total distance travelled,
   * @param duration  - total duration in seconds
   * @param startTime - start time of activity
   * @param endTime   - end time of activity
   */


  // if locId = 123456789
  public WorkoutActivity(String activity, int calories, int steps, int distance, int duration,
                         String startTime, String endTime) {
    this.locID = 0;
    this.activity = activity;
    this.calories = calories;
    this.steps = steps;
    this.locID=0;
    this.distance = distance;
    this.duration = duration;
    this.startTime = startTime;
    this.endTime = endTime;
  }

  // 2nd constructor to include locID if available
  public WorkoutActivity(String activity, int locID, int calories, int steps, int distance, int duration,
                         String startTime, String endTime) {
    this.locID = 0;
    this.activity = activity;
    this.locID = locID;
    this.calories = calories;
    this.steps = steps;
    this.distance = distance;
    this.duration = duration;
    this.startTime = startTime;
    this.endTime = endTime;
  }

  /**
   * This constructor creates a singular workout activity from a list of workout activities.
   * For example, if there is a list of walking activities within a specific day, we can create
   * one singular activity for that day - essentially creating a summary.
   *
   * @param listOfWorkouts - a list of workout activities.
   */
  public WorkoutActivity(List<WorkoutActivity> listOfWorkouts) {
    // map reduce function required to create a combined singular workout
    // i.e. creating 1 running activity from 10
    // for listOfWorkouts = listOfWorkouts;
  }

  /**
   * 3rd Constructor: ** currently undefined
   */
  public WorkoutActivity() {
  }

  public String getActivity() {
    return activity;
  }

  /**
   * Getters: Get methods for all class fields.
   */
  public Integer getCalories() {
    return calories;
  }

  public Integer getSteps() {
    return steps;
  }

  public Integer getDistance() {
    return distance;
  }

  public Integer getDuration() {
    return duration;
  }

  public String getStartTime() {
    return startTime;
  }

  public String getEndTime() {
    return endTime;
  }

  /**
   * Set a location ID to an activity object
   * @param locID - a given location ID
   */
  public void setLocID(int locID) {
    this.locID = locID;
  }

}
