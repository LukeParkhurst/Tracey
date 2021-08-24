package edu.northeastern.tracey;

public class clientFrontEndListener {

//    private String[] lastArgs;
//    private String controllerHandler;
//    private int queryPushFlag;
//    private String itemType;
//    private PointOfInterestController poiController;
//    private WorkoutActivityController workActController;
//    private WorkoutToPointController workPointController;
//    private PointOfInterestRepository repository;
//    private ActivityRepository repo;
//
//    // the args should be in this order: request, requestType, type, itemType, *other params to enter with their attribute
//    //      name in front of them, ie. "name","home"*
//    public clientFrontEndListener(PointOfInterestRepository repository ,ActivityRepository repo){
//
//        this.repository = repository;
//        this.repo = repo;
//        poiController = new PointOfInterestController(repository);
//        workActController = new WorkoutActivityController(repo);
//        workPointController = new WorkoutToPointController(repo);
//    }
//
//    // example of args: ["request","push","type","running","calories","102","steps","250",] everything after type is params
//    //  for either the query or the new item to push
//
//    public void parser(String[] args){
//
//        lastArgs = (args);
//        int iter = 0;
//
//        for (String arg : args){
//
//            if(arg.equals("type")){
//                String helper = args[iter+1];
//
//                if(helper.equals("location")){
//                    this.controllerHandler = "PointOfIntController";
//                } else{
//                    this.controllerHandler = "WorkoutActController";
//                }
//                itemType = helper;
//
//            } else if(arg.equals("request")){
//                String helper = args[iter+1];
//                switch (helper){
//                    case "query":
//                        queryPushFlag = 0;
//                    case "push":
//                        queryPushFlag = 1;
//                }
//            }
//            iter ++;
//        }
//        pushToController(queryPushFlag, itemType, lastArgs);
//    }
//
//
//    private void pushToController(int requestType, String type, String[] args){
//
//        // if requestType = 0, then query else if 1, then push
//        if (requestType == 0){
//            if (type.equals("location")){
//                poiController.queryRequest(args);
//            } else{
//                workActController.queryRequest(type, args);
//            }
//        } else {
//            if (type.equals("location")){
//                poiController.pushRequest(args);
//            } else{
//                workActController.pushRequest(type, args);
//            }
//        }
//    }
//
//    private void lastRequest(String[] args){
//        this.lastArgs = args;
//    }

}
