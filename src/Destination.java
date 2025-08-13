public class Destination {
    private String name;
    private String country;
    private String weather;
    private double averageCost;
    private String[] attractions;

    // Constructor
    public Destination(String name, String country, String weather, double averageCost, String[] attractions) {
        this.name = name;
        this.country = country;
        this.weather = weather;
        this.averageCost = averageCost;
        this.attractions = attractions;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public String getWeather() {
        return weather;
    }

    public double getAverageCost() {
        return averageCost;
    }

    public String[] getAttractions() {
        return attractions;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public void setAverageCost(double averageCost) {
        this.averageCost = averageCost;
    }

    public void setAttractions(String[] attractions) {
        this.attractions = attractions;
    }

    // Display Destination details
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(" - ").append(country).append("\n");
        sb.append("Weather: ").append(weather).append("\n");
        sb.append("Average Cost: $").append(averageCost).append("\n");
        sb.append("Attractions: ");
        if (attractions != null) {
            for (String attraction : attractions) {
                sb.append(attraction).append(", ");
            }
        }
        return sb.toString();
    }
}