package travelplanner;

/**
 * Custom exception for when no affordable destinations are available within budget.
 * Demonstrates Exception Handling.
 */
public class NoAffordableDestinationException extends Exception {
    private double budget;
    private double minimumRequired;

    public NoAffordableDestinationException(String message, double budget, double minimumRequired) {
        super(message);
        this.budget = budget;
        this.minimumRequired = minimumRequired;
    }

    public double getBudget() {
        return budget;
    }

    public double getMinimumRequired() {
        return minimumRequired;
    }

    public double getShortfall() {
        return minimumRequired - budget;
    }
}