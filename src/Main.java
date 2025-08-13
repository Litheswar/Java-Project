import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        PlannerService plannerService = new PlannerService();

        int choice;
        do {
            System.out.println("\n=== Welcome to Smart Travel Planner ===");
            System.out.println("1. Plan a Trip");
            System.out.println("2. View Saved Trips");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    plannerService.planTrip(sc);
                    break;
                case 2:
                    plannerService.viewTrips();
                    break;
                case 3:
                    System.out.println("Thank you for using Smart Travel Planner!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 3);

        sc.close();
    }
}
