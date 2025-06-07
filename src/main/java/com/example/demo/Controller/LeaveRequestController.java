package com.example.demo.Controller;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Entity.LeaveRequest;
import com.example.demo.Entity.userentity;
import com.example.demo.Service.LeaveRequestService;
import com.example.demo.Service.userservice;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/leaves")
@CrossOrigin(origins = "http://localhost:8081") // Adjust as needed
public class LeaveRequestController {

    @Autowired
    private LeaveRequestService leaveRequestService;

    @Autowired
    private userservice userService;

    // Submit a new leave request
    @PostMapping("/submit")
    public ResponseEntity<LeaveRequest> submitLeave(
            @RequestBody LeaveRequest leaveRequest,
            @RequestParam Integer employeeId) {
        try {
            userentity employee = userService.getUserById(employeeId);
            LeaveRequest savedRequest = leaveRequestService.submitLeaveRequest(leaveRequest, employee);
            return ResponseEntity.ok(savedRequest);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Get all leave requests
    @GetMapping("/leavesAll")
    public ResponseEntity<List<LeaveRequest>> getAllLeaves() {
        return ResponseEntity.ok(leaveRequestService.getAllLeaveRequests());
    }

    // Get leave requests for a specific employee
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<LeaveRequest>> getLeavesByEmployee(@PathVariable Integer employeeId) {
        try {
            List<LeaveRequest> leaves = leaveRequestService.getLeaveRequestsByEmployeeId(employeeId);
            return ResponseEntity.ok(leaves);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Get a single leave request
    @GetMapping("/{id}")
    public ResponseEntity<LeaveRequest> getLeaveById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(leaveRequestService.getLeaveRequestById(id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Update leave request status (APPROVED/REJECTED/PENDING) using request body (String)
    @PutMapping("/{id}/status")
    public ResponseEntity<LeaveRequest> updateLeaveStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> payload) {

        String status = payload.get("status");

        if (id == null || status == null || status.isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        status = status.trim().toUpperCase();

        if (!status.equals("APPROVED") && !status.equals("REJECTED") && !status.equals("PENDING")) {
            return ResponseEntity.badRequest().build();
        }

        try {
            LeaveRequest updated = leaveRequestService.updateLeaveStatus(id, status);
            return ResponseEntity.ok(updated);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/employee/all/{empid}")
    public ResponseEntity<List<LeaveRequest>> getLeavesByEmployeeId(@PathVariable Integer empid) {
        List<LeaveRequest> leaves = leaveRequestService.findAllByEmployeeId(empid);
        return ResponseEntity.ok(leaves);
    }
    
    @GetMapping("/employee/one/{empid}")
    public ResponseEntity<userentity> getLeavesByEmployeeIds(@PathVariable Integer empid) {
        try {
        	userentity user = leaveRequestService.getLeavesBySingleIds(empid);
            return ResponseEntity.ok(user);
        } catch (NoSuchElementException | NoSuchFieldError e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

}
