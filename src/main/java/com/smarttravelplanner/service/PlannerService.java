package com.smarttravelplanner.service;

import com.smarttravelplanner.model.CityPlan;
import com.smarttravelplanner.model.Destination;
import com.smarttravelplanner.model.TourPlan;

import java.util.ArrayList;
import java.util.List;

public class PlannerService {
    
    // Default constructor
    public PlannerService() {
    }
    
    /**
     * Creates a city plan with the specified name and destination
     * @param planName The name of the plan
     * @param destination The destination for the plan
     * @return CityPlan object
     */
    public CityPlan createCityPlan(String planName, Destination destination) {
        CityPlan cityPlan = new CityPlan(planName);
        if (destination != null) {
            cityPlan.addDestination(destination);
        }
        return cityPlan;
    }
    
    /**
     * Creates a tour plan with the specified parameters
     * @param planName The name of the plan
     * @param destinations List of destinations for the tour
     * @param durationDays Duration of the tour in days
     * @param tourType Type of tour
     * @return TourPlan object
     */
    public TourPlan createTourPlan(String planName, List<Destination> destinations, int durationDays, String tourType) {
        TourPlan tourPlan = new TourPlan(planName, durationDays, tourType);
        if (destinations != null) {
            for (Destination dest : destinations) {
                tourPlan.addDestination(dest);
            }
        }
        return tourPlan;
    }
    
    /**
     * Validates if a plan is valid (has at least one destination)
     * @param plan The plan to validate
     * @return true if valid, false otherwise
     */
    public boolean validatePlan(CityPlan plan) {
        return plan != null && plan.getDestinations() != null && !plan.getDestinations().isEmpty();
    }
    
    /**
     * Validates if a plan is valid (has at least one destination)
     * @param plan The plan to validate
     * @return true if valid, false otherwise
     */
    public boolean validatePlan(TourPlan plan) {
        return plan != null && plan.getDestinations() != null && !plan.getDestinations().isEmpty();
    }
    
    /**
     * Gets route steps for a plan
     * @param plan The plan to get route steps for
     * @return List of route steps
     */
    public List<String> getRouteSteps(CityPlan plan) {
        List<String> steps = new ArrayList<>();
        if (plan != null && plan.getDestinations() != null) {
            for (Destination dest : plan.getDestinations()) {
                steps.add("Visit " + dest.getCity() + ", " + dest.getState() + ", " + dest.getCountry());
            }
        }
        return steps;
    }
    
    /**
     * Gets route steps for a plan
     * @param plan The plan to get route steps for
     * @return List of route steps
     */
    public List<String> getRouteSteps(TourPlan plan) {
        List<String> steps = new ArrayList<>();
        if (plan != null && plan.getDestinations() != null) {
            for (Destination dest : plan.getDestinations()) {
                steps.add("Visit " + dest.getCity() + ", " + dest.getState() + ", " + dest.getCountry());
            }
        }
        return steps;
    }
}