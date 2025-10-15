package com.smarttravelplanner.exceptions;

public class BudgetExceededException extends Exception {
    
    public BudgetExceededException() {
        super("Budget exceeded for the travel plan");
    }
    
    public BudgetExceededException(String message) {
        super(message);
    }
    
    public BudgetExceededException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public BudgetExceededException(Throwable cause) {
        super(cause);
    }
}