package travelplanner;

import java.util.List;

/**
 * Manages cost calculations for trips.
 * Demonstrates Polymorphism with different accommodation types.
 */
public class CostManager {
    private static final double FOOD_COST_PER_PERSON_PER_DAY = 30.0;
    private static final double SHOPPING_BUDGET_PER_PERSON = 50.0;
    private static final double EMERGENCY_BUFFER_PERCENTAGE = 0.15;

    /**
     * Calculates the total estimated cost for a trip.
     * Demonstrates Polymorphism with accommodation cost calculation.
     */
    public double calculateTotalCost(Location destination, List<Person> familyMembers, 
                                   Accommodation accommodation, TransportMode transportMode,
                                   int numberOfNights) throws InsufficientFundsException {
        double totalCost = 0.0;

        // Calculate accommodation cost
        totalCost += accommodation.calculateCost(numberOfNights, familyMembers.size());

        // Calculate food cost
        totalCost += calculateFoodCost(familyMembers, numberOfNights);

        // Calculate transport cost
        totalCost += calculateTransportCost(destination, familyMembers, transportMode);

        // Calculate shopping budget
        totalCost += calculateShoppingCost(familyMembers);

        // Add emergency buffer
        totalCost += totalCost * EMERGENCY_BUFFER_PERCENTAGE;

        return totalCost;
    }

    /**
     * Calculates food cost based on family members and trip duration.
     */
    private double calculateFoodCost(List<Person> familyMembers, int numberOfNights) {
        double totalFoodCost = 0.0;
        for (Person person : familyMembers) {
            totalFoodCost += FOOD_COST_PER_PERSON_PER_DAY * numberOfNights * person.getCostMultiplier();
        }
        return totalFoodCost;
    }

    /**
     * Calculates transport cost based on destination and transport mode.
     * Demonstrates Polymorphism with transport mode cost calculation.
     */
    private double calculateTransportCost(Location destination, List<Person> familyMembers, 
                                        TransportMode transportMode) {
        // Base cost derived from destination average cost
        double baseCost = destination.getAverageCostPerDay() * 2; // Round trip approximation
        
        // Apply transport mode multiplier
        double transportCost = baseCost * transportMode.getCostMultiplier();
        
        // Apply person multipliers
        double totalPersonMultiplier = 0.0;
        for (Person person : familyMembers) {
            totalPersonMultiplier += person.getCostMultiplier();
        }
        
        return transportCost * totalPersonMultiplier;
    }

    /**
     * Calculates shopping budget based on family members.
     */
    private double calculateShoppingCost(List<Person> familyMembers) {
        double totalShoppingCost = 0.0;
        for (Person person : familyMembers) {
            totalShoppingCost += SHOPPING_BUDGET_PER_PERSON * person.getCostMultiplier();
        }
        return totalShoppingCost;
    }

    /**
     * Suggests cost-saving alternatives if budget is insufficient.
     */
    public String suggestCostSavingAlternatives(double availableBudget, double requiredBudget) {
        double shortfall = requiredBudget - availableBudget;
        StringBuilder suggestions = new StringBuilder();
        
        suggestions.append("Budget shortfall of $").append(String.format("%.2f", shortfall)).append(". Suggestions:\n");
        suggestions.append("1. Consider a budget hotel instead of luxury accommodation\n");
        suggestions.append("2. Choose public transport (bus/train) instead of flights/cars\n");
        suggestions.append("3. Reduce trip duration by 1-2 days\n");
        suggestions.append("4. Limit shopping expenses\n");
        suggestions.append("5. Pack more meals instead of dining out\n");
        
        return suggestions.toString();
    }
    
    /**
     * Suggests cost-saving alternatives if budget is insufficient, with country and state information.
     */
    public String suggestCostSavingAlternatives(double availableBudget, double requiredBudget, Country country, State state) {
        double shortfall = requiredBudget - availableBudget;
        StringBuilder suggestions = new StringBuilder();
        
        suggestions.append("Budget shortfall of $").append(String.format("%.2f", shortfall)).append(". Suggestions:\n");
        suggestions.append("1. Consider a budget hotel instead of luxury accommodation\n");
        suggestions.append("2. Choose public transport (bus/train) instead of flights/cars\n");
        suggestions.append("3. Reduce trip duration by 1-2 days\n");
        suggestions.append("4. Limit shopping expenses\n");
        suggestions.append("5. Pack more meals instead of dining out\n");
        suggestions.append("6. Consider alternative destinations in ").append(state.getName()).append(", ").append(country.getName()).append("\n");
        
        return suggestions.toString();
    }

    /**
     * Calculates sustainability score based on transport mode and other factors.
     */
    public double calculateSustainabilityScore(TransportMode transportMode, Accommodation accommodation) {
        // Base score from transport mode
        double score = transportMode.getSustainabilityScore();
        
        // Adjust based on accommodation type (eco-friendliness)
        if (accommodation instanceof BudgetHotel) {
            score += 0.1; // Budget hotels often have lower environmental impact
        } else if (accommodation instanceof LuxuryHotel) {
            score -= 0.1; // Luxury hotels may have higher environmental impact
        }
        
        // Ensure score is between 0 and 1
        return Math.max(0.0, Math.min(1.0, score));
    }
}