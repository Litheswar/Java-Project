package travelplanner;

/**
 * Enum representing different transport modes with their cost multipliers.
 * Demonstrates how different transport modes affect sustainability scores.
 */
public enum TransportMode {
    CAR(1.0, 0.3),         // High cost, low sustainability
    BUS(0.5, 0.7),         // Medium cost, medium sustainability
    TRAIN(0.6, 0.8),       // Medium cost, high sustainability
    FLIGHT(2.0, 0.4);      // High cost, low sustainability

    private final double costMultiplier;
    private final double sustainabilityScore;

    TransportMode(double costMultiplier, double sustainabilityScore) {
        this.costMultiplier = costMultiplier;
        this.sustainabilityScore = sustainabilityScore;
    }

    public double getCostMultiplier() {
        return costMultiplier;
    }

    public double getSustainabilityScore() {
        return sustainabilityScore;
    }
}