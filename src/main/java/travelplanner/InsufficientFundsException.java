package travelplanner;

/**
 * Custom exception for insufficient budget scenarios.
 * Demonstrates Exception Handling.
 */
public class InsufficientFundsException extends Exception {
    private double availableFunds;
    private double requiredFunds;

    public InsufficientFundsException(String message, double availableFunds, double requiredFunds) {
        super(message);
        this.availableFunds = availableFunds;
        this.requiredFunds = requiredFunds;
    }

    public double getAvailableFunds() {
        return availableFunds;
    }

    public double getRequiredFunds() {
        return requiredFunds;
    }

    public double getShortfall() {
        return requiredFunds - availableFunds;
    }
}