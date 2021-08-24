package edu.northeastern.tracey.SwaggerGeneratedCode.api;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.northeastern.tracey.model.WorkoutActivity;
import edu.northeastern.tracey.repositories.ActivityRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Activity API Controller: implements the Activity REST API and generates an Activity Repository
 * that is populated with WorkoutActivity events. Basic functionality is defined above each method.
 * Also included is the ability to print the sum, median, and average of calorie, distance, steps
 * and duration of all events. When run, the webapp will also show the last 10 activities performed.
 */
@Controller
public class ActivityApiController implements ActivityApi {

    private final ObjectMapper objectMapper;
    private final HttpServletRequest request;
    private final ActivityRepository activityRepository;
    private static final Logger logger = Logger.getLogger(ActivityApiController.class.getName());

    @org.springframework.beans.factory.annotation.Autowired
    public ActivityApiController(ObjectMapper objectMapper, HttpServletRequest request,
        ActivityRepository activityRepository) {
        this.objectMapper = objectMapper;
        this.request = request;
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

    @Override
    public ResponseEntity<WorkoutActivity> postActivityToRecord(@RequestBody WorkoutActivity activity) {
        System.out.println(activity.toString());

        if (activity.getActivity() == null) {
            logger.warning("activity cannot be empty");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            logger.info(activity.toString());
        }

        //activity.setId()? TODO: need to create setters for WorkOutActivity
        // do we want to provide url location to the resource?

        activityRepository.save(activity);
        return new ResponseEntity<>(activity, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<WorkoutActivity>> getActivityByStartTime(@PathVariable("startTime") String startTime) {

        // check if resource exist
        if (startTime == null) {
            logger.warning("Date not found on : " + startTime);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<WorkoutActivity> activities = activityRepository.findByStartTimeContaining(startTime);
        return new ResponseEntity<>(activities, HttpStatus.OK);
    }

    /**
     * NEW: HTML View
     * Create the /tracey/activities by returning the "view of the HTML file's code.
     *
     * @param model - nothing at this moment
     * @return - view of the /activity HTML
     *
     * Note: the getMapping /tracey ensures all requests from tracey.html call on this method
     */
    @GetMapping("/tracey/activity")
    public String tracey(@RequestParam(name="actName", required=false, defaultValue="walking") String actName,
                         @RequestParam(name="locId", required=false, defaultValue="999999999") String locId, Model model){

        model.addAttribute("actName", actName);
        model.addAttribute("locId", locId);

        List<WorkoutActivity> workoutList;
        if(locId.equals("999999999")){
            workoutList = activityRepository.findAllByActivity(actName);
        } else {
            workoutList = activityRepository.findAllByLocID(Integer.parseInt(locId));
        }

        int listSize = workoutList.size();
        HashMap<String, Float> sums = sum(workoutList);
        List<WorkoutActivity> listActivitiesWithName = lastActivities(workoutList, 10);
        HashMap<String, Float> medians = median(workoutList);

        float totalCalSum = sums.get("cals"); float totalCalAvg = totalCalSum/listSize;
        float totalDistSum = sums.get("dis"); float totalDistAvg = totalDistSum/listSize;
        float totalDurSum = sums.get("dur"); float totalDurAvg = totalDurSum/listSize;
        float totalStepSum = sums.get("steps"); float totalStepAvg = totalStepSum/listSize;

        float medCals = medians.get("cals"); float medDur = medians.get("dur");
        float medDis = medians.get("dis"); float medSteps = medians.get("steps");

        model.addAttribute("listActivitiesWithName", listActivitiesWithName);

        model.addAttribute("totalCalSum", totalCalSum);
        model.addAttribute("totalDistSum", totalDistSum);
        model.addAttribute("totalDurSum", totalDurSum);
        model.addAttribute("totalStepSum", totalStepSum);
        model.addAttribute("totalCalAvg", totalCalAvg);
        model.addAttribute("totalDistAvg", totalDistAvg);
        model.addAttribute("totalDurAvg", totalDurAvg);
        model.addAttribute("totalStepAvg", totalStepAvg);
        model.addAttribute("medCals", medCals);
        model.addAttribute("medDis", medDis);
        model.addAttribute("medDur", medDur);
        model.addAttribute("medSteps", medSteps);

        return "traceyActivities.html"; // view webpage named traceyActivities.html
    }

    /**
     * function to return the sums of each queried activities list in a hashmap
     * @param list list of workout activities to sum
     * @return HashMap of sums tied to String keys
     */
    public HashMap<String, Float> sum(List<WorkoutActivity> list){
        HashMap<String, Float> sums = new HashMap<String, Float>();
        float totalCalsSum = 0, totalDistSum = 0, totalDurSum = 0, totalStepsSum = 0;
        for (WorkoutActivity act : list){
            totalCalsSum += act.getCalories();
            totalDistSum += act.getDistance();
            totalDurSum += act.getDuration();
            totalStepsSum += act.getSteps();
        }

        sums.put("cals", totalCalsSum);
        sums.put("dur", totalDistSum);
        sums.put("dis", totalDurSum);
        sums.put("steps", totalStepsSum);

        return sums;
    }

    /**
     * function to return the last x number of activities finished
     * @param list list of workout activities queried
     * @param x integer of the last number of activities wanted
     * @return list of workout activities that is x from the last activity
     */
    public List<WorkoutActivity> lastActivities(List<WorkoutActivity> list, int x){
        int listSize = list.size();
        return list.subList(listSize-x, listSize);
    }

    /**
     * function to find and return the medians of the workout list queried
     * @param list list of workout activities
     * @return HashMap of floats that are the median values of each category, tied to String keys
     */
    public HashMap<String, Float> median(List<WorkoutActivity> list){
        int listSize = list.size();
        int[] medCals = new int[listSize]; int[] medDur = new int[listSize];
        int[] medDis = new int[listSize]; int[] medSteps = new int[listSize];
        int mid;
        if(listSize%2 == 0){
            mid = listSize/2;
        } else
            mid = (listSize/2) - 1;

        HashMap<String, Float> medians = new HashMap<String, Float>();

        Iterator<WorkoutActivity> helper = list.iterator();
        for (int i = 0; i < listSize; i++){
            if(helper.hasNext()){
                WorkoutActivity act = helper.next();
                medCals[i] = act.getCalories();
                medDur[i] = act.getDuration();
                medDis[i] = act.getDistance();
                medSteps[i] = act.getSteps();
            }
        }

        Arrays.sort(medCals); Arrays.sort(medDur); Arrays.sort(medDis); Arrays.sort(medSteps);

        float medianCals = medCals[mid]; float medianDur = medDur[mid];
        float medianDis = medDis[mid]; float medianSteps = medSteps[mid];

        medians.put("cals", medianCals); medians.put("dur", medianDur);
        medians.put("dis", medianDis); medians.put("steps", medianSteps);

        return medians;
    }

    /**
     * This method filters through the activity collection
     * and grabs all the calories burned
     * It then returns the charted values and displays a table in the .html
     *
     * Request URI: /tracey/location
     * @param model - model attribute that calls on certain pieces of data in the view template
     * @return the call to access the location.html
     */
    @GetMapping("/tracey/activity2")
    public String showCaloriesBurnedChart(Model model) throws ParseException {
        List<WorkoutActivity> listActivities = activityRepository.findAll();
        List<String> dateData = new ArrayList<>();
        List<String> dateval = new ArrayList<>();

        // initialize hashmap for each activity type (omitting transportation since it doesn't expend calories)
        // with activity/integer types as key/value pairs
        Map<String, Integer> walkData = new LinkedHashMap<>();
        Map<String, Integer> runData = new LinkedHashMap<>();
        Map<String, Integer> cycData = new LinkedHashMap<>();

//        String date = "20140125"; // jan 25 2014 - find activities that starts with this string in the startTime field
//        String date = "2014"; // jan 2014 - will populate more (all 3 activity types); about 240 data points

        // filter through collection and add each activity type starting with the date
        // it then adds the activity object with calories as its value in the hashmap
        for (WorkoutActivity activity : listActivities) {
            Optional<String> cal = Optional.of(activity.getCalories().toString());
            if (!cal.isEmpty()) {
                String start = activity.getStartTime();
                String[] sts = start.split("T");

                //20130216T150950-0800
                String ts = sts[0] + " " + sts[1];

                SimpleDateFormat outputFormat = new SimpleDateFormat("MM/yyyy");
                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyyMMdd HHmmssZ");
                outputFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                inputFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

                Date d = inputFormat.parse(ts);
                String outputText = outputFormat.format(d);

                if (!walkData.containsKey(outputText) || !runData.containsKey(outputText) || !cycData.containsKey(outputText)) {

                    if (activity.getActivity().equals("walking")) {
                        walkData.put(outputText, activity.getCalories());
                        runData.put(outputText, 0);
                        cycData.put(outputText, 0);
                    }

                    if (activity.getActivity().equals("running")) {
                        runData.put(outputText, activity.getCalories());
                        walkData.put(outputText, 0);
                        cycData.put(outputText, 0);
                    }

                    if (activity.getActivity().equals("cycling")) {
                        cycData.put(outputText, activity.getCalories());
                        walkData.put(outputText, 0);
                        runData.put(outputText, 0);
                    }
                    dateData.add(outputText);
                    dateval.add(sts[0]);
                }
                else {
                    if (activity.getActivity().equals("walking")) {
                        walkData.put(outputText, walkData.get(outputText) + activity.getCalories());
                    }

                    if (activity.getActivity().equals("running")) {
                        runData.put(outputText, runData.get(outputText) + activity.getCalories());
                    }

                    if (activity.getActivity().equals("cycling")) {
                        cycData.put(outputText, cycData.get(outputText) + activity.getCalories());
                    }
                }
            }
        }

        // adding attribute for each data that we will be using
        model.addAttribute("walkData", walkData.values()); // pulls out all calorie values for walking
        model.addAttribute("runData", runData.values()); // pulls out all calorie values for running
        model.addAttribute("cycData", cycData.values()); // pulls out all calorie values for cycling
        model.addAttribute("dateData", walkData.keySet()); // display dates
        return "calorieChart";
    }
}
