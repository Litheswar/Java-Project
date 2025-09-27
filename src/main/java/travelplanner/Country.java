package travelplanner;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a country with its states/regions.
 * Demonstrates Encapsulation with private fields and public getters/setters.
 * Demonstrates ArrayList for storing dynamic lists of states.
 */
public class Country {
    private String name;
    private List<State> states;

    // Default constructor
    public Country() {
        this.states = new ArrayList<>();
    }

    // Parameterized constructor
    public Country(String name) {
        this.name = name;
        this.states = new ArrayList<>();
    }

    // Getters
    public String getName() {
        return name;
    }

    public List<State> getStates() {
        return new ArrayList<>(states);
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    // Methods to manage states
    public void addState(State state) {
        states.add(state);
    }

    public void removeState(State state) {
        states.remove(state);
    }

    public State findStateByName(String name) {
        for (State state : states) {
            if (state.getName().equalsIgnoreCase(name)) {
                return state;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Country{" +
                "name='" + name + '\'' +
                ", states=" + states.size() +
                '}';
    }
}