package com.example.demo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Entity.userentity;
import com.example.demo.Service.userservice;

import jakarta.servlet.http.HttpSession;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api/users")
public class usercontroller {

    @Autowired
    private userservice userService;

    // Save user data
    @PostMapping
    public userentity saveUser(@RequestBody userentity user) {
        return userService.savedata(user);
    }

    // Get all users
    @GetMapping
    public List<userentity> getAllUsers() {
        return userService.getAllData();
    }
    // --- LOGIN ---
    @PostMapping("/login")
    public ResponseEntity<userentity> login(@RequestBody userentity request, HttpSession session) {
        try {
            userentity user = userService.login(request.getEmail(), request.getPassword(), session);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).build();
        }
    }


    // --- LOGOUT ---
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpSession session) {
    	userService.logout(session);
        return ResponseEntity.noContent().build();
    }

 
}
