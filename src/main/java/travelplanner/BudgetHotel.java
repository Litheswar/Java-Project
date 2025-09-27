package travelplanner;

/**
 * Budget hotel implementation.
 * Demonstrates Inheritance from Accommodation class.
 */
public class BudgetHotel extends Accommodation {
    private static final double BUDGET_MULTIPLIER = 1.0;

    public BudgetHotel(String name, double baseCostPerNight, int rating) {
        super(name, baseCostPerNight, rating);
    }

    /**
     * Calculates cost for budget hotel.
     * Demonstrates Polymorphism.
     */
    @Override
    public double calculateCost(int numberOfNights, int numberOfPeople) {
        return baseCostPerNight * numberOfNights * numberOfPeople * BUDGET_MULTIPLIER;
    }
}