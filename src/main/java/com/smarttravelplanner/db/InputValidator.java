package com.smarttravelplanner.db;

public class InputValidator {
    
    /**
     * Validates user age
     * @param age the age to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidAge(int age) {
        return age > 0 && age <= 120;
    }
    
    /**
     * Validates family count
     * @param familyCount the family count to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidFamilyCount(int familyCount) {
        return familyCount > 0 && familyCount <= 10;
    }
    
    /**
     * Validates budget
     * @param budget the budget to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidBudget(double budget) {
        return budget >= 0;
    }
    
    /**
     * Validates trip days
     * @param tripDays the trip days to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidTripDays(int tripDays) {
        return tripDays > 0 && tripDays <= 50;
    }
    
    /**
     * Validates meals per day
     * @param mealsPerDay the meals per day to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidMealsPerDay(int mealsPerDay) {
        return mealsPerDay > 0 && mealsPerDay <= 5;
    }
    
    /**
     * Validates that budget is sufficient for estimated cost
     * @param budget the user's budget
     * @param estimatedCost the estimated cost of the trip
     * @return true if budget is sufficient, false otherwise
     */
    public static boolean isBudgetSufficient(double budget, double estimatedCost) {
        return budget >= estimatedCost;
    }
}