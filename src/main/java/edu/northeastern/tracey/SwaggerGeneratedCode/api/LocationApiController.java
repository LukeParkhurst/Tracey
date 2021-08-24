package edu.northeastern.tracey.SwaggerGeneratedCode.api;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.northeastern.tracey.model.Location;
import edu.northeastern.tracey.model.WorkoutActivity;
import edu.northeastern.tracey.repositories.ActivityRepository;
import edu.northeastern.tracey.repositories.LocationRepository;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * This class represents a controller for the location entity.
 * It implements the location REST API and carries out functionality
 * that allows the client to perform CRUD operations to the collection.
 */
@Controller
public class LocationApiController implements LocationApi {

    private final ObjectMapper objectMapper;
    private final HttpServletRequest request;
    private final LocationRepository locRepository;
    private final ActivityRepository activityRepository;
    private static final Logger logger = Logger.getLogger(LocationApiController.class.getName());



    /**
     * Constructor that creates the controller with objectMapper,
     * request, location repository and activity repository
     * @param objectMapper
     * @param request
     * @param locRepository - location repository
     * @param activityRepository - activity repository
     */
    @org.springframework.beans.factory.annotation.Autowired
    public LocationApiController(ObjectMapper objectMapper, HttpServletRequest request, LocationRepository locRepository, ActivityRepository activityRepository) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.locRepository = locRepository;
        this.activityRepository = activityRepository;
    }

    @Override
    public Optional<ObjectMapper> getObjectMapper() {
        return Optional.ofNullable(objectMapper);
    }

    @Override
    public Optional<HttpServletRequest> getRequest() {
        return Optional.ofNullable(request);
    }

    /**
     * POST method that adds a new location from the request body
     * and saves it to the collection.
     *
     * Request URI: /location
     * @param location - a given location from the request body
     * @return the new location object and a 201 status code if successful, 400 code otherwise
     */
    @Override
    public ResponseEntity<Location> createLocationRecord(@RequestBody Location location) {
        if (location == null) {
            logger.warning("location cannot be empty");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            logger.info(location.toString());
        }

        // grab the current size of the collection and convert it to
        // integer type + 1 to set the next id
        Long size = locRepository.count();
        location.setId(size.intValue() + 1);

        locRepository.save(location);
        return new ResponseEntity<>(location, HttpStatus.CREATED);
    }

    /**
     * DELETE a specific location by searching through the
     * collection with the given ID.
     *
     * Request URI: /location/{locationId}
     * @param locationId - location ID to delete
     * @return the deleted location document and 200 status code if successful, 400 code otherwise
     */
    @Override
    public ResponseEntity<Location> deleteSingleLocation(@PathVariable Integer locationId) {
        Location location = locRepository.findById(locationId);

        // check if resource exists
        if (location != null) {
            locRepository.deleteById(locationId);
            logger.info("Successfully deleted " + locationId);
            return new ResponseEntity<>(location, HttpStatus.OK);
        } else {
            logger.warning("location not found on :: "+ locationId);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * GET a list of activities from a specific location. Methods searches
     * by location ID in the location collection to check
     * if the location object exists. Then it searches in the
     * activity collection by location ID and returns a
     * list of activities.
     *
     * Request URI: /location/{locationId}/activity
     * @param locationId - ID of the location
     * @return a list of activities from a specific location and 200 status code if successful, 400 code otherwise
     */
    @Override
    public ResponseEntity<List<WorkoutActivity>> getActivitiesAtLocationById(@PathVariable("locationId") Integer locationId) {
        Location location = locRepository.findById(locationId);

        // check if resource exist
        if (location == null) {
            logger.warning("location not found on :: "+ locationId);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<WorkoutActivity> activities = activityRepository.findByLocID(locationId);
        return new ResponseEntity<>(activities, HttpStatus.OK);
    }


    /**
     * GET all locations from the collection.
     *
     * Request URI: /location
     * @return a list of all location documents
     */
    @Override
    public ResponseEntity<List<Location>> getAllLocationRecords() {
        List<Location> locations = locRepository.findAll();
        return new ResponseEntity<>(locations, HttpStatus.OK);
    }

    /**
     * GET a specific location by searching for its
     * location ID in the collection.
     *
     * Request URI: /location/{locationId}
     * @param locationId - ID of the location
     * @return a specific location document and 200 status code if it exists, 400 code otherwise
     */
    @Override
    public ResponseEntity<Location> getSingleLocationById(@PathVariable("locationId") Integer locationId) {
        Location location = locRepository.findById(locationId);

        // check if resource exist
        if (location == null) {
            logger.warning("location not found on :: "+ locationId);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(location, HttpStatus.OK);
    }

    /**
     * GET a specific location by searching
     * for location name in the collection.
     *
     * Note: GET method is already taken by "/location/{locationId}" so have
     * to add another keyword "name" in URI to do another search
     *
     * Request URI: /location/name/{locationName}
     * @param locationName - string name of location
     * @return a specific location and 200 status code if it exists, 400 code otherwise
     */
    @Override
    public ResponseEntity<Location> getSingleLocationByName(@PathVariable("locationName") String locationName) {
        Location location = locRepository.findByName(locationName);

        // check if resource exist
        if (location == null) {
            logger.warning("location not found on :: "+ locationName + " - check spelling of name and URI - make sure first letter is capitalized");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(location, HttpStatus.OK);
    }

    /**
     * PATCH a location document by searching for its location ID in the collection
     * then uses a map to tie in the key/value pairs to update the appropriate fields
     * in the resource.
     *
     * For example, if the client only wants to update the
     * id and name with {id: 1, name: "example"}, then it goes through
     * each fields in the object and finds the key (id and name) and update
     * the value associated with it (1 and "example").
     * Reference: https://stackoverflow.com/questions/45200142/spring-rest-partial-update-with-patch-method
     *
     * Request URI: /location/{locationId}
     * @param locationId - ID of the location
     * @param fieldsToUpdate - hashmap with key/value pairs, key -> field of object and value -> new value to update
     * @return the updated location and 200 status code if successful, 400 code otherwise
     */
    @Override
    public ResponseEntity<Location> patchSingleLocationById(@PathVariable("locationId") Integer locationId, @RequestBody Map<String, Object> fieldsToUpdate) {
        Location location = locRepository.findById(locationId);

        // check if resource exist
        if (location == null) {
            logger.warning("location not found on :: "+ locationId);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        fieldsToUpdate.forEach((key, value) -> {

            // uses reflection method to get field key on location and set it to the updated value
            Field field = ReflectionUtils.findField(Location.class, key);

            // have to set the fields to be accessible in order to update
            field.setAccessible(true);
            ReflectionUtils.setField(field, location, value);
        });

        locRepository.save(location);
        logger.info(location.toString());

        return new ResponseEntity<>(location, HttpStatus.OK);
    }

    /**
     * POST an activity with a given location ID. Method finds the location
     * by ID in the location repository and checks to see if both
     * the location and activity resource exists. Then it sets
     * the activity's locID with the given locationID and saves
     * the new activity to the activity collection.
     *
     * Request URI: /location/{locationId}/activity
     * @param locationId - ID of the location
     * @param activity - a new activity to add
     * @return the new activity object and 200 status code if successful, 400 code otherwise
     */
    @Override
    public ResponseEntity<WorkoutActivity> postActivityToLocationById(@PathVariable("locationId") Integer locationId,
                                                                      @RequestBody WorkoutActivity activity) {
        Location location = locRepository.findById(locationId);

        // check if location or activity resource exist
        if (location == null) {
            logger.warning("location not found on :: "+ locationId);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else if (activity.getActivity() == null) {
            logger.warning("activity type cannot be null");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        activity.setLocID(locationId);
        activityRepository.save(activity);
        return new ResponseEntity<>(activity, HttpStatus.OK);
    }

    /**
     * PUT a single location in the collection. Method finds the location
     * by ID then deletes the document and saves the updatedLocation in the collection.
     *
     * Request URI: /location/{locationId}
     * @param locationId - ID of the location
     * @param updatedLocation - another location object with updated values
     * @return the original location with new values and 200 status code if successful, 400 code otherwise
     */
    @Override
    public ResponseEntity<Location> putSingleLocationById(@PathVariable("locationId") Integer locationId, @RequestBody
            Location updatedLocation) {
        Location location = locRepository.findById(locationId);

        // check if resource exist
        if (location == null) {
            logger.warning("location not found on :: "+ locationId);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        locRepository.save(updatedLocation);
        locRepository.delete(location);
        return new ResponseEntity<>(updatedLocation, HttpStatus.OK);
    }

    /**
     * This method filters through the location collection
     * and grabs all the locations that has a name associated with it.
     * It then returns the list and displays a table in the location.html
     *
     * Request URI: /tracey/location
     * @param model - model attribute that calls on certain pieces of data in the view template
     * @return the call to access the location.html
     */
    @GetMapping("/tracey/location")
    public String showLocationsWithName(Model model) {
        List<Location> listLocations = locRepository.findAll();
        List<Location> listLocationsWithName = new ArrayList<>();

        // filter through list and add locations that has a name
        for (Location location : listLocations) {
            Optional<String> loc = Optional.ofNullable(location.getName());

            if (!loc.isEmpty()) {
                listLocationsWithName.add(location);
            }
        }

        model.addAttribute("listLocationsWithName", listLocationsWithName);
        return "location";
    }

}
