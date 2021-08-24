package edu.northeastern.tracey.utils;
import com.google.gson.JsonArray;

import edu.northeastern.tracey.repositories.ActivityRepository;
import edu.northeastern.tracey.repositories.LocationRepository;

/**
 * This is the file that will parse and upload bulk JSON data into the MongoDB repository.
 */

public class StorylineParser {

    private JsonArray mongo_docs_Locations;
    private JsonArray mongo_docs_Activities;
    private JsonArray mongo_docs_Acts_with_Locs;
    private final ActivityRepository repo;
    private final LocationRepository repository;

    StorylineParser(LocationRepository repository, ActivityRepository repo){
        mongo_docs_Locations = new JsonArray();
        mongo_docs_Activities = new JsonArray();
        mongo_docs_Acts_with_Locs = new JsonArray();
        this.repository = repository;
        this.repo = repo;
    }
/**
    public void parseStorylineData(String docs) throws IOException {

        JsonObject jObject; Gson gson = new Gson();

        try (Reader reader = new FileReader(docs)){
            jObject = gson.fromJson(reader, JsonObject.class);
        };

        with open (docs) as json_file:
        data = json.load(json_file)

        // initialize counter variables
        int i = 0, totalCount = 0, place = 0, move = 0, compound = 0, locNameCount = 0, transport = 0, cycling = 0,
                running = 0, walking = 0;
        ArrayList<Integer> locationList = new ArrayList<>();

        // parse through each day
        for (JsonObject date : data) {
            //look at each activity stored in the day
            for (JsonObject loggedActivity : date["segments"]) {
                totalCount += 1;
                //System.out.println(loggedActivity["type"]);

                if (loggedActivity.get("type").equals("place")) {
                    // counting unique locations based on id
                    if (loggedActivity["place"]["id"] not in locationList) {
                        locationList.add(loggedActivity["place"]["id"]);
                        // create a new MongoDB document dict

                        Json doc = {"id":loggedActivity["place"]["id"],
                                "lat":loggedActivity["place"]["location"]["lat"],
                                "lon":loggedActivity["place"]["location"]["lon"]}

                        if ("name" in loggedActivity[ "place"]){
                            doc["name"] = loggedActivity["place"]["name"];
                            locNameCount += 1;

                            // add the MongoDB document to the list
                            mongo_docs_Locations.add(doc);
                            System.out.println(doc);
                        }
                    }
                    // counting the total number of location documents
                    place += 1;

                    // if location has activities tied with it, create new activity objects with a locationID variable
                    if ("activities" in loggedActivity){
                        compound += 1;
                        int subActivityCount = 0;
                        int duration = 0, distance = 0, steps = 0, calories = 0;
                        String activity = "";

                        for (JsonObject subActivity : loggedActivity["activities"]) {
                            activity = subActivity["activity"];
                            duration += subActivity["duration"];
                            distance += subActivity["distance"];
                            steps += subActivity["steps"];
                            calories += subActivity["calories"];
                        }

                        Json doc = {"startTime":loggedActivity["startTime"],
                                "endTime":loggedActivity["endTime"],
                                "locID":loggedActivity["place"]["id"],
                                "activity":activity,
                                "duration":distance,
                                "distance":distance,
                                "steps":steps,
                                "calories":calories}
                        // add the MongoDB document to the list
                        mongo_docs_Acts_with_Locs.add(doc);
                        System.out.println(doc);
                    }
                } else if (loggedActivity["type"].equals("move")) {
                    move += 1;
                    for (JsonObject info : loggedActivity["activities"]) {
                        switch (info["activity"]){
                            case "transport":
                                transport += 1; activityBuilder(loggedActivity, info);
                            case "cycling":
                                cycling += 1; activityBuilder(loggedActivity, info);
                            case "running":
                                running += 1; activityBuilder(loggedActivity, info);
                            case "walking":
                                walking += 1; activityBuilder(loggedActivity, info);
                        }
                    }
                }
            }
        }

        // gather and print out results from StoryLine Json
        String activityResults = String.format("Number of transport documents: %d \n Number of cycling documents: %d " +
                "\n Number of running documents: %d \n Number of walking documents: %d \n", transport, cycling,
                running, walking);

        String storylineResults = String.format("Number of days in dataset: %d \n Total Number of Activities: %d \n " +
                "Locations: %d \n # of unique locations: %d \n Locations w/ Activities: %d \n Move Only Activities: " +
                "%d \n", i, totalCount, place, locationList.size(), compound, move);
        System.out.println(activityResults);
        System.out.println(storylineResults);

        // methods to send all ArrayList info right to the database and then clear the array list for another storyLine
        sendToDatabase();
        clearCache();
    }

    public void activityBuilder(JsonObject loggedActivity, JsonObject info){
        Json doc = {"startTime":loggedActivity["startTime"],
                "endTime":loggedActivity["endTime"],
                "activity":info["activity"],
                "duration":info["duration"],
                "distance":info["distance"]}
        // add the MongoDB document to the list
 git merge master        mongo_docs_Activities.add(doc);
        System.out.println(doc);

    } **/
/**
    public void sendToDatabase() throws FileNotFoundException {
        //The following code is to insert all unique locations saved onto the Places.Json, inserts them into the
        //    Location collection in the MongoDB
        try (Reader reader = new FileReader("src/main/java/edu/northeastern/tracey/Places.json")) {
            Gson gson = new Gson();
 Location[] places = gson.fromJson(reader, Location[].class);

            for (Location place : places) {
                repository.save(place);
                System.out.printf("Place: %d saved! \n", place.getId());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // The following code takes all activities in the Activity.json and inserts them into the WorkoutActivity Collection
        //    in the MongoDB
        try (Reader reader = new FileReader("src/main/java/edu/northeastern/tracey/Activity.json")) {
            Gson gson2 = new Gson();
            WorkoutActivity[] acts = gson2.fromJson(reader, WorkoutActivity[].class);

            for (WorkoutActivity act : acts) {
                repo.save(act);
                System.out.println("Document added");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // The following code takes all activities with location ID's attached to them from the ActivitiesWithLocations.Json
        //    and inserts them into the WorkoutActivity collection in the MongoDB
        try (Reader reader = new FileReader("src/main/java/edu/northeastern/tracey/ActivitiesWithLocations.json")) {
            Gson gson2 = new Gson();
            Walking[] acts = gson2.fromJson(reader, Walking[].class);

            for (Walking act : acts) {
                repo.save(act);
                System.out.println("Document added");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clearCache(){
        this.mongo_docs_Locations = new ArrayList<>();
        this.mongo_docs_Activities = new ArrayList<>();
        this.mongo_docs_Acts_with_Locs = new ArrayList<>();
    }
**/
}