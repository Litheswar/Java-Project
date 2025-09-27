package travelplanner;

/**
 * Custom exception for invalid or missing destinations.
 * Demonstrates Exception Handling.
 */
public class InvalidDestinationException extends Exception {
    public InvalidDestinationException(String message) {
        super(message);
    }
}