package Planner;

import LocationList.Location;
import LocationList.LocationRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * SmartSuggestionEngine offers affordable and premium trip suggestions.
 */
public class SmartSuggestionEngine {
    private final LocationRepository repo;

    public SmartSuggestionEngine(LocationRepository repo) {
        this.repo = repo;
    }

    public List<Location> suggestAffordableTrips(double userBudget) {
        return repo.getLocations().stream()
                .filter(l -> l.getCostEstimate() <= userBudget)
                .sorted(Comparator.comparingDouble(Location::getCostEstimate))
                .limit(5)
                .collect(Collectors.toList());
    }

    public List<Location> suggestAffordableTrips(double userBudget, String country) {
        return repo.getLocations().stream()
                .filter(l -> l.getCountry().equalsIgnoreCase(country))
                .filter(l -> l.getCostEstimate() <= userBudget)
                .sorted(Comparator.comparingDouble(Location::getCostEstimate))
                .limit(5)
                .collect(Collectors.toList());
    }

    public List<Location> suggestPremiumTrips(double userBudget) {
        return repo.getLocations().stream()
                .filter(l -> l.getCostEstimate() > userBudget)
                .sorted(Comparator.comparingDouble(Location::getCostEstimate))
                .limit(5)
                .collect(Collectors.toList());
    }
}
