import java.io.Serializable;

public class Trip implements Serializable {
    private static final long serialVersionUID = 1L;

    private String destination;
    private String startDate;
    private String endDate;
    private double budget;
    private String[] activities;

    // Constructor
    public Trip(String destination, String startDate, String endDate, double budget, String[] activities) {
        this.destination = destination;
        this.startDate = startDate;
        this.endDate = endDate;
        this.budget = budget;
        this.activities = activities;
    }

    // Getters
    public String getDestination() {
        return destination;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public double getBudget() {
        return budget;
    }

    public String[] getActivities() {
        return activities;
    }

    // Setters
    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public void setActivities(String[] activities) {
        this.activities = activities;
    }

    // Display Trip details
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Destination: ").append(destination).append("\n");
        sb.append("Start Date: ").append(startDate).append("\n");
        sb.append("End Date: ").append(endDate).append("\n");
        sb.append("Budget: $").append(budget).append("\n");
        sb.append("Activities: ");
        if (activities != null) {
            for (String activity : activities) {
                sb.append(activity).append(", ");
            }
        }
        return sb.toString();
    }
}
