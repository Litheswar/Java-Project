package com.smarttravelplanner.db;

public class ExpenseCalculator {
    
    /**
     * Calculates travel expense based on trip days and family count
     * @param baseCost the base cost of the destination
     * @param tripDays the number of trip days
     * @param familyCount the number of family members
     * @return the calculated travel expense
     */
    public static double calculateTravelExpense(double baseCost, int tripDays, int familyCount) {
        // Simple calculation: base cost + (base cost * 0.1 per day) + (base cost * 0.05 per family member)
        return baseCost + (baseCost * 0.1 * tripDays) + (baseCost * 0.05 * familyCount);
    }
    
    /**
     * Calculates food expense based on meals per day, trip days, and family count
     * @param mealsPerDay the number of meals per day
     * @param tripDays the number of trip days
     * @param familyCount the number of family members
     * @return the calculated food expense
     */
    public static double calculateFoodExpense(int mealsPerDay, int tripDays, int familyCount) {
        // Simple calculation: ₹300 per meal per person
        return mealsPerDay * tripDays * familyCount * 300.0;
    }
    
    /**
     * Calculates stay expense based on trip days and family count
     * @param tripDays the number of trip days
     * @param familyCount the number of family members
     * @param stayType the type of stay (budget, standard, premium)
     * @return the calculated stay expense
     */
    public static double calculateStayExpense(int tripDays, int familyCount, String stayType) {
        double costPerDayPerPerson;
        switch (stayType.toLowerCase()) {
            case "budget":
                costPerDayPerPerson = 500.0;
                break;
            case "standard":
                costPerDayPerPerson = 1000.0;
                break;
            case "premium":
                costPerDayPerPerson = 2000.0;
                break;
            default:
                costPerDayPerPerson = 1000.0;
        }
        return tripDays * familyCount * costPerDayPerPerson;
    }
    
    /**
     * Calculates total estimated cost
     * @param travelExpense the travel expense
     * @param foodExpense the food expense
     * @param stayExpense the stay expense
     * @return the total estimated cost
     */
    public static double calculateTotalEstimatedCost(double travelExpense, double foodExpense, double stayExpense) {
        // Add 10% for shopping and leisure
        double subtotal = travelExpense + foodExpense + stayExpense;
        return subtotal + (subtotal * 0.1);
    }
}