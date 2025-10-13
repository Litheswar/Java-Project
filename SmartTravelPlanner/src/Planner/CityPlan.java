package Planner;

import LocationList.Route;

/**
 * CityPlan: single-city trip plan, overrides estimateCost and displayPlan.
 */
public class CityPlan extends TravelPlan {
    @Override
    public double estimateCost() {
        double sum = 0;
        for (Route r : steps) sum += r.getTravelCost();
        // Basic city plan surcharge factor
        return sum * 1.05;
    }

    @Override
    public void displayPlan() {
        System.out.println("City Plan Steps:");
        int i = 1;
        for (Route r : steps) System.out.println((i++) + ". " + r);
        System.out.println("Estimated Travel Cost: ₹" + estimateCost());
    }
}
