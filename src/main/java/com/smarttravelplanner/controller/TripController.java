package com.smarttravelplanner.controller;

import com.smarttravelplanner.db.TripDAO;
import com.smarttravelplanner.model.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/trips")
@CrossOrigin
public class TripController {
    
    private final TripDAO tripDAO;
    
    @Autowired
    public TripController(TripDAO tripDAO) {
        this.tripDAO = tripDAO;
    }
    
    @GetMapping
    public List<Trip> getAllTrips() throws SQLException {
        return tripDAO.getAllTrips();
    }
    
    @GetMapping("/{id}")
    public Trip getTripById(@PathVariable int id) throws SQLException {
        return tripDAO.getTripById(id);
    }
    
    @GetMapping("/user/{userId}")
    public List<Trip> getTripsByUserId(@PathVariable UUID userId) throws SQLException {
        return tripDAO.getTripsByUserId(userId);
    }
    
    @PostMapping
    public int createTrip(@RequestBody Trip trip) throws SQLException {
        return tripDAO.createTrip(trip);
    }
    
    @PutMapping
    public boolean updateTrip(@RequestBody Trip trip) throws SQLException {
        return tripDAO.updateTrip(trip);
    }
    
    @DeleteMapping("/{id}")
    public boolean deleteTrip(@PathVariable int id) throws SQLException {
        return tripDAO.deleteTrip(id);
    }
}