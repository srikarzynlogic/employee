package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Entity.LeaveRequest;

@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {

    // Optional: custom finder methods

    List<LeaveRequest> findByEmployeeId(Integer employeeId);

    List<LeaveRequest> findByStatus(String status);
}

