package com.smarttravelplanner.exceptions;

public class InvalidFamilyCountException extends Exception {
    
    public InvalidFamilyCountException() {
        super("Invalid family count provided");
    }
    
    public InvalidFamilyCountException(String message) {
        super(message);
    }
    
    public InvalidFamilyCountException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public InvalidFamilyCountException(Throwable cause) {
        super(cause);
    }
}