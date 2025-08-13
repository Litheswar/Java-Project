import java.util.ArrayList;
import java.util.Scanner;

public class PlannerService {
    private ArrayList<Trip> trips;
    private ArrayList<Destination> destinations = new ArrayList<>();

    // Constructor - load trips and set up destination list
    public PlannerService() {
        trips = DatabaseManager.loadTrips(); // Load saved trips

        // Preloaded destination suggestions
        destinations.add(new Destination("Paris", "France", "Mild", 1500,
                new String[]{"Eiffel Tower", "Louvre Museum", "Seine River Cruise"}));
        destinations.add(new Destination("Tokyo", "Japan", "Cool", 2000,
                new String[]{"Shinjuku", "Mount Fuji", "Cherry Blossom Viewing"}));
        destinations.add(new Destination("Bali", "Indonesia", "Warm", 1000,
                new String[]{"Beaches", "Temples", "Rice Terraces"}));
    }

    // Plan a new trip
    public void planTrip(Scanner sc) {
        System.out.println("\n=== Suggested Destinations ===");
        for (int i = 0; i < destinations.size(); i++) {
            System.out.println((i + 1) + ". " + destinations.get(i).getName() + " - " + destinations.get(i).getCountry());
        }
        System.out.println((destinations.size() + 1) + ". Enter my own destination");

        System.out.print("Choose an option: ");
        int destChoice = sc.nextInt();
        sc.nextLine(); // consume newline

        String destinationName;
        if (destChoice >= 1 && destChoice <= destinations.size()) {
            destinationName = destinations.get(destChoice - 1).getName();
        } else {
            System.out.print("Enter Destination Name: ");
            destinationName = sc.nextLine();
        }

        System.out.print("Enter Start Date (YYYY-MM-DD): ");
        String startDate = sc.nextLine();

        System.out.print("Enter End Date (YYYY-MM-DD): ");
        String endDate = sc.nextLine();

        System.out.print("Enter Budget: ");
        double budget = sc.nextDouble();
        sc.nextLine(); // consume newline

        System.out.print("Enter number of activities: ");
        int numActivities = sc.nextInt();
        sc.nextLine(); // consume newline

        String[] activities = new String[numActivities];
        for (int i = 0; i < numActivities; i++) {
            System.out.print("Enter activity " + (i + 1) + ": ");
            activities[i] = sc.nextLine();
        }

        Trip trip = new Trip(destinationName, startDate, endDate, budget, activities);
        trips.add(trip);

        // Save trips to file
        DatabaseManager.saveTrips(trips);

        System.out.println("✅ Trip planned successfully!");
    }

    // View saved trips
    public void viewTrips() {
        if (trips.isEmpty()) {
            System.out.println("No trips saved yet.");
            return;
        }
        System.out.println("\n=== Saved Trips ===");
        for (Trip t : trips) {
            System.out.println(t);
            System.out.println("----------------------");
        }
    }
}
