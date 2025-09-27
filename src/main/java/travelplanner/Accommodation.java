package travelplanner;

/**
 * Abstract class representing accommodation options.
 * Demonstrates Abstraction with abstract methods.
 */
public abstract class Accommodation {
    protected String name;
    protected double baseCostPerNight;
    protected int rating; // 1-5 stars

    public Accommodation(String name, double baseCostPerNight, int rating) {
        this.name = name;
        this.baseCostPerNight = baseCostPerNight;
        this.rating = rating;
    }

    // Getters
    public String getName() {
        return name;
    }

    public double getBaseCostPerNight() {
        return baseCostPerNight;
    }

    public int getRating() {
        return rating;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setBaseCostPerNight(double baseCostPerNight) {
        this.baseCostPerNight = baseCostPerNight;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    /**
     * Abstract method to calculate accommodation cost.
     * Must be implemented by subclasses.
     * Demonstrates Abstraction.
     */
    public abstract double calculateCost(int numberOfNights, int numberOfPeople);

    @Override
    public String toString() {
        return "Accommodation{" +
                "name='" + name + '\'' +
                ", baseCostPerNight=" + baseCostPerNight +
                ", rating=" + rating +
                '}';
    }
}