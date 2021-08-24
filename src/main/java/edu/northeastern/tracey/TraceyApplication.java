package edu.northeastern.tracey;

/**
 * This is the main class where we can run the Tracey application, however since implementing
 * ThymeLeaf, we are no longer utilizing this class as the main entry point of the application. Our
 * previous tests have been commented out as proof of testing.
 */
import com.google.gson.Gson;

import edu.northeastern.tracey.model.Location;
import edu.northeastern.tracey.model.WorkoutActivity;
import edu.northeastern.tracey.repositories.ActivityRepository;
import edu.northeastern.tracey.repositories.LocationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import java.io.FileReader;
import java.io.Reader;
import java.util.Optional;

/**
 * Main/Run Class: Creates a SpringBoot instance to run and test our Tracey program.
 *
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class TraceyApplication implements CommandLineRunner {

  private final LocationRepository repository;
  private final ActivityRepository repo;
//  private final clientFrontEndListener cliListener;

  @Autowired
  public TraceyApplication(LocationRepository repository, ActivityRepository repo) {
    this.repository = repository;
    this.repo = repo;
//    cliListener = new clientFrontEndListener(repository,repo);

  }

  public static void main(String[] args) {
    SpringApplication.run(TraceyApplication.class, args);
  }

  // testing through the command line
  @Override
  public void run(String... args) throws Exception {

  //    repository.deleteAll();

    /**
     // All test locations to be imported
     // save a couple of locations
     repository.save(new PointOfInterest("Seattle", 01, 1l, 1l));
     repository.save(new PointOfInterest("Boston", 02, 10l, 10l));
     repository.save(new PointOfInterest("Littleton", 03, 200, 200));
     **/

/*
    // trying to find the original test places
    if (!repository.findAll().isEmpty()){
      Optional<Location> poi = Optional.ofNullable(repository.findById(1));

      String helper = poi.toString();
      System.out.println(helper);

      Optional<Location> poi2 = Optional.ofNullable(repository.findByName("testDummy"));
      String helper2 = poi2.toString();
      System.out.println(helper2);

    }

    // fetch all locations
    System.out.println("Locations:");
    for (PointOfInterest location : repository.findAll()) {
      System.out.println(location);
    }*/
  }

}
