package com.smarttravelplanner.exceptions;

public class InvalidInputException extends Exception {
    
    public InvalidInputException() {
        super("Invalid input provided");
    }
    
    public InvalidInputException(String message) {
        super(message);
    }
    
    public InvalidInputException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public InvalidInputException(Throwable cause) {
        super(cause);
    }
}