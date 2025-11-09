package com.smarttravelplanner.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import spark.Request;
import spark.Response;

import java.util.logging.Logger;
import java.util.logging.Level;

public class TravelController {
    private static final Logger logger = Logger.getLogger(TravelController.class.getName());
    private Gson gson;
    
    public TravelController() {
        this.gson = new Gson();
    }
    
    public void setupRoutes() {
        // GET /api/test/:param - test endpoint to verify parameterized routes work
        spark.Spark.get("/api/test/:param", (req, res) -> {
            String param = req.params(":param");
            System.out.println("Test endpoint called with param: " + param);
            return "Test endpoint working with param: " + param;
        });
        System.out.println("Registered route: /api/test/:param");
        
        // GET /api/simple-test - simple test endpoint
        spark.Spark.get("/api/simple-test", (req, res) -> {
            System.out.println("Simple test endpoint called");
            return "Simple test endpoint working";
        });
        System.out.println("Registered route: /api/simple-test");
        
        // GET /api/countries-states - new endpoint for states using query parameters
        spark.Spark.get("/api/countries-states", this::getStatesByCountryQueryParam);
        System.out.println("Registered route: /api/countries-states");
        
        // GET /api/countries/:countryCode/states - register this BEFORE the countries route
        spark.Spark.get("/api/countries/:countryCode/states", this::getStatesByCountry);
        System.out.println("Registered route: /api/countries/:countryCode/states");
        
        // GET /api/places
        spark.Spark.get("/api/places", this::getPlacesByCountry);
        
        // GET /api/planner/options
        spark.Spark.get("/api/planner/options", this::getPlannerOptions);
        
        // POST /api/planner/estimate
        spark.Spark.post("/api/planner/estimate", this::postPlannerEstimate);
        
        // GET /api/destinations
        spark.Spark.get("/api/destinations", this::getAllDestinations);
        
        // GET /api/countries - this should be registered LAST to avoid interfering with parameterized routes
        spark.Spark.get("/api/countries", this::getAllCountries);
        
        // Enable CORS for all routes
        spark.Spark.options("/*", (request, response) -> {
            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }
            
            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }
            
            return "OK";
        });
        
        spark.Spark.before((request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Allow-Headers", "*");
            response.header("Content-Type", "application/json");
        });
    }
    
    // GET /api/countries-states/:countryCode
    private String getStatesByCountry(Request req, Response res) {
        try {
            String countryCode = req.params(":countryCode");
            System.out.println("Received request for states of country: " + countryCode);
            
            JsonArray statesArray = new JsonArray();
            
            // Add sample state data based on country code
            switch (countryCode.toUpperCase()) {
                case "FR":
                    addState(statesArray, "Île-de-France", 48.8566, 2.3522);
                    addState(statesArray, "Provence-Alpes-Côte d'Azur", 43.9352, 6.0679);
                    addState(statesArray, "Auvergne-Rhône-Alpes", 45.7640, 4.8357);
                    addState(statesArray, "Nouvelle-Aquitaine", 44.8404, -0.5805);
                    addState(statesArray, "Occitanie", 43.6047, 1.4442);
                    break;
                case "JP":
                    addState(statesArray, "Tokyo", 35.6762, 139.6503);
                    addState(statesArray, "Osaka", 34.6937, 135.5023);
                    addState(statesArray, "Hokkaido", 43.0621, 141.3544);
                    addState(statesArray, "Kyoto", 35.0116, 135.7681);
                    addState(statesArray, "Okinawa", 26.2124, 127.6811);
                    break;
                case "IN":
                    addState(statesArray, "Maharashtra", 19.0760, 72.8777);
                    addState(statesArray, "Delhi", 28.6139, 77.2090);
                    addState(statesArray, "Karnataka", 12.9716, 77.5946);
                    addState(statesArray, "Tamil Nadu", 13.0827, 80.2707);
                    addState(statesArray, "West Bengal", 22.5726, 88.3639);
                    break;
                case "US":
                    addState(statesArray, "California", 36.7783, -119.4179);
                    addState(statesArray, "New York", 40.7128, -74.0060);
                    addState(statesArray, "Texas", 31.9686, -99.9018);
                    addState(statesArray, "Florida", 27.6648, -81.5158);
                    addState(statesArray, "Illinois", 40.6331, -89.3985);
                    break;
                case "IT":
                    addState(statesArray, "Lazio", 41.8919, 12.5113);
                    addState(statesArray, "Lombardy", 45.4654, 9.1859);
                    addState(statesArray, "Campania", 40.8333, 14.2500);
                    addState(statesArray, "Sicily", 37.5079, 14.0525);
                    addState(statesArray, "Veneto", 45.4408, 12.3155);
                    break;
                default:
                    // Return empty array for unknown countries
                    break;
            }
            
            return gson.toJson(statesArray);
        } catch (Exception e) {
            System.out.println("Error in getStatesByCountry: " + e.getMessage());
            e.printStackTrace();
            res.status(500);
            return "Error: " + e.getMessage();
        }
    }

    // GET /api/countries-states - new endpoint for states using query parameters
    private String getStatesByCountryQueryParam(Request req, Response res) {
        try {
            String countryCode = req.queryParams("countryCode");
            System.out.println("Received request for states of country (query param): " + countryCode);
            
            if (countryCode == null || countryCode.isEmpty()) {
                res.status(400);
                return "Error: countryCode parameter is required";
            }
            
            JsonArray statesArray = new JsonArray();
            
            // Add sample state data based on country code
            switch (countryCode.toUpperCase()) {
                case "FR":
                    addState(statesArray, "Île-de-France", 48.8566, 2.3522);
                    addState(statesArray, "Provence-Alpes-Côte d'Azur", 43.9352, 6.0679);
                    addState(statesArray, "Auvergne-Rhône-Alpes", 45.7640, 4.8357);
                    addState(statesArray, "Nouvelle-Aquitaine", 44.8404, -0.5805);
                    addState(statesArray, "Occitanie", 43.6047, 1.4442);
                    break;
                case "JP":
                    addState(statesArray, "Tokyo", 35.6762, 139.6503);
                    addState(statesArray, "Osaka", 34.6937, 135.5023);
                    addState(statesArray, "Hokkaido", 43.0621, 141.3544);
                    addState(statesArray, "Kyoto", 35.0116, 135.7681);
                    addState(statesArray, "Okinawa", 26.2124, 127.6811);
                    break;
                case "IN":
                    addState(statesArray, "Maharashtra", 19.0760, 72.8777);
                    addState(statesArray, "Delhi", 28.6139, 77.2090);
                    addState(statesArray, "Karnataka", 12.9716, 77.5946);
                    addState(statesArray, "Tamil Nadu", 13.0827, 80.2707);
                    addState(statesArray, "West Bengal", 22.5726, 88.3639);
                    break;
                case "US":
                    addState(statesArray, "California", 36.7783, -119.4179);
                    addState(statesArray, "New York", 40.7128, -74.0060);
                    addState(statesArray, "Texas", 31.9686, -99.9018);
                    addState(statesArray, "Florida", 27.6648, -81.5158);
                    addState(statesArray, "Illinois", 40.6331, -89.3985);
                    break;
                case "IT":
                    addState(statesArray, "Lazio", 41.8919, 12.5113);
                    addState(statesArray, "Lombardy", 45.4654, 9.1859);
                    addState(statesArray, "Campania", 40.8333, 14.2500);
                    addState(statesArray, "Sicily", 37.5079, 14.0525);
                    addState(statesArray, "Veneto", 45.4408, 12.3155);
                    break;
                default:
                    // Return empty array for unknown countries
                    break;
            }
            
            return gson.toJson(statesArray);
        } catch (Exception e) {
            System.out.println("Error in getStatesByCountryQueryParam: " + e.getMessage());
            e.printStackTrace();
            res.status(500);
            return "Error: " + e.getMessage();
        }
    }
    
    // GET /api/places
    private String getPlacesByCountry(Request req, Response res) {
        try {
            String countryIdParam = req.queryParams("countryId");
            System.out.println("Received request for places with countryIdParam: " + countryIdParam);
            
            // Return a simple response for testing
            return "Places for country: " + countryIdParam;
        } catch (Exception e) {
            System.out.println("Error in getPlacesByCountry: " + e.getMessage());
            e.printStackTrace();
            res.status(500);
            return "Error: " + e.getMessage();
        }
    }
    
    // Helper method to add a state to the array
    private void addState(JsonArray statesArray, String name, double lat, double lng) {
        JsonObject state = new JsonObject();
        state.addProperty("name", name);
        state.addProperty("lat", lat);
        state.addProperty("lng", lng);
        statesArray.add(state);
    }
    
    // GET /api/countries
    private String getAllCountries(Request req, Response res) {
        try {
            JsonArray countriesArray = new JsonArray();
            
            // Sample country data
            JsonObject france = new JsonObject();
            france.addProperty("id", 1);
            france.addProperty("name", "France");
            france.addProperty("code", "FR");
            france.addProperty("sustainabilityScore", 85);
            JsonObject franceCoords = new JsonObject();
            franceCoords.addProperty("lat", 46.603354);
            franceCoords.addProperty("lng", 1.888334);
            france.add("coordinates", franceCoords);
            countriesArray.add(france);
            
            JsonObject japan = new JsonObject();
            japan.addProperty("id", 2);
            japan.addProperty("name", "Japan");
            japan.addProperty("code", "JP");
            japan.addProperty("sustainabilityScore", 92);
            JsonObject japanCoords = new JsonObject();
            japanCoords.addProperty("lat", 36.204824);
            japanCoords.addProperty("lng", 138.252924);
            japan.add("coordinates", japanCoords);
            countriesArray.add(japan);
            
            JsonObject india = new JsonObject();
            india.addProperty("id", 3);
            india.addProperty("name", "India");
            india.addProperty("code", "IN");
            india.addProperty("sustainabilityScore", 78);
            JsonObject indiaCoords = new JsonObject();
            indiaCoords.addProperty("lat", 20.5937);
            indiaCoords.addProperty("lng", 78.9629);
            india.add("coordinates", indiaCoords);
            countriesArray.add(india);
            
            JsonObject usa = new JsonObject();
            usa.addProperty("id", 4);
            usa.addProperty("name", "United States");
            usa.addProperty("code", "US");
            usa.addProperty("sustainabilityScore", 72);
            JsonObject usaCoords = new JsonObject();
            usaCoords.addProperty("lat", 37.0902);
            usaCoords.addProperty("lng", -95.7129);
            usa.add("coordinates", usaCoords);
            countriesArray.add(usa);
            
            JsonObject italy = new JsonObject();
            italy.addProperty("id", 5);
            italy.addProperty("name", "Italy");
            italy.addProperty("code", "IT");
            italy.addProperty("sustainabilityScore", 80);
            JsonObject italyCoords = new JsonObject();
            italyCoords.addProperty("lat", 41.8719);
            italyCoords.addProperty("lng", 12.5674);
            italy.add("coordinates", italyCoords);
            countriesArray.add(italy);
            
            return gson.toJson(countriesArray);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error getting countries", e);
            res.status(500);
            return createErrorResponse("Failed to retrieve countries");
        }
    }
    
    // GET /api/planner/options
    private String getPlannerOptions(Request req, Response res) {
        try {
            JsonObject options = new JsonObject();
            
            // Meals per day options
            JsonArray mealsArray = new JsonArray();
            for (int i = 1; i <= 5; i++) {
                mealsArray.add(i);
            }
            options.add("mealsPerDay", mealsArray);
            
            // Transport types
            JsonArray transportArray = new JsonArray();
            transportArray.add("rail");
            transportArray.add("air");
            transportArray.add("sea");
            transportArray.add("mixed");
            options.add("transportTypes", transportArray);
            
            // Food types
            JsonArray foodArray = new JsonArray();
            foodArray.add("veg");
            foodArray.add("non-veg");
            foodArray.add("mixed");
            options.add("foodTypes", foodArray);
            
            return gson.toJson(options);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error getting planner options", e);
            res.status(500);
            return createErrorResponse("Failed to retrieve planner options");
        }
    }
    
    // POST /api/planner/estimate
    private String postPlannerEstimate(Request req, Response res) {
        try {
            // Parse the request body
            JsonObject requestJson = gson.fromJson(req.body(), JsonObject.class);
            
            // Extract parameters
            int destinationId = requestJson.has("destination") ? requestJson.get("destination").getAsInt() : 1;
            int travelers = requestJson.has("travelers") ? requestJson.get("travelers").getAsInt() : 1;
            int tripDays = requestJson.has("tripDays") ? requestJson.get("tripDays").getAsInt() : 7;
            int mealsPerDay = requestJson.has("mealsPerDay") ? requestJson.get("mealsPerDay").getAsInt() : 3;
            String transportType = requestJson.has("transportType") ? requestJson.get("transportType").getAsString() : "mixed";
            String foodType = requestJson.has("foodType") ? requestJson.get("foodType").getAsString() : "mixed";
            double budget = requestJson.has("budget") ? requestJson.get("budget").getAsDouble() : 5000.0;
            
            // Create a simple estimate response
            JsonObject estimate = new JsonObject();
            estimate.addProperty("estimatedCost", calculateEstimatedCost(budget, tripDays, travelers, transportType, destinationId, mealsPerDay, foodType));
            estimate.addProperty("sustainabilityScore", calculateSustainabilityScore(transportType, foodType));
            estimate.addProperty("co2Savings", calculateCO2Savings(transportType, tripDays, destinationId));
            estimate.addProperty("message", "Estimate generated successfully");
            
            // Log the request for debugging
            logger.info("Planner estimate request: " + req.body());
            
            return gson.toJson(estimate);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error processing planner estimate", e);
            res.status(500);
            return createErrorResponse("Failed to process planner estimate");
        }
    }
    
    // GET /api/destinations
    private String getAllDestinations(Request req, Response res) {
        try {
            JsonArray destinationsArray = new JsonArray();
            
            // Sample destinations data
            JsonObject paris = new JsonObject();
            paris.addProperty("id", 1);
            paris.addProperty("name", "Paris");
            paris.addProperty("countryId", 1);
            JsonObject parisCoords = new JsonObject();
            parisCoords.addProperty("lat", 48.8566);
            parisCoords.addProperty("lng", 2.3522);
            paris.add("coordinates", parisCoords);
            destinationsArray.add(paris);
            
            JsonObject tokyo = new JsonObject();
            tokyo.addProperty("id", 2);
            tokyo.addProperty("name", "Tokyo");
            tokyo.addProperty("countryId", 2);
            JsonObject tokyoCoords = new JsonObject();
            tokyoCoords.addProperty("lat", 35.6762);
            tokyoCoords.addProperty("lng", 139.6503);
            tokyo.add("coordinates", tokyoCoords);
            destinationsArray.add(tokyo);
            
            JsonObject delhi = new JsonObject();
            delhi.addProperty("id", 3);
            delhi.addProperty("name", "Delhi");
            delhi.addProperty("countryId", 3);
            JsonObject delhiCoords = new JsonObject();
            delhiCoords.addProperty("lat", 28.6139);
            delhiCoords.addProperty("lng", 77.2090);
            delhi.add("coordinates", delhiCoords);
            destinationsArray.add(delhi);
            
            JsonObject newYork = new JsonObject();
            newYork.addProperty("id", 4);
            newYork.addProperty("name", "New York");
            newYork.addProperty("countryId", 4);
            JsonObject newYorkCoords = new JsonObject();
            newYorkCoords.addProperty("lat", 40.7128);
            newYorkCoords.addProperty("lng", -74.0060);
            newYork.add("coordinates", newYorkCoords);
            destinationsArray.add(newYork);
            
            JsonObject rome = new JsonObject();
            rome.addProperty("id", 5);
            rome.addProperty("name", "Rome");
            rome.addProperty("countryId", 5);
            JsonObject romeCoords = new JsonObject();
            romeCoords.addProperty("lat", 41.9028);
            romeCoords.addProperty("lng", 12.4964);
            rome.add("coordinates", romeCoords);
            destinationsArray.add(rome);
            
            return gson.toJson(destinationsArray);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error getting destinations", e);
            res.status(500);
            return createErrorResponse("Failed to retrieve destinations");
        }
    }
    
    // Helper method to calculate estimated cost
    private double calculateEstimatedCost(double budget, int tripDays, int travelers, String transportType, int destinationId, int mealsPerDay, String foodType) {
        // Simple calculation based on inputs
        double baseCost = budget * 0.9;
        double transportMultiplier = 1.0;
        double destinationMultiplier = 1.0;
        double mealsMultiplier = 1.0;
        double foodMultiplier = 1.0;
        
        switch (transportType.toLowerCase()) {
            case "rail":
                transportMultiplier = 0.8;
                break;
            case "air":
                transportMultiplier = 1.3;
                break;
            case "sea":
                transportMultiplier = 1.1;
                break;
            default:
                transportMultiplier = 1.0;
                break;
        }
        
        // Adjust cost based on destination (higher cost for international destinations)
        if (destinationId > 3) {
            destinationMultiplier = 1.5; // Higher cost for international destinations
        } else {
            destinationMultiplier = 1.2; // Slightly higher cost for domestic destinations
        }
        
        // Adjust cost based on meals per day
        mealsMultiplier = 1.0 + (mealsPerDay - 3) * 0.1; // Base is 3 meals per day
        
        // Adjust cost based on food type
        switch (foodType.toLowerCase()) {
            case "non-veg":
                foodMultiplier = 1.2;
                break;
            case "veg":
                foodMultiplier = 0.9;
                break;
            default:
                foodMultiplier = 1.0;
                break;
        }
        
        return baseCost * transportMultiplier * destinationMultiplier * mealsMultiplier * foodMultiplier * (1 + (travelers - 1) * 0.7);
    }
    
    // Helper method to calculate sustainability score based on transport type
    private int calculateSustainabilityScore(String transportType, String foodType) {
        int score = 0;
        
        switch (transportType.toLowerCase()) {
            case "rail":
                score = 90;
                break;
            case "air":
                score = 30;
                break;
            case "sea":
                score = 70;
                break;
            default:
                score = 60;
                break;
        }
        
        // Adjust score based on food type (veg is more sustainable)
        switch (foodType.toLowerCase()) {
            case "veg":
                score += 10;
                break;
            case "non-veg":
                score -= 5;
                break;
            default:
                // No change for mixed
                break;
        }
        
        // Ensure score is within valid range
        return Math.max(0, Math.min(100, score));
    }
    
    // Helper method to calculate CO2 savings
    private double calculateCO2Savings(String transportType, int tripDays, int destinationId) {
        double dailySavings = 0.0;
        
        switch (transportType.toLowerCase()) {
            case "rail":
                dailySavings = 2.5; // kg CO2 saved per day
                break;
            case "air":
                dailySavings = 0.5; // kg CO2 saved per day
                break;
            case "sea":
                dailySavings = 1.8; // kg CO2 saved per day
                break;
            default:
                dailySavings = 1.2; // kg CO2 saved per day
                break;
        }
        
        // Adjust savings based on destination distance
        double distanceMultiplier = 1.0;
        if (destinationId > 3) {
            distanceMultiplier = 1.5; // Longer distance = more savings potential
        }
        
        return dailySavings * tripDays * distanceMultiplier;
    }
    
    // Helper method to create error response
    private String createErrorResponse(String message) {
        JsonObject error = new JsonObject();
        error.addProperty("error", true);
        error.addProperty("message", message);
        return gson.toJson(error);
    }
}