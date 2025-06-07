package com.example.demo.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.LeaveRequest;
import com.example.demo.Entity.userentity;
import com.example.demo.Repository.IRepository;
import com.example.demo.Repository.LeaveRequestRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class LeaveRequestService {
@Autowired
    private  LeaveRequestRepository leaveRequestRepository;
    @Autowired
    private  IRepository reppp;
    

    

    // Submit a new leave request
    public LeaveRequest submitLeaveRequest(LeaveRequest leaveRequest, userentity employee) {
        if (leaveRequest == null || employee == null) {
            throw new IllegalArgumentException("Leave request and employee cannot be null");
        }
        leaveRequest.setEmployee(employee);
        leaveRequest.setStatus("PENDING");
        return leaveRequestRepository.save(leaveRequest);
    }

    // Get all leave requests
    public List<LeaveRequest> getAllLeaveRequests() {
        return leaveRequestRepository.findAll();
    }

    // Get all leave requests for a specific employee
    public List<LeaveRequest> getLeaveRequestsByEmployeeId(Integer employeeId) {
        if (employeeId == null) {
            throw new IllegalArgumentException("Employee ID cannot be null");
        }
        return leaveRequestRepository.findByEmployeeId(employeeId);
    }

    // Approve or reject a leave request
    public LeaveRequest updateLeaveStatus(Long requestId, String status) {
        if (requestId == null || status == null) {
            throw new IllegalArgumentException("Request ID and status cannot be null");
        }
        LeaveRequest request = leaveRequestRepository.findById(requestId)
                .orElseThrow(() -> new EntityNotFoundException("Leave request not found with ID: " + requestId));
        request.setStatus(status.toUpperCase());
        return leaveRequestRepository.save(request);
    }

    // Get a single leave request
    public LeaveRequest getLeaveRequestById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Request ID cannot be null");
        }
        return leaveRequestRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Leave request not found with ID: " + id));
    }
    
 // LeaveRequestService.java
    public List<LeaveRequest> findAllByEmployeeId(Integer empid) {
        return leaveRequestRepository.findByEmployeeId(empid);
    }

    public userentity getLeavesBySingleIds(Integer empid) {
    	Optional<userentity>msg=reppp.findById(empid);
    	return msg.get();
    }

	
}