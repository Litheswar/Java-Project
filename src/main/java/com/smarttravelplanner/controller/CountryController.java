package com.smarttravelplanner.controller;

import com.smarttravelplanner.db.CountryDAO;
import com.smarttravelplanner.model.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/countries")
@CrossOrigin // Allow requests from frontend
public class CountryController {
    
    private final CountryDAO countryDAO;
    
    @Autowired
    public CountryController(CountryDAO countryDAO) {
        this.countryDAO = countryDAO;
    }
    
    @GetMapping
    public List<Country> getAllCountries() throws SQLException {
        return countryDAO.getAllCountries();
    }
    
    @GetMapping("/{id}")
    public Country getCountryById(@PathVariable UUID id) throws SQLException {
        return countryDAO.getCountryById(id);
    }
}