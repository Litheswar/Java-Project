package com.smarttravelplanner.db;

public class CO2Calculator {
    
    /**
     * Calculates estimated CO2 footprint based on travel mode and trip days
     * @param travelMode the travel mode (road, rail, air, mixed)
     * @param tripDays the number of trip days
     * @param familyCount the number of family members
     * @return the estimated CO2 footprint in kg
     */
    public static double calculateCO2Footprint(String travelMode, int tripDays, int familyCount) {
        double co2PerDayPerPerson;
        switch (travelMode.toLowerCase()) {
            case "road":
                co2PerDayPerPerson = 5.0; // kg CO2 per day per person
                break;
            case "rail":
                co2PerDayPerPerson = 2.0; // kg CO2 per day per person
                break;
            case "air":
                co2PerDayPerPerson = 15.0; // kg CO2 per day per person
                break;
            case "mixed":
                co2PerDayPerPerson = 7.0; // kg CO2 per day per person
                break;
            default:
                co2PerDayPerPerson = 7.0; // default mixed
        }
        return co2PerDayPerPerson * tripDays * familyCount;
    }
    
    /**
     * Gets sustainability tip based on CO2 footprint
     * @param co2Footprint the CO2 footprint
     * @return a sustainability tip
     */
    public static String getSustainabilityTip(double co2Footprint) {
        if (co2Footprint < 50) {
            return "Great job! Your travel plan has a low carbon footprint.";
        } else if (co2Footprint < 150) {
            return "Good effort! Consider using public transportation to reduce your carbon footprint.";
        } else {
            return "Consider choosing rail travel instead of air travel to significantly reduce your carbon footprint.";
        }
    }
}