package Planner;

/**
 * MissingDestinationException indicates when a destination/state/country is missing.
 */
public class MissingDestinationException extends Exception {
    public MissingDestinationException(String message) { super(message); }
}
