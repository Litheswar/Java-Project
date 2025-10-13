package CostManager;

import Database.DBUtils;
import Planner.TravelPlan;
import LocationList.Route;
import Models.CostBreakdown;
import Config.Constants;

import java.util.List;

/**
 * CostManager encapsulates budget estimation, validation, and suggestions.
 */
public class CostManager {
    // Basic category multipliers per family member
    private static final double FOOD_PER_MEAL_PER_PERSON = 200; // adjustable
    private static final double STAY_PER_DAY_PER_PERSON = 800;   // hotel/stay cost baseline
    private static final double SHOPPING_PER_PERSON = 200;       // one-time
    private static final double TRAVEL_RATE_PER_KM = 10;         // per km if plan lacks explicit cost

    public double calculateEstimatedCost(TravelPlan plan, int familyMembers) {
        // Backward-compatible default: assume 1 day and 3 meals per day
        return calculateEstimatedCost(plan, familyMembers, 1, 3);
    }

    // Richer cost formula: (distance * travelRate) + (days * stayCost) + (meals * foodCost) + shopping
    public double calculateEstimatedCost(TravelPlan plan, int familyMembers, int days, int mealsPerDay) {
        double travel = plan.estimateCost();
        if (Double.isNaN(travel) || travel <= 0) {
            // derive from total distance if needed
            int distance = plan.getSteps().stream().mapToInt(r -> r.getDistanceKm()).sum();
            travel = distance * TRAVEL_RATE_PER_KM;
        }
        double stay = days * STAY_PER_DAY_PER_PERSON * familyMembers;
        double food = ((long) days * mealsPerDay) * FOOD_PER_MEAL_PER_PERSON * familyMembers;
        double shopping = SHOPPING_PER_PERSON * familyMembers;
        return travel + stay + food + shopping;
    }

    public CostBreakdown getBreakdown(TravelPlan plan, int familyMembers, int days, int mealsPerDay, double discountRate) {
        return getBreakdown(plan, familyMembers, days, mealsPerDay, discountRate, "road", "standard", "veg", null);
    }

    public CostBreakdown getBreakdown(TravelPlan plan, int familyMembers, int days, int mealsPerDay,
                                      double discountRate, String travelMode, String accommodationType, String mealPreference) {
        return getBreakdown(plan, familyMembers, days, mealsPerDay, discountRate, travelMode, accommodationType, mealPreference, null);
    }

    // Region-aware overload
    public CostBreakdown getBreakdown(TravelPlan plan, int familyMembers, int days, int mealsPerDay,
                                      double discountRate, String travelMode, String accommodationType, String mealPreference, String country) {
        // Determine distance to support mixed mode and travel fallback
        int distance = plan.getSteps().stream().mapToInt(Route::getDistanceKm).sum();
        // travel either explicit from plan or derived by distance
        double travel = plan.estimateCost();
        if (Double.isNaN(travel) || travel <= 0) {
            travel = distance * TRAVEL_RATE_PER_KM;
        }
        // base variable categories
        double stay = days * STAY_PER_DAY_PER_PERSON * familyMembers;
        double food = ((long) days * mealsPerDay) * FOOD_PER_MEAL_PER_PERSON * familyMembers;
        double shopping = SHOPPING_PER_PERSON * familyMembers;

        // Apply preference multipliers
        double modeFactor = resolveModeFactor(travelMode, distance);
        double accFactor = resolveAccommodationFactor(accommodationType);
        double mealFactor = resolveMealFactor(mealPreference);

        travel *= modeFactor;
        stay *= accFactor;
        food *= mealFactor;

        // Apply regional pricing factor (affects core categories)
        double regionFactor = Config.Constants.resolveRegionFactor(country);
        travel *= regionFactor;
        stay *= regionFactor;
        food *= regionFactor;

        // Apply discounts to variable categories after multipliers
        stay *= (1 - discountRate);
        food *= (1 - discountRate);

        // leisure and local commute additions
        double leisure = (stay + food) * Constants.LEISURE_PERCENT;
        double localCommute = travel * Constants.LOCAL_COMMUTE_PERCENT;

        int sustainability = computeSustainability(plan);
        double carbon = estimateCarbonFootprintKg(plan, familyMembers);
        return new CostBreakdown(travel, food, stay, shopping, leisure, localCommute, sustainability, carbon);
    }

    private double resolveModeFactor(String mode, int distanceKm) {
        if (mode == null) return Constants.MODE_ROAD_COST;
        String m = mode.toLowerCase();
        if ("mixed".equals(m)) {
            if (distanceKm > 800) return Constants.MODE_AIR_COST;
            if (distanceKm > 150) return Constants.MODE_RAIL_COST;
            return Constants.MODE_ROAD_COST;
        }
        switch (m) {
            case "rail": return Constants.MODE_RAIL_COST;
            case "air": return Constants.MODE_AIR_COST;
            default: return Constants.MODE_ROAD_COST;
        }
    }

    private double resolveAccommodationFactor(String acc) {
        if (acc == null) return Constants.ACC_STANDARD;
        switch (acc.toLowerCase()) {
            case "budget": return Constants.ACC_BUDGET;
            case "premium": return Constants.ACC_PREMIUM;
            default: return Constants.ACC_STANDARD;
        }
    }

    private double resolveMealFactor(String pref) {
        if (pref == null) return Constants.MEAL_VEG;
        switch (pref.toLowerCase()) {
            case "non-veg": return Constants.MEAL_NONVEG;
            case "mixed": return Constants.MEAL_MIXED;
            default: return Constants.MEAL_VEG;
        }
    }

    private int computeSustainability(TravelPlan plan) {
        if (plan.getSteps().isEmpty()) return 7;
        // simple heuristic by mode; average across steps
        int total = 0;
        for (Route r : plan.getSteps()) {
            int s;
            String m = r.getMode() == null ? "road" : r.getMode().toLowerCase();
            switch (m) {
                case "train": s = 9; break;
                case "road": s = 8; break;
                case "air": s = 5; break;
                default: s = 7; break;
            }
            total += s;
        }
        return Math.max(1, Math.min(10, Math.round((float) total / plan.getSteps().size())));
    }

    private double estimateCarbonFootprintKg(TravelPlan plan, int familyMembers) {
        if (plan.getSteps().isEmpty()) return 0;
        double totalKg = 0.0;
        for (Route r : plan.getSteps()) {
            String m = r.getMode() == null ? "road" : r.getMode().toLowerCase();
            double factor;
            switch (m) {
                case "train": factor = Constants.CARBON_RAIL; break;
                case "air": factor = Constants.CARBON_AIR; break;
                default: factor = Constants.CARBON_ROAD; break;
            }
            totalKg += r.getDistanceKm() * factor * Math.max(1, familyMembers);
        }
        return Math.round(totalKg);
    }

    public void suggestAlternativeDestinations(double userBudget) {
        List<String> fromDB = DBUtils.listDestinationsUnderBudget(userBudget);
        if (!fromDB.isEmpty()) {
            System.out.println("Affordable destinations (DB):");
            int i = 1;
            for (String name : fromDB) System.out.println((i++) + ". " + name);
            return;
        }
        // Fallback message if DB not available
        System.out.println("No DB suggestions available. Consider destinations from the file list under your budget.");
    }
}
