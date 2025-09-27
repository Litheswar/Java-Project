package travelplanner;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository for managing country-state-destination hierarchy using File I/O.
 * Demonstrates File I/O operations for storing hierarchical travel data.
 */
public class CountryRepository {
    private static final String COUNTRIES_FILE = "countries.txt";
    private static final String STATES_FILE = "states.txt";
    private static final String DESTINATIONS_FILE = "destinations.txt";
    private static final String ROUTES_FILE = "routes.txt";
    
    private List<Country> countries;

    public CountryRepository() {
        this.countries = new ArrayList<>();
        loadAllData();
    }

    /**
     * Gets all countries from the repository
     */
    public List<Country> getAllCountries() {
        return new ArrayList<>(countries);
    }

    /**
     * Finds a country by name
     */
    public Country findCountryByName(String name) {
        for (Country country : countries) {
            if (country.getName().equalsIgnoreCase(name)) {
                return country;
            }
        }
        return null;
    }

    /**
     * Adds a new country to the repository
     */
    public void addCountry(Country country) {
        countries.add(country);
        saveCountries();
    }

    /**
     * Initializes the repository with sample data if it's empty
     */
    public void initializeWithSampleData() {
        if (countries.isEmpty()) {
            // Add sample countries
            Country india = new Country("India");
            Country usa = new Country("USA");
            Country france = new Country("France");
            
            // Add states to India
            State rajasthan = new State("Rajasthan", "India");
            State kerala = new State("Kerala", "India");
            
            // Add destinations to Rajasthan
            rajasthan.addDestination(new Location("Jaipur", "India", "Pink City", 26.9124, 75.7873, "Winter", 75.0));
            rajasthan.addDestination(new Location("Udaipur", "India", "City of Lakes", 24.5854, 73.7125, "Winter", 80.0));
            rajasthan.addDestination(new Location("Jodhpur", "India", "Blue City", 26.2389, 73.0243, "Winter", 70.0));
            
            // Add routes to Rajasthan
            rajasthan.addRoute(new Route("Jaipur to Udaipur", "Jaipur", "Udaipur", 400.0, 8.0, 200.0));
            rajasthan.addRoute(new Route("Udaipur to Jodhpur", "Udaipur", "Jodhpur", 350.0, 7.0, 180.0));
            rajasthan.addRoute(new Route("Jaipur to Jodhpur", "Jaipur", "Jodhpur", 300.0, 6.0, 150.0));
            
            // Add destinations to Kerala
            kerala.addDestination(new Location("Kochi", "India", "Queen of the Arabian Sea", 9.9312, 76.2673, "Winter", 85.0));
            kerala.addDestination(new Location("Munnar", "India", "Hill Station", 10.0889, 77.0595, "Winter", 90.0));
            kerala.addDestination(new Location("Alleppey", "India", "Backwaters", 9.4988, 76.3388, "Winter", 80.0));
            
            // Add routes to Kerala
            kerala.addRoute(new Route("Kochi to Munnar", "Kochi", "Munnar", 180.0, 4.0, 100.0));
            kerala.addRoute(new Route("Munnar to Alleppey", "Munnar", "Alleppey", 200.0, 5.0, 120.0));
            kerala.addRoute(new Route("Kochi to Alleppey", "Kochi", "Alleppey", 80.0, 2.0, 50.0));
            
            india.addState(rajasthan);
            india.addState(kerala);
            
            // Add states to USA
            State california = new State("California", "USA");
            State newYork = new State("New York", "USA");
            
            // Add destinations to California
            california.addDestination(new Location("Los Angeles", "USA", "City of Angels", 34.0522, -118.2437, "Spring/Fall", 200.0));
            california.addDestination(new Location("San Francisco", "USA", "The City by the Bay", 37.7749, -122.4194, "Spring/Fall", 220.0));
            california.addDestination(new Location("San Diego", "USA", "America's Finest City", 32.7157, -117.1611, "Spring/Fall", 180.0));
            
            // Add routes to California
            california.addRoute(new Route("Los Angeles to San Francisco", "Los Angeles", "San Francisco", 560.0, 8.0, 300.0));
            california.addRoute(new Route("San Francisco to San Diego", "San Francisco", "San Diego", 780.0, 12.0, 400.0));
            california.addRoute(new Route("Los Angeles to San Diego", "Los Angeles", "San Diego", 220.0, 3.5, 120.0));
            
            // Add destinations to New York
            newYork.addDestination(new Location("New York City", "USA", "The Big Apple", 40.7128, -74.0060, "Spring/Fall", 250.0));
            newYork.addDestination(new Location("Albany", "USA", "Capital of New York", 42.6526, -73.7562, "Spring/Fall", 150.0));
            newYork.addDestination(new Location("Buffalo", "USA", "City of Good Neighbors", 42.8864, -78.8784, "Spring/Fall", 140.0));
            
            // Add routes to New York
            newYork.addRoute(new Route("New York City to Albany", "New York City", "Albany", 250.0, 4.5, 150.0));
            newYork.addRoute(new Route("Albany to Buffalo", "Albany", "Buffalo", 400.0, 7.0, 200.0));
            newYork.addRoute(new Route("New York City to Buffalo", "New York City", "Buffalo", 500.0, 8.5, 250.0));
            
            usa.addState(california);
            usa.addState(newYork);
            
            // Add states to France
            State parisRegion = new State("Paris Region", "France");
            State provence = new State("Provence", "France");
            
            // Add destinations to Paris Region
            parisRegion.addDestination(new Location("Paris", "France", "City of Light", 48.8566, 2.3522, "Spring/Fall", 150.0));
            parisRegion.addDestination(new Location("Versailles", "France", "Palace of Versailles", 48.8049, 2.1201, "Spring/Fall", 120.0));
            parisRegion.addDestination(new Location("Fontainebleau", "France", "Royal Palace", 48.4047, 2.7018, "Spring/Fall", 100.0));
            
            // Add routes to Paris Region
            parisRegion.addRoute(new Route("Paris to Versailles", "Paris", "Versailles", 20.0, 0.5, 20.0));
            parisRegion.addRoute(new Route("Paris to Fontainebleau", "Paris", "Fontainebleau", 60.0, 1.5, 40.0));
            parisRegion.addRoute(new Route("Versailles to Fontainebleau", "Versailles", "Fontainebleau", 80.0, 2.0, 50.0));
            
            // Add destinations to Provence
            provence.addDestination(new Location("Nice", "France", "French Riviera", 43.7102, 7.2620, "Spring/Fall", 180.0));
            provence.addDestination(new Location("Marseille", "France", "Oldest city in France", 43.2965, 5.3698, "Spring/Fall", 140.0));
            provence.addDestination(new Location("Avignon", "France", "City of Popes", 43.9493, 4.8055, "Spring/Fall", 130.0));
            
            // Add routes to Provence
            provence.addRoute(new Route("Nice to Marseille", "Nice", "Marseille", 200.0, 3.0, 100.0));
            provence.addRoute(new Route("Marseille to Avignon", "Marseille", "Avignon", 100.0, 1.5, 60.0));
            provence.addRoute(new Route("Nice to Avignon", "Nice", "Avignon", 250.0, 3.5, 120.0));
            
            france.addState(parisRegion);
            france.addState(provence);
            
            countries.add(india);
            countries.add(usa);
            countries.add(france);
            
            saveAllData();
        }
    }

    /**
     * Saves all data to files
     */
    public void saveAllData() {
        saveCountries();
        saveStates();
        saveDestinations();
        saveRoutes();
    }

    /**
     * Loads all data from files
     */
    private void loadAllData() {
        loadCountries();
        loadStates();
        loadDestinations();
        loadRoutes();
    }

    /**
     * Saves countries to a text file
     */
    private void saveCountries() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(COUNTRIES_FILE))) {
            for (Country country : countries) {
                writer.println(country.getName());
            }
        } catch (IOException e) {
            System.err.println("Error saving countries: " + e.getMessage());
        }
    }

    /**
     * Loads countries from a text file
     */
    private void loadCountries() {
        try (BufferedReader reader = new BufferedReader(new FileReader(COUNTRIES_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                countries.add(new Country(line.trim()));
            }
        } catch (FileNotFoundException e) {
            // File doesn't exist yet, which is fine for a new application
            System.out.println("Countries file not found. Starting with empty repository.");
        } catch (IOException e) {
            System.err.println("Error loading countries: " + e.getMessage());
        }
    }

    /**
     * Saves states to a text file
     */
    private void saveStates() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(STATES_FILE))) {
            for (Country country : countries) {
                for (State state : country.getStates()) {
                    writer.println(country.getName() + "|" + state.getName());
                }
            }
        } catch (IOException e) {
            System.err.println("Error saving states: " + e.getMessage());
        }
    }

    /**
     * Loads states from a text file
     */
    private void loadStates() {
        try (BufferedReader reader = new BufferedReader(new FileReader(STATES_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 2) {
                    String countryName = parts[0];
                    String stateName = parts[1];
                    
                    Country country = findCountryByName(countryName);
                    if (country != null) {
                        country.addState(new State(stateName, countryName));
                    }
                }
            }
        } catch (FileNotFoundException e) {
            // File doesn't exist yet, which is fine for a new application
            System.out.println("States file not found. Starting with empty repository.");
        } catch (IOException e) {
            System.err.println("Error loading states: " + e.getMessage());
        }
    }

    /**
     * Saves destinations to a text file
     */
    private void saveDestinations() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(DESTINATIONS_FILE))) {
            for (Country country : countries) {
                for (State state : country.getStates()) {
                    for (Location destination : state.getDestinations()) {
                        writer.println(country.getName() + "|" + state.getName() + "|" + 
                                      destination.getName() + "|" + destination.getCountry() + "|" + 
                                      destination.getDescription() + "|" + destination.getLatitude() + "|" + 
                                      destination.getLongitude() + "|" + destination.getBestSeason() + "|" + 
                                      destination.getAverageCostPerDay());
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error saving destinations: " + e.getMessage());
        }
    }

    /**
     * Loads destinations from a text file
     */
    private void loadDestinations() {
        try (BufferedReader reader = new BufferedReader(new FileReader(DESTINATIONS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 9) {
                    String countryName = parts[0];
                    String stateName = parts[1];
                    String name = parts[2];
                    String country = parts[3];
                    String description = parts[4];
                    double latitude = Double.parseDouble(parts[5]);
                    double longitude = Double.parseDouble(parts[6]);
                    String bestSeason = parts[7];
                    double averageCostPerDay = Double.parseDouble(parts[8]);
                    
                    Country countryObj = findCountryByName(countryName);
                    if (countryObj != null) {
                        State state = countryObj.findStateByName(stateName);
                        if (state != null) {
                            state.addDestination(new Location(name, country, description, latitude, longitude, bestSeason, averageCostPerDay));
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            // File doesn't exist yet, which is fine for a new application
            System.out.println("Destinations file not found. Starting with empty repository.");
        } catch (IOException e) {
            System.err.println("Error loading destinations: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error parsing destination data: " + e.getMessage());
        }
    }

    /**
     * Saves routes to a text file
     */
    private void saveRoutes() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ROUTES_FILE))) {
            for (Country country : countries) {
                for (State state : country.getStates()) {
                    for (Route route : state.getRoutes()) {
                        writer.println(country.getName() + "|" + state.getName() + "|" + 
                                      route.getName() + "|" + route.getFromLocation() + "|" + 
                                      route.getToLocation() + "|" + route.getDistance() + "|" + 
                                      route.getEstimatedTime() + "|" + route.getBaseCost());
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error saving routes: " + e.getMessage());
        }
    }

    /**
     * Loads routes from a text file
     */
    private void loadRoutes() {
        try (BufferedReader reader = new BufferedReader(new FileReader(ROUTES_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 8) {
                    String countryName = parts[0];
                    String stateName = parts[1];
                    String name = parts[2];
                    String fromLocation = parts[3];
                    String toLocation = parts[4];
                    double distance = Double.parseDouble(parts[5]);
                    double estimatedTime = Double.parseDouble(parts[6]);
                    double baseCost = Double.parseDouble(parts[7]);
                    
                    Country countryObj = findCountryByName(countryName);
                    if (countryObj != null) {
                        State state = countryObj.findStateByName(stateName);
                        if (state != null) {
                            state.addRoute(new Route(name, fromLocation, toLocation, distance, estimatedTime, baseCost));
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            // File doesn't exist yet, which is fine for a new application
            System.out.println("Routes file not found. Starting with empty repository.");
        } catch (IOException e) {
            System.err.println("Error loading routes: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error parsing route data: " + e.getMessage());
        }
    }
}