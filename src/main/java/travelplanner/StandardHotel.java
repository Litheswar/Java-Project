package travelplanner;

/**
 * Standard hotel implementation.
 * Demonstrates Inheritance from Accommodation class.
 */
public class StandardHotel extends Accommodation {
    private static final double STANDARD_MULTIPLIER = 1.5;

    public StandardHotel(String name, double baseCostPerNight, int rating) {
        super(name, baseCostPerNight, rating);
    }

    /**
     * Calculates cost for standard hotel.
     * Demonstrates Polymorphism.
     */
    @Override
    public double calculateCost(int numberOfNights, int numberOfPeople) {
        return baseCostPerNight * numberOfNights * STANDARD_MULTIPLIER;
    }
}