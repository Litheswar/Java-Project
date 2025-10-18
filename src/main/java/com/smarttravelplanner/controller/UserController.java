package com.smarttravelplanner.controller;

import com.smarttravelplanner.db.UserDAO;
import com.smarttravelplanner.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {
    
    private final UserDAO userDAO;
    
    @Autowired
    public UserController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
    
    @GetMapping
    public List<User> getAllUsers() throws SQLException {
        return userDAO.getAllUsers();
    }
    
    @GetMapping("/{id}")
    public User getUserById(@PathVariable UUID id) throws SQLException {
        return userDAO.getUserById(id);
    }
    
    @PostMapping
    public UUID createUser(@RequestBody User user) throws SQLException {
        return userDAO.createUser(user);
    }
    
    @PutMapping
    public boolean updateUser(@RequestBody User user) throws SQLException {
        return userDAO.updateUser(user);
    }
    
    @DeleteMapping("/{id}")
    public boolean deleteUser(@PathVariable UUID id) throws SQLException {
        return userDAO.deleteUser(id);
    }
}