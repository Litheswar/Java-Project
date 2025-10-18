package com.smarttravelplanner.controller;

import com.smarttravelplanner.db.RouteDAO;
import com.smarttravelplanner.model.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/routes")
@CrossOrigin
public class RouteController {
    
    private final RouteDAO routeDAO;
    
    @Autowired
    public RouteController(RouteDAO routeDAO) {
        this.routeDAO = routeDAO;
    }
    
    @GetMapping
    public List<Route> getAllRoutes() throws SQLException {
        return routeDAO.getAllRoutes();
    }
    
    @GetMapping("/{id}")
    public Route getRouteById(@PathVariable int id) throws SQLException {
        return routeDAO.getRouteById(id);
    }
    
    @GetMapping("/trip/{tripId}")
    public List<Route> getRoutesByTripId(@PathVariable int tripId) throws SQLException {
        return routeDAO.getRoutesByTripId(tripId);
    }
    
    @PostMapping
    public int createRoute(@RequestBody Route route) throws SQLException {
        return routeDAO.createRoute(route);
    }
    
    @PostMapping("/optimize")
    public Route optimizeRoute(@RequestBody Route route) throws SQLException {
        // In a real implementation, you would call the RouteOptimizer service here
        // For now, we'll just return the route as is
        return route;
    }
    
    @PutMapping
    public boolean updateRoute(@RequestBody Route route) throws SQLException {
        return routeDAO.updateRoute(route);
    }
    
    @DeleteMapping("/{id}")
    public boolean deleteRoute(@PathVariable int id) throws SQLException {
        return routeDAO.deleteRoute(id);
    }
}