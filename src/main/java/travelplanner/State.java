package travelplanner;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a state/region within a country with its destinations and routes.
 * Demonstrates Encapsulation with private fields and public getters/setters.
 * Demonstrates ArrayList for storing dynamic lists of destinations and routes.
 */
public class State {
    private String name;
    private String country;
    private List<Location> destinations;
    private List<Route> routes;

    // Default constructor
    public State() {
        this.destinations = new ArrayList<>();
        this.routes = new ArrayList<>();
    }

    // Parameterized constructor
    public State(String name, String country) {
        this.name = name;
        this.country = country;
        this.destinations = new ArrayList<>();
        this.routes = new ArrayList<>();
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public List<Location> getDestinations() {
        return new ArrayList<>(destinations);
    }

    public List<Route> getRoutes() {
        return new ArrayList<>(routes);
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    // Methods to manage destinations
    public void addDestination(Location destination) {
        destinations.add(destination);
    }

    public void removeDestination(Location destination) {
        destinations.remove(destination);
    }

    public Location findDestinationByName(String name) {
        for (Location destination : destinations) {
            if (destination.getName().equalsIgnoreCase(name)) {
                return destination;
            }
        }
        return null;
    }

    // Methods to manage routes
    public void addRoute(Route route) {
        routes.add(route);
    }

    public void removeRoute(Route route) {
        routes.remove(route);
    }

    public Route findRouteByName(String name) {
        for (Route route : routes) {
            if (route.getName().equalsIgnoreCase(name)) {
                return route;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "State{" +
                "name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", destinations=" + destinations.size() +
                ", routes=" + routes.size() +
                '}';
    }
}