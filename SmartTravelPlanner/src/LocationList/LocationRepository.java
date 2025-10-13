package LocationList;

import Database.DBConnection;

import java.io.*;
import java.sql.*;
import java.util.*;

/**
 * LocationRepository loads locations and routes from file or DB.
 * Demonstrates File I/O, JDBC, and graceful fallbacks with Exception Handling.
 */
public class LocationRepository {
    private final List<Location> locations = new ArrayList<>();
    private final List<Route> routes = new ArrayList<>();

    public List<Location> getLocations() { return Collections.unmodifiableList(locations); }
    public List<Route> getRoutes() { return Collections.unmodifiableList(routes); }

    public void loadFromFiles(String locationsFile, String routesFile) throws IOException {
        locations.clear();
        routes.clear();
        // locations.txt format: country|state|city|costEstimate|ecoScore
        try (BufferedReader br = new BufferedReader(new FileReader(locationsFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty() || line.startsWith("#")) continue;
                String[] p = line.split("\\|");
                if (p.length >= 5) {
                    locations.add(new Location(p[0], p[1], p[2], Double.parseDouble(p[3]), Integer.parseInt(p[4])));
                }
            }
        }
        // routes.txt format:
        // basic: fromCity|toCity|distanceKm|travelCost
        // extended: fromCity|toCity|distanceKm|travelCost|mode|durationMinutes
        try (BufferedReader br = new BufferedReader(new FileReader(routesFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty() || line.startsWith("#")) continue;
                String[] p = line.split("\\|");
                if (p.length >= 6) {
                    routes.add(new Route(p[0], p[1], Integer.parseInt(p[2]), Double.parseDouble(p[3]), p[4], Integer.parseInt(p[5])));
                } else if (p.length >= 4) {
                    routes.add(new Route(p[0], p[1], Integer.parseInt(p[2]), Double.parseDouble(p[3])));
                }
            }
        }
    }

    public void loadFromDatabase() throws SQLException {
        locations.clear();
        routes.clear();
        try (Connection con = DBConnection.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("SELECT d.name, s.name, c.name, d.cost_estimate, d.eco_score FROM destinations d JOIN states s ON d.state_id=s.id JOIN countries c ON s.country_id=c.id");
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String city = rs.getString(1);
                    String state = rs.getString(2);
                    String country = rs.getString(3);
                    double cost = rs.getDouble(4);
                    int eco = rs.getInt(5);
                    locations.add(new Location(country, state, city, cost, eco));
                }
            }
            try (PreparedStatement ps = con.prepareStatement("SELECT from_city, to_city, distance_km, travel_cost FROM routes");
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    routes.add(new Route(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getDouble(4)));
                }
            }
        }
    }

    public List<String> statesByCountry(String country) {
        Set<String> states = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        for (Location l : locations) if (l.getCountry().equalsIgnoreCase(country)) states.add(l.getState());
        return new ArrayList<>(states);
    }

    public List<Location> destinationsByState(String country, String state) {
        List<Location> out = new ArrayList<>();
        for (Location l : locations) {
            if (l.getCountry().equalsIgnoreCase(country) && l.getState().equalsIgnoreCase(state)) out.add(l);
        }
        return out;
    }

    public List<Route> routesFrom(String city) {
        List<Route> out = new ArrayList<>();
        for (Route r : routes) if (r.getFromCity().equalsIgnoreCase(city)) out.add(r);
        return out;
    }

    public List<String> countries() {
        Set<String> cs = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        for (Location l : locations) cs.add(l.getCountry());
        return new ArrayList<>(cs);
    }
}
