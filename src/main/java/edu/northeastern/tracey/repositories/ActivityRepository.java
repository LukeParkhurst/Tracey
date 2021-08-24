package edu.northeastern.tracey.repositories;

import edu.northeastern.tracey.model.WorkoutActivity;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

/**
 * Activity Repository Interface Represents common functionality for all extending workout
 * activities.
 */
public interface ActivityRepository
        extends MongoRepository<WorkoutActivity, String> {

  List<WorkoutActivity> findByStartTimeContaining(String startTime);

  List<WorkoutActivity> findByLocID(int locID);

  List<WorkoutActivity> findAllByLocID(int locId);

  List<WorkoutActivity> findAllByActivity(String activity);
}
