package com.example.demo.Controller;

import com.example.demo.Entity.AdminEntity;
import com.example.demo.Service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:8081")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/all")
    public List<AdminEntity> getAllAdmins() {
        return adminService.getAllAdmins();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdminEntity> getAdminById(@PathVariable Integer id) {
        return adminService.getAdminById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public AdminEntity createAdmin(@RequestBody AdminEntity admin) {
        return adminService.createAdmin(admin);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<AdminEntity> updateAdmin(@PathVariable Integer id, @RequestBody AdminEntity updatedAdmin) {
        return ResponseEntity.ok(adminService.updateAdmin(id, updatedAdmin));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> softDeleteAdmin(@PathVariable Integer id) {
        adminService.softDeleteAdmin(id);
        return ResponseEntity.ok("Admin soft deleted successfully");
    }

   

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
        String email = loginRequest.get("email");
        String password = loginRequest.get("password");

        try {
            AdminEntity admin = adminService.login(email, password);
            return ResponseEntity.ok(admin); // Return admin or create a response DTO
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body("Login failed: " + e.getMessage());
        }
    }

}
