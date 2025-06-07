package com.example.demo.Entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "attendance_records")
@Data
public class AttendanceRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Link to employee
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private userentity employee;


    private LocalDate date;

    private LocalDateTime clockIn;

    private LocalDateTime clockOut;

    private double hoursWorked;

    // e.g., "Present", "Late", "Absent", "On Leave"
    private String status;

    // e.g., "Remote", "Office"
    private String location;
    
    private String setCheckInImageUrl;

	private String setCheckOutImageUrl;
	
	private String timer;

}

