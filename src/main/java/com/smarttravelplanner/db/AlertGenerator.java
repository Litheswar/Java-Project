package com.smarttravelplanner.db;

import java.util.ArrayList;
import java.util.List;

public class AlertGenerator {
    
    /**
     * Generates alerts for meal selections that exceed typical limits
     * @param mealsPerDay the number of meals per day
     * @return a list of alerts
     */
    public static List<String> generateMealAlerts(int mealsPerDay) {
        List<String> alerts = new ArrayList<>();
        if (mealsPerDay > 3) {
            alerts.add("High meal frequency detected (" + mealsPerDay + " meals/day). Consider reducing to save costs.");
        }
        return alerts;
    }
    
    /**
     * Generates alerts for stay selections that exceed typical limits
     * @param stayType the type of stay
     * @param tripDays the number of trip days
     * @return a list of alerts
     */
    public static List<String> generateStayAlerts(String stayType, int tripDays) {
        List<String> alerts = new ArrayList<>();
        if ("premium".equalsIgnoreCase(stayType) && tripDays > 7) {
            alerts.add("Premium stay for extended period (" + tripDays + " days) may exceed typical budget.");
        }
        return alerts;
    }
    
    /**
     * Generates alerts for travel selections that exceed typical limits
     * @param travelMode the travel mode
     * @param tripDays the number of trip days
     * @return a list of alerts
     */
    public static List<String> generateTravelAlerts(String travelMode, int tripDays) {
        List<String> alerts = new ArrayList<>();
        if ("air".equalsIgnoreCase(travelMode) && tripDays < 3) {
            alerts.add("Air travel for short trip (" + tripDays + " days) may not be cost-effective.");
        }
        return alerts;
    }
    
    /**
     * Generates all alerts for a trip configuration
     * @param mealsPerDay the number of meals per day
     * @param stayType the type of stay
     * @param travelMode the travel mode
     * @param tripDays the number of trip days
     * @return a list of all alerts
     */
    public static List<String> generateAllAlerts(int mealsPerDay, String stayType, String travelMode, int tripDays) {
        List<String> allAlerts = new ArrayList<>();
        allAlerts.addAll(generateMealAlerts(mealsPerDay));
        allAlerts.addAll(generateStayAlerts(stayType, tripDays));
        allAlerts.addAll(generateTravelAlerts(travelMode, tripDays));
        return allAlerts;
    }
}