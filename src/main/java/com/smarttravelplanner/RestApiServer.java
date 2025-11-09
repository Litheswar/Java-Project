package com.smarttravelplanner;

import com.smarttravelplanner.controller.TravelController;
import spark.Spark;

public class RestApiServer {
    public static void main(String[] args) {
        int port = 8080;
        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.err.println("Invalid port number, using default port 8080");
            }
        }
        
        // Set the port before any routes are defined
        Spark.port(port);
        
        // Create controller and setup routes
        TravelController controller = new TravelController();
        controller.setupRoutes();
        
        System.out.println("Travel Planner REST API Server started on port " + port);
        System.out.println("API endpoints available at http://localhost:" + port + "/api/");
        System.out.println("Press Ctrl+C to stop the server");
        
        // Keep the server running
        try {
            // This will keep the main thread alive
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            System.err.println("Server interrupted");
        }
    }
}