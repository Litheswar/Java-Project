import java.io.*;
import java.util.ArrayList;

public class DatabaseManager {
    private static final String FILE_NAME = "data/trips.dat";

    // Save trips to file
    public static void saveTrips(ArrayList<Trip> trips) {
        try {
            File dir = new File("data");
            if (!dir.exists()) {
                dir.mkdir();
            }

            FileOutputStream fos = new FileOutputStream(FILE_NAME);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(trips);
            oos.close();
            fos.close();
            System.out.println("💾 Trips saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving trips: " + e.getMessage());
        }
    }

    // Load trips from file
    @SuppressWarnings("unchecked")
    public static ArrayList<Trip> loadTrips() {
        ArrayList<Trip> trips = new ArrayList<>();
        try {
            File file = new File(FILE_NAME);
            if (!file.exists()) {
                return trips; // return empty if no file
            }

            FileInputStream fis = new FileInputStream(FILE_NAME);
            ObjectInputStream ois = new ObjectInputStream(fis);
            trips = (ArrayList<Trip>) ois.readObject();
            ois.close();
            fis.close();
            System.out.println("📂 Trips loaded successfully.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading trips: " + e.getMessage());
        }
        return trips;
    }
}
