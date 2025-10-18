package com.smarttravelplanner.controller;

import com.smarttravelplanner.db.DestinationDAO;
import com.smarttravelplanner.model.Destination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/destinations")
@CrossOrigin
public class DestinationController {
    
    private final DestinationDAO destinationDAO;
    
    @Autowired
    public DestinationController(DestinationDAO destinationDAO) {
        this.destinationDAO = destinationDAO;
    }
    
    @GetMapping
    public List<Destination> getAllDestinations() throws SQLException {
        return destinationDAO.getAllDestinations();
    }
    
    @GetMapping("/state/{stateId}")
    public List<Destination> getDestinationsByStateId(@PathVariable int stateId) throws SQLException {
        return destinationDAO.getDestinationsByStateId(stateId);
    }
    
    @GetMapping("/{id}")
    public Destination getDestinationById(@PathVariable int id) throws SQLException {
        return destinationDAO.getDestinationById(id);
    }
}