package com.smarttravelplanner.service;

import com.smarttravelplanner.model.Destination;

import java.util.ArrayList;
import java.util.List;

public class SuggestionService {
    private LocationService locationService;
    
    public SuggestionService(LocationService locationService) {
        this.locationService = locationService;
    }
    
    /**
     * Suggests alternate destinations based on budget
     * @param userBudget The user's budget
     * @return List of suggested destinations within 10% of the user's budget
     */
    public List<Destination> suggestDestinationsByBudget(double userBudget) {
        List<Destination> allDestinations = locationService.getAllDestinations();
        List<Destination> suggestions = new ArrayList<>();
        
        double lowerBound = userBudget * 0.9; // 10% below user's budget
        double upperBound = userBudget * 1.1; // 10% above user's budget
        
        for (Destination destination : allDestinations) {
            double baseCost = destination.getBaseCost();
            if (baseCost >= lowerBound && baseCost <= upperBound) {
                suggestions.add(destination);
            }
        }
        
        return suggestions;
    }
    
    /**
     * Suggests cost adjustments for a destination
     * @param destination The destination to suggest adjustments for
     * @param userBudget The user's budget
     * @return Formatted string with adjustment suggestions
     */
    public String suggestCostAdjustments(Destination destination, double userBudget) {
        double baseCost = destination.getBaseCost();
        StringBuilder suggestions = new StringBuilder();
        
        if (baseCost > userBudget) {
            double difference = baseCost - userBudget;
            suggestions.append("To fit within your budget, consider these adjustments:\n");
            
            // Suggest reducing travel mode from luxury to standard
            suggestions.append("- Choose standard travel mode instead of luxury (save ~30%)\n");
            
            // Suggest reducing duration
            int daysToReduce = (int) (difference / 100); // Rough estimate
            if (daysToReduce > 0) {
                suggestions.append("- Reduce trip duration by ").append(daysToReduce).append(" days\n");
            }
            
            // Suggest alternate accommodation
            suggestions.append("- Choose budget accommodation options\n");
        } else {
            double savings = userBudget - baseCost;
            suggestions.append("You have $").append(String.format("%.2f", savings)).append(" left in your budget.\n");
            suggestions.append("Consider these upgrades:\n");
            suggestions.append("- Upgrade to luxury travel mode\n");
            suggestions.append("- Extend your trip duration\n");
            suggestions.append("- Add premium experiences and activities\n");
        }
        
        return suggestions.toString();
    }
    
    /**
     * Suggests destinations by country
     * @param country The country to get destinations for
     * @return List of destinations in the specified country
     */
    public List<Destination> suggestDestinationsByCountry(String country) {
        return locationService.getDestinationsByCountry(country);
    }
    
    /**
     * Suggests the best value destinations (lowest cost per day)
     * @param durationDays The planned duration of the trip
     * @return List of best value destinations
     */
    public List<Destination> suggestBestValueDestinations(int durationDays) {
        List<Destination> allDestinations = locationService.getAllDestinations();
        List<Destination> suggestions = new ArrayList<>();
        
        // Sort by cost per day (base cost / duration)
        allDestinations.sort((d1, d2) -> {
            double costPerDay1 = d1.getBaseCost() / durationDays;
            double costPerDay2 = d2.getBaseCost() / durationDays;
            return Double.compare(costPerDay1, costPerDay2);
        });
        
        // Return top 3 best value destinations
        int count = Math.min(3, allDestinations.size());
        for (int i = 0; i < count; i++) {
            suggestions.add(allDestinations.get(i));
        }
        
        return suggestions;
    }
}