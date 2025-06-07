package com.example.demo.Service;

import com.example.demo.Entity.AdminEntity;
import com.example.demo.Repository.AdminRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private AdminRepo adminRepo;

    public List<AdminEntity> getAllAdmins() {
        return adminRepo.findAll();
    }

    public Optional<AdminEntity> getAdminById(Integer id) {
        return adminRepo.findById(id);
    }

    public AdminEntity createAdmin(AdminEntity admin) {
        return adminRepo.save(admin);
    }

    public void softDeleteAdmin(Integer id) {
        AdminEntity admin = adminRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Admin not found"));
        admin.setDeleted(true);
        adminRepo.save(admin);
    }

    public List<AdminEntity> getAllAdmins1() {
        return adminRepo.findByDeletedFalse();
    }
    

    public AdminEntity login(String email, String password) {
        AdminEntity admin = adminRepo.findByEmailAndPassword(email, password);
        if (admin == null || Boolean.TRUE.equals(admin.getDeleted()) || Boolean.FALSE.equals(admin.getActive())) {
            throw new RuntimeException("Invalid credentials or account is inactive/deleted");
        }
        return admin;
    }


    public AdminEntity updateAdmin(Integer id, AdminEntity updatedAdmin) {
        AdminEntity admin = adminRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Admin not found"));

        admin.setName(updatedAdmin.getName());
        admin.setEmail(updatedAdmin.getEmail());
        admin.setNumber(updatedAdmin.getNumber());
        admin.setAddress(updatedAdmin.getAddress());
        admin.setRole(updatedAdmin.getRole());
        admin.setPassword(updatedAdmin.getPassword());
        admin.setSuperAdmin(updatedAdmin.getSuperAdmin());

        return adminRepo.save(admin);
    }
}
