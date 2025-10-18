package com.smarttravelplanner.controller;

import com.smarttravelplanner.db.AlertDAO;
import com.smarttravelplanner.model.Alert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/alerts")
@CrossOrigin
public class AlertController {
    
    private final AlertDAO alertDAO;
    
    @Autowired
    public AlertController(AlertDAO alertDAO) {
        this.alertDAO = alertDAO;
    }
    
    @GetMapping
    public List<Alert> getAllAlerts() throws SQLException {
        return alertDAO.getAllAlerts();
    }
    
    @GetMapping("/{id}")
    public Alert getAlertById(@PathVariable int id) throws SQLException {
        return alertDAO.getAlertById(id);
    }
    
    @GetMapping("/user/{userId}")
    public List<Alert> getAlertsByUserId(@PathVariable UUID userId) throws SQLException {
        // We need to convert UUID to int for the DAO
        // This is a temporary solution - ideally we should update the database schema
        return alertDAO.getAlertsByUserId(userId.hashCode());
    }
    
    @PostMapping
    public int createAlert(@RequestBody Alert alert) throws SQLException {
        return alertDAO.createAlert(alert);
    }
    
    @PutMapping
    public boolean updateAlert(@RequestBody Alert alert) throws SQLException {
        return alertDAO.updateAlert(alert);
    }
    
    @DeleteMapping("/{id}")
    public boolean deleteAlert(@PathVariable int id) throws SQLException {
        return alertDAO.deleteAlert(id);
    }
}