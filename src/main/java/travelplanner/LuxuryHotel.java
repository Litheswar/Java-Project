package travelplanner;

/**
 * Luxury hotel implementation.
 * Demonstrates Inheritance from Accommodation class.
 */
public class LuxuryHotel extends Accommodation {
    private static final double LUXURY_MULTIPLIER = 2.5;

    public LuxuryHotel(String name, double baseCostPerNight, int rating) {
        super(name, baseCostPerNight, rating);
    }

    /**
     * Calculates cost for luxury hotel.
     * Demonstrates Polymorphism.
     */
    @Override
    public double calculateCost(int numberOfNights, int numberOfPeople) {
        return baseCostPerNight * numberOfNights * numberOfPeople * LUXURY_MULTIPLIER;
    }
}