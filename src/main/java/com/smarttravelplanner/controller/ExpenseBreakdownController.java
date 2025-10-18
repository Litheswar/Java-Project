package com.smarttravelplanner.controller;

import com.smarttravelplanner.db.ExpenseBreakdownDAO;
import com.smarttravelplanner.model.ExpenseBreakdown;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/expense_breakdown")
@CrossOrigin
public class ExpenseBreakdownController {
    
    private final ExpenseBreakdownDAO expenseBreakdownDAO;
    
    @Autowired
    public ExpenseBreakdownController(ExpenseBreakdownDAO expenseBreakdownDAO) {
        this.expenseBreakdownDAO = expenseBreakdownDAO;
    }
    
    @GetMapping
    public List<ExpenseBreakdown> getAllExpenseBreakdowns() throws SQLException {
        return expenseBreakdownDAO.getAllExpenseBreakdowns();
    }
    
    @GetMapping("/{id}")
    public ExpenseBreakdown getExpenseBreakdownById(@PathVariable int id) throws SQLException {
        return expenseBreakdownDAO.getExpenseBreakdownById(id);
    }
    
    @GetMapping("/trip/{tripId}")
    public ExpenseBreakdown getExpenseBreakdownByTripId(@PathVariable int tripId) throws SQLException {
        return expenseBreakdownDAO.getExpenseBreakdownByTripId(tripId);
    }
    
    @PostMapping
    public int createExpenseBreakdown(@RequestBody ExpenseBreakdown expenseBreakdown) throws SQLException {
        return expenseBreakdownDAO.createExpenseBreakdown(expenseBreakdown);
    }
    
    @PutMapping
    public boolean updateExpenseBreakdown(@RequestBody ExpenseBreakdown expenseBreakdown) throws SQLException {
        return expenseBreakdownDAO.updateExpenseBreakdown(expenseBreakdown);
    }
    
    @DeleteMapping("/{id}")
    public boolean deleteExpenseBreakdown(@PathVariable int id) throws SQLException {
        return expenseBreakdownDAO.deleteExpenseBreakdown(id);
    }
}