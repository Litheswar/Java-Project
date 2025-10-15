package com.smarttravelplanner.model;

public class PlannerFactory {
    
    /**
     * Creates a planner based on the plan type
     * @param planType The type of plan ("city" or "tour")
     * @return Planner object (either CityPlanner or TourPlanner)
     */
    public static Planner createPlanner(String planType) {
        if (planType == null) {
            return null;
        }
        
        switch (planType.toLowerCase()) {
            case "city":
                return new CityPlanner();
            case "tour":
                return new TourPlanner();
            default:
                return null;
        }
    }
    
    /**
     * Creates a city planner with specific parameters
     * @param destination The destination city
     * @param travelMode The travel mode
     * @return CityPlanner object
     */
    public static CityPlanner createCityPlanner(String destination, String travelMode) {
        return new CityPlanner(destination, travelMode);
    }
    
    /**
     * Creates a tour planner with specific parameters
     * @param destinations The list of destinations
     * @param travelMode The travel mode
     * @return TourPlanner object
     */
    public static TourPlanner createTourPlanner(String[] destinations, String travelMode) {
        TourPlanner tourPlanner = new TourPlanner();
        tourPlanner.setTravelMode(travelMode);
        
        for (String destination : destinations) {
            tourPlanner.addDestination(destination);
        }
        
        return tourPlanner;
    }
}