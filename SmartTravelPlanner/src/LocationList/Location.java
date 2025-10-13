package LocationList;

/**
 * Location represents a destination with country and state metadata.
 * Demonstrates Encapsulation via private fields and getters/setters.
 */
public class Location {
    private String country;
    private String state;
    private String city;
    private double costEstimate;
    private int ecoScore; // 1-10

    public Location(String country, String state, String city, double costEstimate, int ecoScore) {
        this.country = country;
        this.state = state;
        this.city = city;
        this.costEstimate = costEstimate;
        this.ecoScore = ecoScore;
    }

    public String getCountry() { return country; }
    public String getState() { return state; }
    public String getCity() { return city; }
    public double getCostEstimate() { return costEstimate; }
    public int getEcoScore() { return ecoScore; }

    public void setCostEstimate(double costEstimate) { this.costEstimate = costEstimate; }
    public void setEcoScore(int ecoScore) { this.ecoScore = ecoScore; }

    @Override
    public String toString() {
        return country + ", " + state + ", " + city + " (₹" + costEstimate + ", eco:" + ecoScore + ")";
    }
}
