package edu.northeastern.tracey.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WorkoutActivityTest {
  // dates are in UTC format
  // 02/09/2013 time: 14:50:54 PT (0800)
  // 2013-02-09-T145054-0800

  // date and start time is 08/30/2020 @ 12:05:31
  String dateAndStartTime1 = "20200830T120531-0800";

  // end time is 8/30/2020 @ 13:05:31
  String endTime1 = "20200830T130531-0800";

  // date and start time is 08/30/2020 @ 13:30:31
  String dateAndStartTime2 = "20200830T133031-0800";

  // end time is 8/30/2020 @ 14:05:31
  String endTime2 = "20200830T140531-0800";

  // date and start time is 04/15/2019 @ 12:05:31
  String dateAndStartTime3 = "20190415T120531-0800";


  // end time is 4/15/2019 @ 13:05:31
  String endTime3 = "20190415T130531-0800";

  // first two activities have same date, different times
  WorkoutActivity w1 = new Cycling("cycling", 12, 15, 10, 10, 15, dateAndStartTime1, endTime1);

  WorkoutActivity w2 = new Running("running", 16, 15, 10, 10, 15, dateAndStartTime2, endTime2);

  WorkoutActivity w3 = new Walking("walking", 20, 15, 10, 10, 15, dateAndStartTime3, endTime3);


  // expected should be w3, w1, w2
  // sorts by furthest to present date, if two activities have the same date, then it sorts by increasing time

  List<WorkoutActivity> listTester = new ArrayList<>();

  WorkoutActivity tester;



 @BeforeEach
  void setUp() {
    listTester.add(w2);
    listTester.add(w3);
    listTester.add(w1);

    //tester = new WorkoutActivity(listTester);

  }

  @Test
  void shouldGetCalories() {
    assertEquals(15, w1.getCalories());
  }

  @Test
  void shouldGetSteps() {
    assertEquals(10, w1.getSteps());
  }

  @Test
  void shouldGetDistance() {
    assertEquals(10, w1.getDistance());
  }

  @Test
  void shouldGetDuration() {
    assertEquals(15, w1.getDuration());
  }

  @Test
  void shouldGetStartTime() {
    assertEquals(dateAndStartTime1, w1.getStartTime());
  }

  @Test
  void shouldGetEndTime() {
    assertEquals(endTime1, w1.getEndTime());
  }

//  @Test
//  void shouldGetListOfWorkouts() {
//    assertEquals(listTester, tester.getListOfWorkouts());
//  }

  @Test
  void shouldCreateAndAddActivity() throws IllegalArgumentException {
    // date and start time is 04/15/2019 @ 15:05:31
    String dateAndStartTime4 = "20190415T150531-0800";

    // end time is 4/15/2019 @ 15:30:31
    String endTime4 = "20190415T153031-0800";


//    tester.addActivity(ActivityType.TRANSPORT, "transport", 10,10, 10, 10, 10, dateAndStartTime4, endTime4);

    assertEquals(4, listTester.size());

  }

//  @Test
//  void shouldSortActivityByEndTime() {
//    List<WorkoutActivity> expected = Arrays.asList(w3, w1, w2);
//
//    tester.sortActivityByEndTime();
//    assertEquals(expected, listTester);
//  }


  @Test
  void shouldConvertToString() {
    String expected = "Activity: cycling , calories=15, steps='10', distance='10', duration='15', date='20200830T120531-0800', startTime='20200830T120531-0800', endTime='20200830T130531-0800'";
    assertEquals(expected, w1.toString());
  }

}

