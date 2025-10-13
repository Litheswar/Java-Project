package Planner;

import LocationList.Route;

/**
 * TourPlan: multi-city or multi-day plan, uses a different cost strategy.
 */
public class TourPlan extends TravelPlan {
    @Override
    public double estimateCost() {
        double sum = 0;
        for (Route r : steps) sum += r.getTravelCost();
        // Tour discount for multi-leg trips
        return sum * 0.95;
    }

    @Override
    public void displayPlan() {
        System.out.println("Tour Plan Steps:");
        int i = 1;
        for (Route r : steps) System.out.println((i++) + ". " + r);
        System.out.println("Estimated Travel Cost: ₹" + estimateCost());
    }
}
