package LocationList;

/**
 * Route represents a path between two cities and its cost.
 */
public class Route {
    private final String fromCity;
    private final String toCity;
    private final int distanceKm;
    private final double travelCost;
    private final String mode; // road/train/air
    private final int durationMinutes; // estimated duration

    public Route(String fromCity, String toCity, int distanceKm, double travelCost) {
        this(fromCity, toCity, distanceKm, travelCost, "road", Math.max(60, distanceKm));
    }

    public Route(String fromCity, String toCity, int distanceKm, double travelCost, String mode, int durationMinutes) {
        this.fromCity = fromCity;
        this.toCity = toCity;
        this.distanceKm = distanceKm;
        this.travelCost = travelCost;
        this.mode = mode;
        this.durationMinutes = durationMinutes;
    }

    public String getFromCity() { return fromCity; }
    public String getToCity() { return toCity; }
    public int getDistanceKm() { return distanceKm; }
    public double getTravelCost() { return travelCost; }
    public String getMode() { return mode; }
    public int getDurationMinutes() { return durationMinutes; }

    @Override
    public String toString() {
        return fromCity + " → " + toCity + " (" + distanceKm + " km, Cost: ₹" + travelCost + ", Mode: " + mode + ", Duration: " + durationMinutes + "m)";
    }
}
