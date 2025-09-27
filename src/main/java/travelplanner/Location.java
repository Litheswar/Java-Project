package travelplanner;

/**
 * Represents a travel destination with its details.
 * Demonstrates Encapsulation with private fields and public getters/setters.
 */
public class Location {
    private String name;
    private String country;
    private String description;
    private double latitude;
    private double longitude;
    private String bestSeason;
    private double averageCostPerDay;

    // Default constructor
    public Location() {
    }

    // Parameterized constructor
    public Location(String name, String country, String description, 
                   double latitude, double longitude, String bestSeason, 
                   double averageCostPerDay) {
        this.name = name;
        this.country = country;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.bestSeason = bestSeason;
        this.averageCostPerDay = averageCostPerDay;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public String getDescription() {
        return description;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getBestSeason() {
        return bestSeason;
    }

    public double getAverageCostPerDay() {
        return averageCostPerDay;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setBestSeason(String bestSeason) {
        this.bestSeason = bestSeason;
    }

    public void setAverageCostPerDay(double averageCostPerDay) {
        this.averageCostPerDay = averageCostPerDay;
    }

    @Override
    public String toString() {
        return "Location{" +
                "name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", description='" + description + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", bestSeason='" + bestSeason + '\'' +
                ", averageCostPerDay=" + averageCostPerDay +
                '}';
    }
}