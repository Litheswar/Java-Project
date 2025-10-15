package com.smarttravelplanner.service;

import java.util.ArrayList;
import java.util.List;

public class SmartAdvisor {
    
    /**
     * Provides suggestions when trip cost exceeds budget
     * @param budgetDifference The difference between budget and cost (negative if cost > budget)
     * @return List of suggestions
     */
    public List<String> suggestCostReduction(double budgetDifference) {
        List<String> suggestions = new ArrayList<>();
        
        if (budgetDifference < 0) {
            double excessAmount = Math.abs(budgetDifference);
            suggestions.add("Trip cost exceeds your budget by $" + String.format("%.2f", excessAmount));
            
            // Suggest ways to reduce cost
            if (excessAmount > 1000) {
                suggestions.add("Consider reducing trip duration");
                suggestions.add("Choose budget accommodation options");
            }
            
            if (excessAmount > 500) {
                suggestions.add("Switch to a more economical travel mode");
                suggestions.add("Reduce the number of destinations");
            }
            
            suggestions.add("Look for deals and discounts on travel and accommodation");
        }
        
        return suggestions;
    }
    
    /**
     * Provides suggestions when trip cost is less than budget
     * @param budgetDifference The difference between budget and cost (positive if cost < budget)
     * @return List of suggestions
     */
    public List<String> suggestEnhancements(double budgetDifference) {
        List<String> suggestions = new ArrayList<>();
        
        if (budgetDifference > 0) {
            suggestions.add("You have $" + String.format("%.2f", budgetDifference) + " left in your budget!");
            
            // Suggest ways to enhance the trip
            if (budgetDifference > 1000) {
                suggestions.add("Consider upgrading to premium accommodation");
                suggestions.add("Add premium experiences and activities");
            }
            
            if (budgetDifference > 500) {
                suggestions.add("Extend your trip duration");
                suggestions.add("Add more destinations to your itinerary");
            }
            
            suggestions.add("Treat yourself to fine dining experiences");
            suggestions.add("Purchase travel insurance for peace of mind");
        }
        
        return suggestions;
    }
    
    /**
     * Provides general travel tips
     * @return List of travel tips
     */
    public List<String> getTravelTips() {
        List<String> tips = new ArrayList<>();
        tips.add("Pack light to avoid excess baggage fees");
        tips.add("Book flights and accommodation in advance for better deals");
        tips.add("Research local customs and etiquette before traveling");
        tips.add("Keep important documents in a secure place");
        tips.add("Stay hydrated and get enough rest during your trip");
        return tips;
    }
    
    /**
     * Provides destination recommendations based on budget
     * @param budget User's budget
     * @return List of destination recommendations
     */
    public List<String> recommendDestinations(double budget) {
        List<String> recommendations = new ArrayList<>();
        
        if (budget < 2000) {
            recommendations.add("Consider domestic travel options");
            recommendations.add("Look for off-season deals");
        } else if (budget >= 2000 && budget < 5000) {
            recommendations.add("Southeast Asia offers great value for money");
            recommendations.add("Eastern Europe provides rich cultural experiences at reasonable costs");
        } else if (budget >= 5000 && budget < 10000) {
            recommendations.add("Western Europe and Japan offer excellent travel experiences");
            recommendations.add("Consider guided tours for hassle-free travel");
        } else {
            recommendations.add("Premium destinations like Switzerland and New Zealand await you");
            recommendations.add("Consider luxury cruises for a unique experience");
        }
        
        return recommendations;
    }
}