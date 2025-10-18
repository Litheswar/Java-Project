package com.smarttravelplanner.controller;

import com.smarttravelplanner.db.ExpenseDAO;
import com.smarttravelplanner.model.Expense;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/expenses")
@CrossOrigin
public class ExpenseController {
    
    private final ExpenseDAO expenseDAO;
    
    @Autowired
    public ExpenseController(ExpenseDAO expenseDAO) {
        this.expenseDAO = expenseDAO;
    }
    
    @GetMapping
    public List<Expense> getAllExpenses() throws SQLException {
        return expenseDAO.getAllExpenses();
    }
    
    @GetMapping("/{id}")
    public Expense getExpenseById(@PathVariable int id) throws SQLException {
        return expenseDAO.getExpenseById(id);
    }
    
    @GetMapping("/trip/{tripId}")
    public List<Expense> getExpensesByTripId(@PathVariable int tripId) throws SQLException {
        return expenseDAO.getExpensesByTripId(tripId);
    }
    
    @PostMapping
    public int createExpense(@RequestBody Expense expense) throws SQLException {
        return expenseDAO.createExpense(expense);
    }
    
    @PutMapping
    public boolean updateExpense(@RequestBody Expense expense) throws SQLException {
        return expenseDAO.updateExpense(expense);
    }
    
    @DeleteMapping("/{id}")
    public boolean deleteExpense(@PathVariable int id) throws SQLException {
        return expenseDAO.deleteExpense(id);
    }
}