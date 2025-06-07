package com.example.demo.Controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Entity.AttendanceRecord;
import com.example.demo.Entity.userentity;
import com.example.demo.Service.AttendanceRecordService;
import com.example.demo.Service.userservice;


@RestController
@RequestMapping("/attendance")
@CrossOrigin(origins = "http://localhost:8081")
public class AttendanceRecordController {

    @Autowired
    private AttendanceRecordService attendanceRecordService;

    @Autowired
    private userservice userService;


    // ----------- Check In --------------------
    @PostMapping(value = "/checkin", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public AttendanceRecord checkIn(
            @RequestParam Integer userId,
            @RequestParam String location,
            @RequestParam("image") MultipartFile image) throws IOException {

        userentity user = userService.getUserById(userId);
        return attendanceRecordService.checkIn(user, location, image);
    }

    // ----------- Check Out --------------------
    @PostMapping(value = "/checkout", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public AttendanceRecord checkOut(
            @RequestParam Long recordId,
            @RequestParam("image") MultipartFile image) throws IOException {

        return attendanceRecordService.checkOut(recordId, image);
    }

    @GetMapping("/all")
    public List<AttendanceRecord> getAllAttendanceRecords() {
        return attendanceRecordService.getAllRecords();
    }

    @GetMapping("/employee/{employeeId}")
    public List<AttendanceRecord> getRecordsByEmployee(@PathVariable Integer employeeId) {
        userentity employee = userService.getUserById(employeeId);
        return attendanceRecordService.getRecordsByEmployee(employee);
    }

    @GetMapping("/date/{date}")
    public List<AttendanceRecord> getRecordsByDate(@PathVariable String date) {
        LocalDate localDate = LocalDate.parse(date);
        return attendanceRecordService.getRecordsByDate(localDate);
    }

    @GetMapping("/employee/{employeeId}/date/{date}")
    public AttendanceRecord getRecordByEmployeeAndDate(@PathVariable Integer employeeId, @PathVariable String date) {
        userentity employee = userService.getUserById(employeeId);
        LocalDate localDate = LocalDate.parse(date);
        return attendanceRecordService.getRecordByEmployeeAndDate(employee, localDate);
    }

    @GetMapping("/employee/{employeeId}/range")
    public List<AttendanceRecord> getWeeklyRecords(
            @PathVariable Integer employeeId,
            @RequestParam String startDate,
            @RequestParam String endDate
    ) {
        userentity employee = userService.getUserById(employeeId);
        return attendanceRecordService.getWeeklyRecords(
            employee,
            LocalDate.parse(startDate),
            LocalDate.parse(endDate)
        );
    }


    @GetMapping("/count/status")
    public long countByStatus(@RequestParam String date, @RequestParam String status) {
        return attendanceRecordService.countByStatus(LocalDate.parse(date), status);
    }

    @GetMapping("/count/location")
    public long countByLocation(@RequestParam String date, @RequestParam String location) {
        return attendanceRecordService.countByLocation(LocalDate.parse(date), location);
    }

    @GetMapping("/{id}")
    public Optional<AttendanceRecord> getById(@PathVariable Long id) {
        return attendanceRecordService.getById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        attendanceRecordService.deleteById(id);
    }
    @GetMapping("/attendacebyidemp/{id}")
    public ResponseEntity<userentity> getUserById(@PathVariable Integer id) {
        try {
            userentity user = attendanceRecordService.getbyids(id);
            return ResponseEntity.ok(user);
        } catch (NoSuchElementException | NoSuchFieldError e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
