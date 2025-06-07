package com.example.demo.Repository;

import com.example.demo.Entity.AttendanceRecord;
import com.example.demo.Entity.userentity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttendanceRecordRepository extends JpaRepository<AttendanceRecord, Long> {

    // Get all records for a specific employee
    List<AttendanceRecord> findByEmployee(userentity employee);

    // Get all records for a specific date
    List<AttendanceRecord> findByDate(LocalDate date);

    // Get all records for a specific employee on a specific date
    AttendanceRecord findByEmployeeAndDate(userentity employee, LocalDate date);

    // Count by status for a specific date
    Long countByDateAndStatus(LocalDate date, String status);

    // Count by location for a specific date
    Long countByDateAndLocation(LocalDate date, String location);

    // Get all records between two dates
    List<AttendanceRecord> findByEmployeeAndDateBetween(userentity employee, LocalDate startDate, LocalDate endDate);
}

