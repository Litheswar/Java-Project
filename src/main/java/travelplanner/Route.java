package travelplanner;

/**
 * Represents a route within a state/region with distance, time, and cost information.
 * Demonstrates Encapsulation with private fields and public getters/setters.
 */
public class Route {
    private String name;
    private String fromLocation;
    private String toLocation;
    private double distance; // in kilometers
    private double estimatedTime; // in hours
    private double baseCost;

    // Default constructor
    public Route() {
    }

    // Parameterized constructor
    public Route(String name, String fromLocation, String toLocation, double distance, 
                double estimatedTime, double baseCost) {
        this.name = name;
        this.fromLocation = fromLocation;
        this.toLocation = toLocation;
        this.distance = distance;
        this.estimatedTime = estimatedTime;
        this.baseCost = baseCost;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getFromLocation() {
        return fromLocation;
    }

    public String getToLocation() {
        return toLocation;
    }

    public double getDistance() {
        return distance;
    }

    public double getEstimatedTime() {
        return estimatedTime;
    }

    public double getBaseCost() {
        return baseCost;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setFromLocation(String fromLocation) {
        this.fromLocation = fromLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public void setEstimatedTime(double estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public void setBaseCost(double baseCost) {
        this.baseCost = baseCost;
    }

    /**
     * Calculates the cost for a specific transport mode
     */
    public double calculateCost(TransportMode transportMode) {
        return baseCost * transportMode.getCostMultiplier();
    }

    @Override
    public String toString() {
        return "Route{" +
                "name='" + name + '\'' +
                ", fromLocation='" + fromLocation + '\'' +
                ", toLocation='" + toLocation + '\'' +
                ", distance=" + distance +
                ", estimatedTime=" + estimatedTime +
                ", baseCost=" + baseCost +
                '}';
    }
}