package edu.northeastern.tracey.repositories;

import edu.northeastern.tracey.model.Location;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * PointOfInterest Repository Interface
 * Represents functionality for accessing data in MongoDB, including CRUD
 * operations.
 * Usually we would need a java class to implement an interface, but with
 * Springboot, we can run methods "on their own" in the TracyApplication Class.
 */
public interface LocationRepository
        extends MongoRepository<Location, String> {

  Location findByName(String name);

  void deleteById(Integer locationId);

  Location findById(Integer locationId);

  // Location findByLocationId(int LocId);

  // List<Location> getLocation(String name);
}
