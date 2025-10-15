package com.smarttravelplanner.exceptions;

public class InvalidAgeException extends Exception {
    
    public InvalidAgeException() {
        super("Invalid age provided");
    }
    
    public InvalidAgeException(String message) {
        super(message);
    }
    
    public InvalidAgeException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public InvalidAgeException(Throwable cause) {
        super(cause);
    }
}