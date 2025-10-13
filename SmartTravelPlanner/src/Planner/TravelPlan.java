package Planner;

import LocationList.Route;

import java.util.ArrayList;
import java.util.List;

/**
 * TravelPlan is an abstract class showcasing Abstraction.
 * Subclasses use Polymorphism to provide estimateCost and displayPlan.
 */
public abstract class TravelPlan {
    protected final List<Route> steps = new ArrayList<>(); // ArrayList usage

    public void addStep(Route route) { steps.add(route); }
    public List<Route> getSteps() { return steps; }

    public abstract double estimateCost();
    public abstract void displayPlan();
}
