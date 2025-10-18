package com.smarttravelplanner.controller;

import com.smarttravelplanner.db.TripHistoryDAO;
import com.smarttravelplanner.model.TripHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/trip_history")
@CrossOrigin
public class TripHistoryController {
    
    private final TripHistoryDAO tripHistoryDAO;
    
    @Autowired
    public TripHistoryController(TripHistoryDAO tripHistoryDAO) {
        this.tripHistoryDAO = tripHistoryDAO;
    }
    
    @GetMapping
    public List<TripHistory> getAllTripHistory() throws SQLException {
        return tripHistoryDAO.getAllTripHistory();
    }
    
    @GetMapping("/{id}")
    public TripHistory getTripHistoryById(@PathVariable int id) throws SQLException {
        return tripHistoryDAO.getTripHistoryById(id);
    }
    
    @GetMapping("/user/{userId}")
    public List<TripHistory> getTripHistoryByUserId(@PathVariable UUID userId) throws SQLException {
        return tripHistoryDAO.getTripHistoryByUserId(userId);
    }
    
    @GetMapping("/trip/{tripId}")
    public List<TripHistory> getTripHistoryByTripId(@PathVariable int tripId) throws SQLException {
        return tripHistoryDAO.getTripHistoryByTripId(tripId);
    }
    
    @PostMapping
    public int createTripHistory(@RequestBody TripHistory tripHistory) throws SQLException {
        return tripHistoryDAO.createTripHistory(tripHistory);
    }
    
    @PutMapping
    public boolean updateTripHistory(@RequestBody TripHistory tripHistory) throws SQLException {
        return tripHistoryDAO.updateTripHistory(tripHistory);
    }
    
    @DeleteMapping("/{id}")
    public boolean deleteTripHistory(@PathVariable int id) throws SQLException {
        return tripHistoryDAO.deleteTripHistory(id);
    }
}