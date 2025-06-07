package com.example.demo.Service;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Entity.AttendanceRecord;
import com.example.demo.Entity.userentity;
import com.example.demo.Repository.AttendanceRecordRepository;
import com.example.demo.Repository.IRepository;

@Service
public class AttendanceRecordService {
	
	
	 @Value("${supabase.url}")
	    private String supabaseUrl;

	    @Value("${supabase.api.key}")
	    private String apiKey;

	    @Value("${supabase.bucket}")
	    private String bucket;
	   
    @Autowired
    private AttendanceRecordRepository attendanceRecordRepository;
    
    @Autowired
    private IRepository emprepo;
    
    private String uploadImageToSupabase(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID() + ".jpg";
        String path = "/storage/v1/object/" + bucket + "/" + fileName;

        HttpHeaders headers = new HttpHeaders();
        // Use the service role key here for full permissions
        headers.set("Authorization", "Bearer " + apiKey);
        headers.set("apikey", apiKey);
        headers.setContentType(MediaType.IMAGE_JPEG);

        HttpEntity<byte[]> entity = new HttpEntity<>(file.getBytes(), headers);
        RestTemplate restTemplate = new RestTemplate();
        String uploadUrl = supabaseUrl + path;

        restTemplate.put(uploadUrl, entity);
        return supabaseUrl + "/storage/v1/object/public/" + bucket + "/" + fileName;
    }


    public AttendanceRecord checkIn(userentity employee, String location, MultipartFile file) throws IOException {
        AttendanceRecord record = new AttendanceRecord();
        record.setEmployee(employee);
        record.setDate(LocalDate.now());
        record.setClockIn(LocalDateTime.now());
        record.setLocation(location);
        record.setStatus("Present");

        if (file != null && !file.isEmpty()) {
            String imageUrl = uploadImageToSupabase(file);
            record.setSetCheckInImageUrl(imageUrl);
        }

        return attendanceRecordRepository.save(record);
    }

    public AttendanceRecord checkOut(Long recordId, MultipartFile file) throws IOException {
        AttendanceRecord record = attendanceRecordRepository.findById(recordId)
            .orElseThrow(() -> new RuntimeException("Attendance record not found"));

        record.setClockOut(LocalDateTime.now());

        if (record.getClockIn() != null) {
            Duration duration = Duration.between(record.getClockIn(), record.getClockOut());
            record.setHoursWorked((double) duration.toMinutes() / 60);
        }

        if (file != null && !file.isEmpty()) {
            String imageUrl = uploadImageToSupabase(file);
            record.setSetCheckOutImageUrl(imageUrl);
        }

        return attendanceRecordRepository.save(record);
    }

    // Get all attendance records
    public List<AttendanceRecord> getAllRecords() {
        return attendanceRecordRepository.findAll();
    }

    // Get records by employee
    public List<AttendanceRecord> getRecordsByEmployee(userentity employee) {
        return attendanceRecordRepository.findByEmployee(employee);
    }

    // Get records by date
    public List<AttendanceRecord> getRecordsByDate(LocalDate date) {
        return attendanceRecordRepository.findByDate(date);
    }

    // Get specific employee record on a given date
    public AttendanceRecord getRecordByEmployeeAndDate(userentity employee, LocalDate date) {
        return attendanceRecordRepository.findByEmployeeAndDate(employee, date);
    }

    // Get all records for an employee within a date range (for weekly summary)
    public List<AttendanceRecord> getWeeklyRecords(userentity employee, LocalDate startDate, LocalDate endDate) {
        return attendanceRecordRepository.findByEmployeeAndDateBetween(employee, startDate, endDate);
    }

    // Count attendance status for a specific day
    public long countByStatus(LocalDate date, String status) {
        return attendanceRecordRepository.countByDateAndStatus(date, status);
    }

    // Count location-based attendance for a specific day
    public long countByLocation(LocalDate date, String location) {
        return attendanceRecordRepository.countByDateAndLocation(date, location);
    }

    // Get record by ID (optional for admin editing)
    public Optional<AttendanceRecord> getById(Long id) {
        return attendanceRecordRepository.findById(id);
    }

    // Delete a record (optional)
    public void deleteById(Long id) {
        attendanceRecordRepository.deleteById(id);
    }
    
    public userentity getbyids(Integer id) {
    	Optional<userentity>datsa= emprepo.findById(id);
    	return datsa.get();
    }
}
