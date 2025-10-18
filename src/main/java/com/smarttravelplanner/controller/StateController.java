package com.smarttravelplanner.controller;

import com.smarttravelplanner.db.StateDAO;
import com.smarttravelplanner.model.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/states")
@CrossOrigin
public class StateController {
    
    private final StateDAO stateDAO;
    
    @Autowired
    public StateController(StateDAO stateDAO) {
        this.stateDAO = stateDAO;
    }
    
    @GetMapping
    public List<State> getAllStates() throws SQLException {
        return stateDAO.getAllStates();
    }
    
    @GetMapping("/country/{countryId}")
    public List<State> getStatesByCountryId(@PathVariable int countryId) throws SQLException {
        return stateDAO.getStatesByCountryId(countryId);
    }
    
    @GetMapping("/{id}")
    public State getStateById(@PathVariable int id) throws SQLException {
        return stateDAO.getStateById(id);
    }
}