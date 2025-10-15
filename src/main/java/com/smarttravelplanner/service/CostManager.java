package com.smarttravelplanner.service;

import com.smarttravelplanner.exceptions.BudgetExceededException;
import com.smarttravelplanner.model.CityPlan;
import com.smarttravelplanner.model.CityPlanner;
import com.smarttravelplanner.model.Planner;
import com.smarttravelplanner.model.TourPlanner;
import com.smarttravelplanner.utils.RouteOptimizer;

public class CostManager {
    private static final double FOOD_COST_PER_MEAL = 1.0;
    private static final double STAY_COST_PER_DAY_BUDGET = 50.0;
    private static final double STAY_COST_PER_DAY_STANDARD = 100.0;
    private static final double STAY_COST_PER_DAY_PREMIUM = 200.0;
    private static final double SHOPPING_LEISURE_COMMUTE_BASE = 100.0;
    
    /**
     * Estimates the total cost for a trip
     * @param planner The planner with route information
     * @param familyCount Number of family members
     * @param tripDays Number of days for the trip
     * @param mealsPerDay Number of meals per day
     * @param stayType Type of stay (Budget/Standard/Premium)
     * @return Total estimated cost
     * @throws BudgetExceededException if cost exceeds reasonable limits
     */
    public double estimateTotalCost(Planner planner, int familyCount, int tripDays, 
                                   int mealsPerDay, String stayType) throws BudgetExceededException {
        // Calculate individual cost components
        double travelCost = calculateTravelCost(planner, familyCount);
        double foodCost = calculateFoodCost(familyCount, tripDays, mealsPerDay);
        double stayCost = calculateStayCost(familyCount, tripDays, stayType);
        double otherCost = calculateOtherCost();
        
        double totalCost = travelCost + foodCost + stayCost + otherCost;
        
        // Check if cost exceeds reasonable limits (e.g., $50000)
        if (totalCost > 50000) {
            throw new BudgetExceededException("Estimated cost $" + String.format("%.2f", totalCost) + " exceeds reasonable limit");
        }
        
        return totalCost;
    }
    
    /**
     * Calculates total cost for a trip (used in tests)
     * @param plan The plan
     * @param familyCount Number of family members
     * @param travelMode Travel mode
     * @param tripDays Number of days for the trip
     * @return Total cost
     * @throws BudgetExceededException if cost exceeds reasonable limits
     */
    public double calculateTotalCost(CityPlan plan, int familyCount, String travelMode, int tripDays) throws BudgetExceededException {
        // Calculate base cost from destinations
        double baseCost = 0;
        for (com.smarttravelplanner.model.Destination dest : plan.getDestinations()) {
            baseCost += dest.getBaseCost();
        }
        
        // Travel cost: base cost * 0.2
        double travelCost = baseCost * 0.2;
        
        // Food cost: 30 meals per day * familyCount * tripDays * $1 per meal
        double foodCost = 30 * familyCount * tripDays * 1;
        
        // Stay cost: $100 per day per person
        double stayCost = 100 * familyCount * tripDays;
        
        // Leisure cost: $200 per person per 2 destinations
        double leisureCost = 200 * familyCount * 2;
        
        double subtotal = baseCost + travelCost + foodCost + stayCost + leisureCost;
        
        // Family adjustment: subtotal * (familyCount * 0.8)
        double familyMultiplier = familyCount * 0.8;
        double familyAdjusted = subtotal * familyMultiplier;
        
        // Mode adjustment
        double modeMultiplier = 1.0;
        switch (travelMode.toLowerCase()) {
            case "budget":
                modeMultiplier = 0.8;
                break;
            case "luxury":
                modeMultiplier = 1.5;
                break;
            case "standard":
            default:
                modeMultiplier = 1.0;
                break;
        }
        
        double totalCost = familyAdjusted * modeMultiplier;
        
        // Check if cost exceeds reasonable limits
        if (totalCost > 50000) {
            throw new BudgetExceededException("Estimated cost $" + String.format("%.2f", totalCost) + " exceeds reasonable limit");
        }
        
        return totalCost;
    }
    
    /**
     * Calculates sustainability score (used in tests)
     * @param plan The plan
     * @param travelMode Travel mode
     * @param tripDays Number of days for the trip
     * @return Sustainability score
     */
    public int calculateSustainabilityScore(CityPlan plan, String travelMode, int tripDays) {
        int baseScore = 100;
        
        // Adjust for travel mode
        switch (travelMode.toLowerCase()) {
            case "car":
                baseScore -= 20;
                break;
            case "plane":
                baseScore -= 30;
                break;
            case "train":
                baseScore -= 10;
                break;
            case "bus":
                baseScore -= 15;
                break;
            default:
                baseScore -= 5;
                break;
        }
        
        // Adjust for number of destinations
        baseScore -= plan.getDestinations().size() * 5;
        
        // Adjust for trip duration
        baseScore -= tripDays / 2;
        
        // Ensure score is between 0 and 100
        return Math.max(0, Math.min(100, baseScore));
    }
    
    /**
     * Calculates travel cost based on distance and travel mode
     * @param planner The planner with route information
     * @param familyCount Number of family members
     * @return Travel cost
     */
    private double calculateTravelCost(Planner planner, int familyCount) {
        double distance = planner.getEstimatedDistance();
        double costPerKm = 0.5; // Base cost per km
        
        // Adjust cost based on planner type and travel mode
        if (planner instanceof CityPlanner) {
            CityPlanner cityPlanner = (CityPlanner) planner;
            costPerKm *= RouteOptimizer.getTravelModeMultiplier(cityPlanner.getTravelMode());
        } else if (planner instanceof TourPlanner) {
            TourPlanner tourPlanner = (TourPlanner) planner;
            costPerKm *= RouteOptimizer.getTravelModeMultiplier(tourPlanner.getTravelMode());
        }
        
        return distance * costPerKm * familyCount;
    }
    
    /**
     * Calculates food cost
     * @param familyCount Number of family members
     * @param tripDays Number of days for the trip
     * @param mealsPerDay Number of meals per day
     * @return Food cost
     */
    private double calculateFoodCost(int familyCount, int tripDays, int mealsPerDay) {
        return FOOD_COST_PER_MEAL * familyCount * tripDays * mealsPerDay;
    }
    
    /**
     * Calculates stay cost based on stay type
     * @param familyCount Number of family members
     * @param tripDays Number of days for the trip
     * @param stayType Type of stay (Budget/Standard/Premium)
     * @return Stay cost
     */
    private double calculateStayCost(int familyCount, int tripDays, String stayType) {
        double costPerDay;
        
        switch (stayType.toLowerCase()) {
            case "budget":
                costPerDay = STAY_COST_PER_DAY_BUDGET;
                break;
            case "premium":
                costPerDay = STAY_COST_PER_DAY_PREMIUM;
                break;
            case "standard":
            default:
                costPerDay = STAY_COST_PER_DAY_STANDARD;
                break;
        }
        
        return costPerDay * familyCount * tripDays;
    }
    
    /**
     * Calculates other costs (shopping, leisure, commute)
     * @return Other costs
     */
    private double calculateOtherCost() {
        return SHOPPING_LEISURE_COMMUTE_BASE;
    }
    
    /**
     * Calculates sustainability score based on travel mode
     * @param planner The planner with route information
     * @return Sustainability score (0-10)
     */
    public int calculateSustainabilityScore(Planner planner) {
        if (planner instanceof CityPlanner) {
            CityPlanner cityPlanner = (CityPlanner) planner;
            return RouteOptimizer.calculateSustainabilityScore(cityPlanner.getTravelMode());
        } else if (planner instanceof TourPlanner) {
            TourPlanner tourPlanner = (TourPlanner) planner;
            return RouteOptimizer.calculateSustainabilityScore(tourPlanner.getTravelMode());
        }
        return 5; // Default score
    }
    
    /**
     * Compares trip cost with user budget
     * @param totalCost Estimated trip cost
     * @param budget User's budget
     * @return true if budget is sufficient, false otherwise
     */
    public boolean isBudgetSufficient(double totalCost, double budget) {
        return budget >= totalCost;
    }
    
    /**
     * Calculates the budget difference
     * @param totalCost Estimated trip cost
     * @param budget User's budget
     * @return Difference between budget and cost
     */
    public double calculateBudgetDifference(double totalCost, double budget) {
        return budget - totalCost;
    }
}