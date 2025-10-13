package Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import LocationList.Route;

/**
 * DBUtils centralizes common DB operations using PreparedStatement for safety.
 */
public class DBUtils {

    public static List<String> listDestinationsUnderBudget(double budget) {
        String sql = "SELECT name FROM destinations WHERE cost_estimate <= ? ORDER BY cost_estimate ASC";
        List<String> result = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDouble(1, budget);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    result.add(rs.getString("name"));
                }
            }
        } catch (SQLException ex) {
            // Swallow for now, higher layers may fallback to file-based suggestion
        }
        return result;
    }

    // Returns list of country names from DB. Falls back to empty list on failure.
    public static List<String> getAllCountries() {
        String sql = "SELECT name FROM countries ORDER BY name";
        List<String> countries = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) countries.add(rs.getString(1));
        } catch (SQLException e) {
            System.out.println("Error fetching countries: " + e.getMessage());
        }
        return countries;
    }

    // Returns list of state/province names for a given country using a join.
    public static List<String> getStatesByCountry(String country) {
        String sql = "SELECT s.name FROM states s JOIN countries c ON s.country_id = c.id WHERE c.name = ? ORDER BY s.name";
        List<String> states = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, country);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) states.add(rs.getString(1));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching states for '" + country + "': " + e.getMessage());
        }
        return states;
    }

    // Returns destinations for a state with an average cost estimate per destination name.
    // Format each entry as "name|avg_cost" to keep it generic for console rendering.
    public static List<String> getDestinationsByState(String state) {
        String sql = "SELECT d.name, AVG(d.cost_estimate) AS avg_cost FROM destinations d " +
                "JOIN states s ON d.state_id = s.id WHERE s.name = ? GROUP BY d.name ORDER BY d.name";
        List<String> destinations = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, state);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String name = rs.getString("name");
                    double avg = rs.getDouble("avg_cost");
                    destinations.add(name + "|" + Math.round(avg));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching destinations for state '" + state + "': " + e.getMessage());
        }
        return destinations;
    }

    public static List<String> listStatesByCountry(String country) {
        String sql = "SELECT s.name FROM states s JOIN countries c ON s.country_id=c.id WHERE c.name=? ORDER BY s.name";
        List<String> out = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, country);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) out.add(rs.getString(1));
            }
        } catch (SQLException e) {
            // ignore, caller may fall back to file
        }
        return out;
    }

    // Inserts a user and returns generated user_id, or -1 on failure
    public static int insertUser(String name, int familyMembers, double totalBudget) {
        String sql = "INSERT INTO users(name, family_members, total_budget) VALUES(?,?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, name);
            ps.setInt(2, familyMembers);
            ps.setDouble(3, totalBudget);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException e) {
            // ignore for demo
        }
        return -1;
    }

    // Inserts a trip row linked to user and destination; returns generated trip id or -1
    public static int insertTrip(int userId, String destinationName, double estimatedCost, String status) {
        String findDest = "SELECT id FROM destinations WHERE name = ? LIMIT 1";
        String insertTrip = "INSERT INTO trips(user_id, destination_id, estimated_cost, status) VALUES(?,?,?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement psDest = con.prepareStatement(findDest)) {
            int destId = -1;
            psDest.setString(1, destinationName);
            try (ResultSet rs = psDest.executeQuery()) {
                if (rs.next()) destId = rs.getInt(1);
            }
            if (destId == -1) return -1;
            try (PreparedStatement psIns = con.prepareStatement(insertTrip, Statement.RETURN_GENERATED_KEYS)) {
                psIns.setInt(1, userId);
                psIns.setInt(2, destId);
                psIns.setDouble(3, estimatedCost);
                psIns.setString(4, status);
                psIns.executeUpdate();
                try (ResultSet rs = psIns.getGeneratedKeys()) {
                    if (rs.next()) return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            // ignore for demo
        }
        return -1;
    }

    // Fetch routes starting at a given city; maps DB rows to Route objects.
    public static List<Route> fetchRoutes(String fromCity) {
        String sql = "SELECT from_city, to_city, distance_km, travel_cost FROM routes WHERE from_city = ?";
        List<Route> out = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, fromCity);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    out.add(new Route(
                            rs.getString(1),
                            rs.getString(2),
                            rs.getInt(3),
                            rs.getDouble(4)
                    ));
                }
            }
        } catch (SQLException ignored) { }
        return out;
    }
}
