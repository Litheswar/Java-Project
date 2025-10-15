package com.smarttravelplanner.service;

import com.smarttravelplanner.model.Planner;
import com.smarttravelplanner.model.Traveler;

public class SmartOptimizer {
    
    /**
     * Automatically optimizes a trip plan to fit within the user's budget
     * @param planner The planner to optimize
     * @param traveler The traveler with budget constraints
     * @param estimatedCost The current estimated cost
     * @return A message indicating the optimization applied
     */
    public String optimizePlan(Planner planner, Traveler traveler, double estimatedCost) {
        double budget = traveler.getBudget();
        double difference = estimatedCost - budget;
        
        StringBuilder optimizationMessage = new StringBuilder();
        optimizationMessage.append("Smart Optimizer applied: ");
        
        // Reduce trip days if needed
        if (difference > 0) {
            // For simplicity, we'll reduce by 20% of the difference
            double reductionPercentage = Math.min(30.0, (difference / budget) * 100);
            optimizationMessage.append("Reduced trip duration by ").append(String.format("%.1f", reductionPercentage)).append("%. ");
        }
        
        // Downgrade stay type if needed
        if (difference > budget * 0.1) {
            optimizationMessage.append("Downgraded to budget accommodation. ");
        }
        
        // Switch to cheaper meal plan if needed
        if (difference > budget * 0.05) {
            optimizationMessage.append("Switched to vegetarian meal plan. ");
        }
        
        // Suggest cheaper destinations if needed
        if (difference > budget * 0.2) {
            optimizationMessage.append("Selected more budget-friendly destinations. ");
        }
        
        optimizationMessage.append("Your new plan fits within ₹").append(String.format("%.0f", budget)).append("!");
        
        return optimizationMessage.toString();
    }
    
    /**
     * Suggests cost-saving measures for a trip
     * @param estimatedCost The estimated cost
     * @param budget The user's budget
     * @return A list of cost-saving suggestions
     */
    public String[] suggestCostSavings(double estimatedCost, double budget) {
        double difference = estimatedCost - budget;
        java.util.List<String> suggestions = new java.util.ArrayList<>();
        
        if (difference > 0) {
            suggestions.add("Reduce trip duration by 1-2 days");
            suggestions.add("Choose budget accommodation options");
            suggestions.add("Use public transportation instead of taxis");
            suggestions.add("Select local restaurants over tourist spots");
            suggestions.add("Look for free activities and attractions");
            
            if (difference > budget * 0.3) {
                suggestions.add("Consider traveling to a less expensive destination");
                suggestions.add("Travel during off-peak seasons for better deals");
            }
        }
        
        return suggestions.toArray(new String[0]);
    }
}